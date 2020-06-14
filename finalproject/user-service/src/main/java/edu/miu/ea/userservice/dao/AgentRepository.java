package edu.miu.ea.userservice.dao;

import edu.miu.ea.commons.repository.BaseRepository;
import edu.miu.ea.userservice.domain.Agent;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends BaseRepository<Agent, Long> {
    Agent getAgentByEmail(String email);
}
