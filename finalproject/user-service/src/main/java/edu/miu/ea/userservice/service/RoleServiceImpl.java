package edu.miu.ea.userservice.service;

import edu.miu.ea.commons.service.BaseReadWriteServiceImpl;
import edu.miu.ea.contracts.Code;
import edu.miu.ea.contracts.user.RoleResponse;
import edu.miu.ea.userservice.dao.RoleRepository;
import edu.miu.ea.userservice.dao.UserRepository;
import edu.miu.ea.userservice.domain.Role;
import edu.miu.ea.userservice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;

@Service
@Transactional
public class RoleServiceImpl extends BaseReadWriteServiceImpl<RoleResponse, Role, Long> implements RoleService{
    @Autowired
    private RoleRepository roleRepository;

    public EnumMap<ItemType, Object> create(Role role) {
        EnumMap<ItemType, Object> result = new EnumMap<>(ItemType.class);
        result.put(ItemType.Code, Code.Success);
        result.put(ItemType.Msg, "");
        result.put(ItemType.User, null);

        Role foundRole = getRoleByName(role.getName());
        if (foundRole != null) {
            result.put(ItemType.Code, Code.AlreadyExists);
            result.put(ItemType.Msg, "Role name already exists");
            return result;
        }

        roleRepository.save(role);
        result.put(ItemType.User, role);

        return result;
    }

    public Role getRoleByName(String name){
        return roleRepository.getRoleByName(name);
    }
}
