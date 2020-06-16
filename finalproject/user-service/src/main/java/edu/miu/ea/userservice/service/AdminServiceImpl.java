package edu.miu.ea.userservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.miu.ea.commons.service.BaseReadWriteServiceImpl;
import edu.miu.ea.contracts.Code;
import edu.miu.ea.contracts.user.AdminResponse;
import edu.miu.ea.contracts.user.AgentResponse;
import edu.miu.ea.userservice.dao.AdminRepository;
import edu.miu.ea.userservice.domain.Admin;
import edu.miu.ea.userservice.domain.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;

@Service
@Transactional
public class AdminServiceImpl extends BaseReadWriteServiceImpl<AdminResponse, Admin, Long> implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Value("${user-service.token-secret}")
    private String tokenSecret;

    @Override
    public EnumMap<ItemType, Object> create(Admin admin) {
        EnumMap<ItemType, Object> result = new EnumMap<>(ItemType.class);
        result.put(ItemType.Code, Code.Success);
        result.put(ItemType.Msg, "");
        result.put(ItemType.User, null);

        Admin foundAdmin = getAdminByEmail(admin.getEmail());
        if (foundAdmin != null) {
            result.put(ItemType.Code, Code.AlreadyExists);
            result.put(ItemType.Msg, "Email already exists");
            return result;
        }

        adminRepository.save(admin);
        result.put(ItemType.User, admin);

        return result;
    }

    @Override
    public Admin getAdminByEmail(String email) {
        Admin admin = adminRepository.getAdminByEmail(email);
        return admin;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String generateToken(Admin agent) {
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
    public Admin verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            if (jwt == null) {
                return null;
            }

            Long agentId = jwt.getClaim("id").asLong();
            Admin admin = adminRepository.findById(agentId).get();
            return admin;
        } catch (JWTVerificationException exception) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
