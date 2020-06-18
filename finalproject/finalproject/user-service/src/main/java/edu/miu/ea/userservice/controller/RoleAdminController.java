package edu.miu.ea.userservice.controller;

import edu.miu.ea.commons.controller.BaseReadWriteController;
import edu.miu.ea.contracts.user.RoleResponse;
import edu.miu.ea.userservice.domain.Role;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/role")
public class RoleAdminController extends BaseReadWriteController<RoleResponse, Role, Long> {
}
