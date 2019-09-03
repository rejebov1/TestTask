package com.hiendsys.test.persistanceTest;

import com.hiendsys.test.dao.entity.UserAccount;
import com.hiendsys.test.dao.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserPersistenceTest {

    @Autowired
    private UserRepository userRepository;

    @Bean
    BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Test
    public void addNewUser(){
        UserAccount userAccount = UserAccount.builder()
                .username("Username_5")
                .firstName("First_5")
                .lastName("Last_5")
                .password(getEncoder().encode("1234"))
                .role(UserAccount.Role.ADMIN)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.saveAndFlush(userAccount);
        List<UserAccount> userIsPresent = userRepository.findAll();
        assertThat(userIsPresent.contains(userAccount)).isFalse();
    }
}
