package edu.miu.ea.userservice.controller;


import edu.miu.ea.commons.controller.BaseReadWriteController;
import edu.miu.ea.contracts.Code;
import edu.miu.ea.contracts.Response;
import edu.miu.ea.contracts.user.UserCreateRequest;
import edu.miu.ea.contracts.user.UserResponse;
import edu.miu.ea.userservice.domain.User;
import edu.miu.ea.userservice.service.ItemType;
import edu.miu.ea.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.EnumMap;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController extends BaseReadWriteController<UserResponse, User, Long> {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Response create(UserCreateRequest userCreateRequest) {
        if(userCreateRequest.getEmail() == null || userCreateRequest.getPassword() == null) {
            return new Response(Code.ParamError, "Param error");
        }

        User user = new User();
        user.setEmail(userCreateRequest.getEmail());
        user.setPassword(userCreateRequest.getPassword());
        user.setCity(userCreateRequest.getCity());
        user.setCreatedAt(LocalDateTime.now());
        user.setDateOfBirth(userCreateRequest.getDateOfBirth());

        user.setState(userCreateRequest.getState());
        user.setState(userCreateRequest.getStreet());
        user.setZip(userCreateRequest.getZip());
        user.setFirstName(userCreateRequest.getFirstName());
        user.setLastName(userCreateRequest.getLastName());

        EnumMap result = userService.create(user);
        Code code = (Code) result.get(ItemType.Code);
        String msg = (String) result.get(ItemType.Msg);
        if(code != Code.Success) {
            return new Response(code, msg);
        }

        return new Response(Code.Success, "");
    }
}