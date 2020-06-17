package edu.miu.ea.userservice.service;

import edu.miu.ea.commons.service.BaseReadWriteService;
import edu.miu.ea.contracts.user.UserResponse;
import edu.miu.ea.userservice.domain.User;

import java.util.EnumMap;
import java.util.List;
import java.util.Set;

public interface UserService extends BaseReadWriteService<UserResponse, User, Long> {
    EnumMap<ItemType, Object> create(User user);

    User getUserByEmail(String email);

    String generateToken(User user);

    User verifyToken(String token);

    boolean editProfile(String company, String website);

    boolean setRole(Long userId, List<String> roles);
}
