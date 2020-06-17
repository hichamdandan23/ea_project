package edu.miu.ea.userservice.controller;

import edu.miu.ea.commons.controller.BaseReadWriteController;
import edu.miu.ea.contracts.Code;
import edu.miu.ea.contracts.Response;
import edu.miu.ea.contracts.user.RoleCreateRequest;
import edu.miu.ea.contracts.user.RoleResponse;
import edu.miu.ea.contracts.user.UserCreateRequest;
import edu.miu.ea.userservice.domain.Role;
import edu.miu.ea.userservice.domain.User;
import edu.miu.ea.userservice.service.ItemType;
import edu.miu.ea.userservice.service.RoleService;
import edu.miu.ea.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.EnumMap;

@RestController
@RequestMapping("/admin/role")
public class RoleAdminController extends BaseReadWriteController<RoleResponse, Role, Long> {
    @Autowired
    private RoleService roleService;

    @PostMapping("/create")
    public Response create(RoleCreateRequest roleCreateRequest) {
        Role role = new Role();
        role.setName(roleCreateRequest.getName());

        EnumMap result = roleService.create(role);
        Code code = (Code) result.get(ItemType.Code);
        String msg = (String) result.get(ItemType.Msg);
        if(code != Code.Success) {
            return new Response(code, msg);
        }

        return new Response(Code.Success, "");
    }
}
