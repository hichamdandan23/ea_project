package edu.miu.ea.userservice.service;

import edu.miu.ea.commons.service.BaseReadWriteService;
import edu.miu.ea.contracts.user.RoleResponse;
import edu.miu.ea.userservice.domain.Role;
import edu.miu.ea.userservice.domain.User;

import java.util.EnumMap;

public interface RoleService extends BaseReadWriteService<RoleResponse, Role, Long> {
    EnumMap<ItemType, Object> create(Role role);
    Role getRoleByName(String name);
}
