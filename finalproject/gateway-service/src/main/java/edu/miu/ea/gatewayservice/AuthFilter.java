package edu.miu.ea.gatewayservice;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import edu.miu.ea.commons.contracts.Code;
import edu.miu.ea.commons.contracts.UserVerifyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

public class AuthFilter extends ZuulFilter {
    private RestTemplate restTemplate;

    private EurekaClient eurekaClient;

    private Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    @Qualifier("eurekaClient")
    public void setEurekaClient( EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        String contextPath = request.getServletPath();

        if(contextPath.equals("/user/auth") || contextPath.equals("/user/register") || contextPath.equals("/user/verify")) {
            return null;
        }

        String token = getToken(request);
        verifyToken(requestContext, request, token);

        return null;
    }

    private String getToken(HttpServletRequest request) {
        return request.getHeader("USER_TOKEN");
    }

    private void verifyToken(RequestContext requestContext, HttpServletRequest request, String token) {
        String url =  lookupUrlFor("user-service") + "/verify";
        UserVerifyResponse userVerifyResponse = restTemplate.exchange(
                url,
                HttpMethod.POST,
                createHttpEntity(token),
                UserVerifyResponse.class
        ).getBody();

        if(userVerifyResponse.getCode() != Code.Success) {
            setUnauthorizedResponse(requestContext);
        }
    }

    private void setUnauthorizedResponse(RequestContext requestContext) {
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        String result = "{\"code\": \"AuthFailed\", \"msg\":\"User auth failed.\"}";
        requestContext.addZuulResponseHeader("content-type", "application/json;charset=utf-8");
        requestContext.setResponseBody(result);
    }

    private String lookupUrlFor(String appName) {
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(appName, false);
        return instanceInfo.getHomePageUrl();
    }

    private HttpEntity<Object> createHttpEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("token", token);

        return new HttpEntity<>(map, headers);
    }
}
