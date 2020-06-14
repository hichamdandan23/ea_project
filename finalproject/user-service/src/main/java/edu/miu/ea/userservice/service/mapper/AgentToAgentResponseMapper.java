package edu.miu.ea.userservice.service.mapper;

import edu.miu.ea.commons.service.mapper.BaseMapper;
import edu.miu.ea.contracts.user.AgentResponse;
import edu.miu.ea.contracts.user.UserResponse;
import edu.miu.ea.userservice.domain.Agent;
import edu.miu.ea.userservice.domain.User;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgentToAgentResponseMapper extends BaseMapper<Agent, AgentResponse> {
    @Autowired
    public AgentToAgentResponseMapper(MapperFactory mapperFactory) {
        super(mapperFactory, Agent.class, AgentResponse.class);
    }
}
