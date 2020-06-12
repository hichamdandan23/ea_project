package edu.miu.ea.userservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.miu.ea.commons.contracts.Code;
import edu.miu.ea.userservice.dao.UserRepository;
import edu.miu.ea.userservice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Value("${user-service.token-secret}")
    private String tokenSecret;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public EnumMap<ItemType, Object> createUser(String email, String password) {
        EnumMap<ItemType, Object> result = new EnumMap<>(ItemType.class);
        result.put(ItemType.Code, Code.Success);
        result.put(ItemType.Msg, "");
        result.put(ItemType.User, null);

        User user = getUserByEmail(email);
        if(user != null) {
            result.put(ItemType.Code, Code.AlreadyExists);
            result.put(ItemType.Msg, "Email already exists");
            return result;
        }

        user = new User(email, password);
        userRepository.save(user);
        result.put(ItemType.User, user);

        return  result;
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
        } catch (JWTCreationException exception){
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
            if(jwt == null) {
                return null;
            }

            Long userId = jwt.getClaim("id").asLong();
            User user = userRepository.findById(userId).get();
            return user;
        } catch (JWTVerificationException exception){
            return null;
        }
    }

    @Override
    public boolean editProfile(String company, String website) {
        return false;
    }
}
