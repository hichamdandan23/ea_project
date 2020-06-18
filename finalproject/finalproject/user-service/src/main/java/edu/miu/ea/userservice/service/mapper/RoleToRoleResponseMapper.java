package edu.miu.ea.userservice.service.mapper;

import edu.miu.ea.commons.service.mapper.BaseMapper;
import edu.miu.ea.contracts.user.RoleResponse;
import edu.miu.ea.userservice.domain.Role;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleToRoleResponseMapper extends BaseMapper<Role, RoleResponse> {
    @Autowired
    public RoleToRoleResponseMapper(MapperFactory mapperFactory) {
        super(mapperFactory, Role.class, RoleResponse.class);
    }
}
