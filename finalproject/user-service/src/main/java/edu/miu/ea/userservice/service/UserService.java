package edu.miu.ea.userservice.service;

import edu.miu.ea.commons.service.BaseReadWriteService;
import edu.miu.ea.contracts.user.UserResponse;
import edu.miu.ea.userservice.domain.User;

import java.util.EnumMap;

public interface UserService extends BaseReadWriteService<UserResponse, User, Long> {
    EnumMap<ItemType, Object> createUser(String email, String password);

    User getUserByEmail(String email);

    String generateToken(User user);

    User verifyToken(String token);

    boolean editProfile(String company, String website);
}
