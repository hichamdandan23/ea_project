package edu.miu.ea.userservice.service;

import edu.miu.ea.userservice.domain.User;

import java.util.EnumMap;

public interface UserService {
    EnumMap<ItemType, Object> createUser(String email, String password);
    User getUserByEmail(String email);
    String generateToken(User user);
    User verifyToken(String token);
    boolean editProfile(String company, String website);
}
