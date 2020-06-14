package edu.miu.ea.userservice.controller;

import edu.miu.ea.contracts.*;
import edu.miu.ea.contracts.user.*;
import edu.miu.ea.userservice.domain.Agent;
import edu.miu.ea.userservice.service.AgentService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agent")
public class AuthAgentController {
    @Autowired
    private AgentService agentService;

    private static final Log logger = LogFactory.getLog(AuthAgentController.class);

    @PostMapping("/auth")
    public AgentAuthResponse token(AgentAuthRequest request) {
        AgentAuthResponse agentAuthResponse = new AgentAuthResponse(Code.Success, "", "");

        Agent agent = agentService.getAgentByEmail(request.getEmail());
        if (agent == null) {
            agentAuthResponse.setCode(Code.NotExist);
            agentAuthResponse.setMsg("User does not exist");
            return agentAuthResponse;
        }

        if (!agent.getPassword().equals(request.getPassword())) {
            agentAuthResponse.setCode(Code.AuthFailed);
            agentAuthResponse.setMsg("User auth failed");
            return agentAuthResponse;
        }

        String token = agentService.generateToken(agent);
        if (token.equals("")) {
            agentAuthResponse.setCode(Code.SystemError);
            agentAuthResponse.setMsg("System error");
            return agentAuthResponse;
        }


        agentAuthResponse.setToken(token);
        return agentAuthResponse;
    }

    @PostMapping("/verify")
    public AgentVerifyResponse verify(AgentVerifyRequest request) {
        AgentVerifyResponse agentVerifyResponse = new AgentVerifyResponse(
                Code.Success, "", 0L, "", null);

        if (request.getToken() == null || request.getToken().equals("")) {
            agentVerifyResponse.setCode(Code.ParamError);
            agentVerifyResponse.setMsg("Verify failed");
            return agentVerifyResponse;
        }

        Agent agent = agentService.verifyToken(request.getToken());
        if (agent == null) {
            agentVerifyResponse.setCode(Code.AuthFailed);
            agentVerifyResponse.setMsg("Verify failed");
            return agentVerifyResponse;
        }

        agentVerifyResponse.setId(agent.getId());
        agentVerifyResponse.setEmail(agent.getEmail());
        agentVerifyResponse.setCreatedAt(agent.getCreatedAt());

        return agentVerifyResponse;
    }
}
