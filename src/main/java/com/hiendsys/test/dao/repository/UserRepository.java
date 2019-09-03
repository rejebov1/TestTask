package com.hiendsys.test.dao.repository;

import com.hiendsys.test.dao.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAccount, Long> {

    UserAccount getByUsername(String username);

    boolean existsByUsername(String username);
}
