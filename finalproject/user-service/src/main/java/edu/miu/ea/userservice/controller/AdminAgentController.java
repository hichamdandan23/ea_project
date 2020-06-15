package edu.miu.ea.userservice.controller;

import edu.miu.ea.commons.controller.BaseReadWriteController;
import edu.miu.ea.contracts.Code;
import edu.miu.ea.contracts.Response;
import edu.miu.ea.contracts.user.AgentCreateRequest;
import edu.miu.ea.contracts.user.AgentResponse;
import edu.miu.ea.contracts.user.UserCreateRequest;
import edu.miu.ea.contracts.user.UserResponse;
import edu.miu.ea.userservice.domain.Agent;
import edu.miu.ea.userservice.domain.User;
import edu.miu.ea.userservice.service.AdminService;
import edu.miu.ea.userservice.service.AgentService;
import edu.miu.ea.userservice.service.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.EnumMap;

@RestController
@RequestMapping("/admin/agent")
public class AdminAgentController extends BaseReadWriteController<AgentResponse, Agent, Long> {
    @Autowired
    private AgentService agentService;

    @PostMapping("/create")
    public Response create(AgentCreateRequest agentCreateRequest) {
        if(agentCreateRequest.getEmail() == null || agentCreateRequest.getPassword() == null) {
            return new Response(Code.ParamError, "Param error");
        }

        Agent agent = new Agent();
        agent.setEmail(agentCreateRequest.getEmail());
        agent.setPassword(agentCreateRequest.getPassword());
        agent.setCreatedAt(LocalDateTime.now());

        EnumMap result = agentService.create(agent);
        Code code = (Code) result.get(ItemType.Code);
        String msg = (String) result.get(ItemType.Msg);
        if(code != Code.Success) {
            return new Response(code, msg);
        }

        return new Response(Code.Success, "");
    }
}
