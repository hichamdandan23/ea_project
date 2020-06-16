package edu.miu.ea.userservice.dao;

import edu.miu.ea.commons.repository.BaseRepository;
import edu.miu.ea.userservice.domain.Role;
import edu.miu.ea.userservice.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
}
