package edu.miu.ea.userservice.service;

import edu.miu.ea.commons.service.BaseReadWriteService;
import edu.miu.ea.contracts.user.AgentResponse;
import edu.miu.ea.userservice.domain.Agent;
import edu.miu.ea.userservice.domain.User;

import java.util.EnumMap;

public interface AgentService extends BaseReadWriteService<AgentResponse, Agent, Long> {
    EnumMap<ItemType, Object> createUser(String email, String password);

    Agent getAgentByEmail(String email);

    String generateToken(Agent agent);

    Agent verifyToken(String token);
}
