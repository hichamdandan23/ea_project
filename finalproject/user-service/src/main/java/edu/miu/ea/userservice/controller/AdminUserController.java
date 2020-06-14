package edu.miu.ea.userservice.controller;


import edu.miu.ea.commons.controller.BaseReadWriteController;
import edu.miu.ea.contracts.user.UserResponse;
import edu.miu.ea.userservice.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController extends BaseReadWriteController<UserResponse, User, Long> {

}