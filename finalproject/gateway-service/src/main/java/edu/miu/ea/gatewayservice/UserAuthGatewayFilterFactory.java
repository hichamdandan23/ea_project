package edu.miu.ea.gatewayservice;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import edu.miu.ea.contracts.Code;
import edu.miu.ea.contracts.user.UserVerifyResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<UserAuthGatewayFilterFactory.Config> {
    private static final Log logger = LogFactory.getLog(UserAuthGatewayFilterFactory.class);

    private static final String AUTHORIZE_TOKEN = "USER_TOKEN";
    private static final String USER_ID = "USER_ID";
    private static final String USER_EMAIL = "USER_EMAIL";
    private static final String USER_ROLE = "USER_ROLE";

    @Autowired
    private RestTemplate restTemplate;

    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient eurekaClient;

    public UserAuthGatewayFilterFactory() {
        super(Config.class);
        logger.info("Loaded GatewayFilterFactory [Authorize]");
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }

            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            String token = headers.getFirst(AUTHORIZE_TOKEN);
            if (token == null) {
                token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
            }

            ServerHttpResponse response = exchange.getResponse();
            if (StringUtils.isEmpty(token)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }


            String url = lookupUrlFor("user-service") + "/verify";
            UserVerifyResponse userVerifyResponse = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    createHttpEntity(token),
                    UserVerifyResponse.class
            ).getBody();

            if (userVerifyResponse.getCode() == Code.Success) {
                ServerHttpRequest host = exchange.getRequest().mutate()
                        .header(USER_ID, userVerifyResponse.getId().toString())
                        .header(USER_EMAIL, userVerifyResponse.getEmail())
                        .header(USER_ROLE,
                                userVerifyResponse.getRoles().stream().collect(Collectors.joining(",")))
                        .build();
                ServerWebExchange build = exchange.mutate().request(host).build();
                return chain.filter(build);
            } else {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        };
    }

    private boolean verifyToken(ServerWebExchange exchange, String token) {

        return false;
    }

    private String lookupUrlFor(String appName) {
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(appName, false);
        return instanceInfo.getHomePageUrl();
    }

    private HttpEntity<Object> createHttpEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("token", token);

        return new HttpEntity<>(map, headers);
    }

    public static class Config {
        private boolean enabled;

        public Config() {
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
