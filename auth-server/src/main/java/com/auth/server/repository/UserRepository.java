package com.auth.server.repository;

import com.auth.server.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Created 17/03/2023 - 09:15
 * @Package com.auth.server.repository
 * @Project auth-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
