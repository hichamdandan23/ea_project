package edu.miu.ea.userservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.miu.ea.commons.service.BaseReadWriteServiceImpl;
import edu.miu.ea.contracts.Code;
import edu.miu.ea.contracts.user.UserResponse;
import edu.miu.ea.userservice.dao.RoleRepository;
import edu.miu.ea.userservice.dao.UserRepository;
import edu.miu.ea.userservice.domain.Role;
import edu.miu.ea.userservice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl extends BaseReadWriteServiceImpl<UserResponse, User, Long> implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Value("${user-service.token-secret}")
    private String tokenSecret;

    @Override
    public EnumMap<ItemType, Object> create(User user) {
        EnumMap<ItemType, Object> result = new EnumMap<>(ItemType.class);
        result.put(ItemType.Code, Code.Success);
        result.put(ItemType.Msg, "");
        result.put(ItemType.User, null);

        User foundUser = getUserByEmail(user.getEmail());
        if (foundUser != null) {
            result.put(ItemType.Code, Code.AlreadyExists);
            result.put(ItemType.Msg, "Email already exists");
            return result;
        }

        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        result.put(ItemType.User, user);

        return result;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.getUserByEmail(email);
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String generateToken(User user) {
        String token = "";
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            token = JWT.create()
                    .withClaim("id", user.getId())
                    .withClaim("email", user.getEmail())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }

        return token;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            if (jwt == null) {
                return null;
            }

            Long userId = jwt.getClaim("id").asLong();
            User user = userRepository.findById(userId).get();
            return user;
        } catch (JWTVerificationException exception) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean editProfile(String company, String website) {
        return false;
    }

    @Override
    public boolean setRole(Long userId, List<String> roles) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            if(user == null) {
                return false;
            }

            user.getRoles().clear();

            for (String roleStr: roles) {
                Role role = roleRepository.getRoleByName(roleStr);
                if(role != null) {
                    user.getRoles().add(role);
                }
            }

            userRepository.save(user);

            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
