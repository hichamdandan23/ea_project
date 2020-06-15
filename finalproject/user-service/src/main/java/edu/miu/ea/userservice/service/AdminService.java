package edu.miu.ea.userservice.service;

import edu.miu.ea.commons.service.BaseReadWriteService;
import edu.miu.ea.contracts.user.AdminResponse;
import edu.miu.ea.userservice.domain.Admin;
import edu.miu.ea.userservice.domain.Agent;

import java.util.EnumMap;

public interface AdminService extends BaseReadWriteService<AdminResponse, Admin, Long> {
    EnumMap<ItemType, Object> create(Admin admin);

    Admin getAdminByEmail(String email);

    String generateToken(Admin agent);

    Admin verifyToken(String token);
}
