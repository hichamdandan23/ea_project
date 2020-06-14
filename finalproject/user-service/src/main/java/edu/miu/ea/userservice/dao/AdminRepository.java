package edu.miu.ea.userservice.dao;

import edu.miu.ea.commons.repository.BaseRepository;
import edu.miu.ea.userservice.domain.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends BaseRepository<Admin, Long> {
    Admin getAdminByEmail(String email);
}
