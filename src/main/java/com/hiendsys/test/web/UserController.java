package com.hiendsys.test.web;

import com.hiendsys.test.dao.entity.UserAccount;
import com.hiendsys.test.dao.repository.UserRepository;
import com.hiendsys.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    private BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @RequestMapping(value = "/login")
    public String loginPage() {
        return "login";
    }


    @RequestMapping(path = "/user")
    public String getUsers(Model model) {
        List<UserAccount> users = userService.getAllUsers();
        model.addAttribute("user", users);
        return "userList";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String editForm(ModelMap model, HttpServletResponse resp, HttpServletRequest req) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        /*model.addAttribute("newUser", new UserAccount());*/
        return "addUser";
    }

    @RequestMapping(value = {"/{id}/edit"}, method = RequestMethod.GET)
    public String editUser(@PathVariable Long id, ModelMap model, Principal principal) throws IOException {
        model.addAttribute("user", userService.getByUsername(principal.getName()));
        return "edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String saveNewUser(@Valid @ModelAttribute("user") UserAccount user, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                String fieldName = error.getField();
                if (!fieldName.equals("username") && !fieldName.equals("password")) {
                    return "edit";
                }
            }
        }
        user.setId(userService.getByUsername(principal.getName()).getId());
        user.setIsActive(true);
        user.setPassword(user.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        userService.updateUser(user);
        return "redirect:/user";
    }

    @RequestMapping(value = "/user/new", method = RequestMethod.POST)
    public String addNewUser(@Valid @ModelAttribute("userForm") UserAccount user, BindingResult result, ModelMap model) throws IOException {
        if (result.hasErrors()) {
            return "addUser";
        }
        user.setUsername(user.getUsername());
        user.setPassword((user.getPassword()));
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setRole(user.getRole());
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());

        userService.addNewUser(user);
        return "redirect:/user";
    }

    @RequestMapping(value = "/{id}/block", method = RequestMethod.POST)
    public String blockUser(@PathVariable Long userId, ModelMap model) throws IOException {
        userService.blockUser(userId);
        model.addAttribute("users", userService.getAllUsers());
        return "userList";
    }

    @RequestMapping(value = "/{id}/unblock", method = RequestMethod.POST)
    public String unblockUser(@PathVariable Long userId, ModelMap model) throws IOException {
        userService.unblockUser(userId);
        model.addAttribute("users", userService.getAllUsers());
        return "userList";
    }

}
