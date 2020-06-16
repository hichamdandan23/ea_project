package edu.miu.ea.userservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.miu.ea.commons.service.BaseReadWriteServiceImpl;
import edu.miu.ea.contracts.Code;
import edu.miu.ea.contracts.user.AgentResponse;
import edu.miu.ea.contracts.user.UserResponse;
import edu.miu.ea.userservice.dao.AgentRepository;
import edu.miu.ea.userservice.domain.Agent;
import edu.miu.ea.userservice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;

@Service
@Transactional
public class AgentServiceImpl extends BaseReadWriteServiceImpl<AgentResponse, Agent, Long> implements AgentService {
    @Autowired
    private AgentRepository agentRepository;

    @Value("${user-service.token-secret}")
    private String tokenSecret;

    @Override
    public EnumMap<ItemType, Object> create(Agent agent) {
        EnumMap<ItemType, Object> result = new EnumMap<>(ItemType.class);
        result.put(ItemType.Code, Code.Success);
        result.put(ItemType.Msg, "");
        result.put(ItemType.User, null);

        Agent foundAgent = getAgentByEmail(agent.getEmail());
        if (foundAgent != null) {
            result.put(ItemType.Code, Code.AlreadyExists);
            result.put(ItemType.Msg, "Email already exists");
            return result;
        }

        agentRepository.save(agent);
        result.put(ItemType.User, agent);

        return result;
    }

    @Override
    public Agent getAgentByEmail(String email) {
        Agent agent = agentRepository.getAgentByEmail(email);
        return agent;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String generateToken(Agent agent) {
        String token = "";
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            token = JWT.create()
                    .withClaim("id", agent.getId())
                    .withClaim("email", agent.getEmail())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }

        return token;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Agent verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            if (jwt == null) {
                return null;
            }

            Long agentId = jwt.getClaim("id").asLong();
            Agent agent = agentRepository.findById(agentId).get();
            return agent;
        } catch (JWTVerificationException exception) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
