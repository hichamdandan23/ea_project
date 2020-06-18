package edu.miu.ea.userservice.service.mapper;

import edu.miu.ea.commons.service.mapper.BaseMapper;
import edu.miu.ea.contracts.user.RoleResponse;
import edu.miu.ea.userservice.domain.Role;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RoleResponseToRoleMapper extends BaseMapper<RoleResponse, Role> {
    @Autowired
    public RoleResponseToRoleMapper(MapperFactory mapperFactory) {
        super(mapperFactory, RoleResponse.class, Role.class);
    }
}
