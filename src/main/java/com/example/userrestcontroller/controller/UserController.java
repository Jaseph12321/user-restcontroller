package com.example.userrestcontroller.controller;


import com.example.userrestcontroller.controller.response.UserNotFoundException;
import com.example.userrestcontroller.model.entity.User;
import com.example.userrestcontroller.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    //Get all users
    @GetMapping("/users")
    String getUser(Model model) {
        List<User> userList = this.userService.getUserList();
        model.addAttribute("userList", userList);
        return "user";
    }

    //Get one specific user
    @GetMapping("/search")
    String getUser(Model model, @RequestParam int id) {
        User userList = this.userService.getUserByName(id);
        model.addAttribute("userList", userList);
        return "user";
    }


    // create new user
    @GetMapping("/users/new")
    public String showNewForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Add new user");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute User user) {
        this.userService.saveUser(user);
        return "redirect:/users";
    }

    // update one user
    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        try {
            User user = this.userService.get(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit user");

            return "edit_user_form";
        } catch (UserNotFoundException e) {
            return "redirect:/users";
        }
    }

    @PutMapping("/users/update")
    public String updateUser(@ModelAttribute User user) {
        this.userService.updateUser(user);
        return "redirect:/users";
    }

    //delete user
    @GetMapping("/users/delete/{id}")    // confirm if or not to delete
    public String showDeleteForm(@PathVariable("id") int id, Model model) {
        try {
            User user = this.userService.get(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Delete user");

            return "delete_user_form";
        } catch (UserNotFoundException e) {
            return "redirect:/users";
        }
    }

    @DeleteMapping("/users/delete")  // confirm to delete
    public String deleteUser(@ModelAttribute User user) {
        this.userService.delete(user.getId());
        return "redirect:/users";
    }

}