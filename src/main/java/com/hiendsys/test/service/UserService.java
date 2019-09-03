package com.hiendsys.test.service;

import com.hiendsys.test.dao.entity.UserAccount;

import java.util.List;

public interface UserService {

    UserAccount getUserById(Long id);

    UserAccount getByUsername(String username);

    List<UserAccount> getAllUsers();

    void addNewUser(UserAccount user);

    UserAccount updateUser(UserAccount user);

    void blockUser(Long id);

    void unblockUser(Long id);

    boolean isUsernameExist(String email);

}
