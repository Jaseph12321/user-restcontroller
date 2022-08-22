package com.example.userrestcontroller.service;

import com.example.userrestcontroller.controller.response.UserNotFoundException;
import com.example.userrestcontroller.model.entity.User;
import com.example.userrestcontroller.model.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    List<User> userList;

    @Autowired
    private UserRepository repo;

    public List<User> getUserList() {
        return (List<User>) repo.findAll();
    }

    public User getUserByName(int id) {
        return repo.searchUserById(id);
    }
    public User getUser(int id){
        Optional<User> user= repo.findById(id);

        return user.get();
    }

    public String saveUser(User user) {
        repo.save(user);

        return "OK";
    }


    public String updateUser(User user) {
        repo.updateUser(user.getName(), user.getAge(), user.getId());

        return "updated";
    }

    public User get(int id) throws UserNotFoundException{
        Optional<User> result = repo.findById(id);
        if(result.isPresent()){
            return result.get();
        }

        throw new UserNotFoundException("Could not find any user with id: "+id);

    }

    public void delete(int id) {

        repo.deleteById(id);
    }


}
