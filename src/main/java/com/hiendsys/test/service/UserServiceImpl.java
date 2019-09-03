package com.hiendsys.test.service;

import com.hiendsys.test.dao.entity.UserAccount;
import com.hiendsys.test.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Bean
    BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Transactional
    @Override
    public UserAccount getUserById(Long id) {
        return userRepository.getOne(id);
    }

    @Transactional
    @Override
    public UserAccount getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Transactional
    @Override
    public List<UserAccount> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void addNewUser(UserAccount user) {
        user.setPassword(getEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public UserAccount updateUser(UserAccount user) {
        userRepository.getOne(user.getId());
        //возможно надо будет засетать данные на изменение
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    @Override
    public void blockUser(Long id) {
        UserAccount user = userRepository.getOne(id);
        user.setIsActive(false);

    }


    @Transactional
    @Override
    public void unblockUser(Long id) {
        UserAccount user = userRepository.getOne(id);
        user.setIsActive(true);

    }




    @Override
    public boolean isUsernameExist(String email){
        return userRepository.existsByUsername(email);
    }

}
