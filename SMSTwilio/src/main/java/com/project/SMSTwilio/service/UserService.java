package com.project.SMSTwilio.service;

import com.project.SMSTwilio.entity.User;
import com.project.SMSTwilio.payload.request.UserRequest;
import com.project.SMSTwilio.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
public class UserService {

    @Setter
    private boolean logged = false;

    @Autowired
    UserRepository userRepository;

    public boolean loginControl(UserRequest userRequest) {
        User user = userRepository.findByUsernameAndPassword(userRequest.getUsername(), userRequest.getPassword());
        return user != null;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public boolean save(UserRequest userRequest) {
        User user = new User();
        User u = userRepository.findByUsername(userRequest.getUsername());
        if (u == null) {
            user.setUsername(userRequest.getUsername());
            user.setPassword(userRequest.getPassword());
            user.setTelephoneNumber(userRequest.getTelephoneNumber());
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
