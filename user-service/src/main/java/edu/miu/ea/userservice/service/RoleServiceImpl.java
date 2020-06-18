package edu.miu.ea.userservice.service;

import edu.miu.ea.commons.service.BaseReadWriteServiceImpl;
import edu.miu.ea.contracts.user.RoleResponse;
import edu.miu.ea.userservice.domain.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends BaseReadWriteServiceImpl<RoleResponse, Role, Long> implements RoleService{
}
