package com.demos.questapp.services;

import com.demos.questapp.advice.ItemAlreadyExistException;
import com.demos.questapp.advice.ItemNotFoundException;
import com.demos.questapp.entities.User;
import com.demos.questapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public List<User> getAllUser() {
       return userRepository.findAll();
    }

    public User getOneUserById(Long id) {
        User user=userRepository.findById(id).orElseThrow(()->new ItemNotFoundException("User not found with id: "+id));
        return user;
    }

    public User createUser(User user) {
        User foundUser=userRepository.findById(user.getId()).orElse(null);
        if (foundUser !=null)
            throw new ItemAlreadyExistException("User already exist with id: "+user.getId());
        return userRepository.save(user);
    }

    public User updateUserById(User user, Long id) {
        User updateUser=userRepository.findById(id).orElseThrow(()->new ItemNotFoundException("User not found with id: "+id));
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        User deleteUser=userRepository.findById(id).orElseThrow(()->new ItemAlreadyExistException("User not found with id: "+id));
        userRepository.delete(getOneUserById(id));
    }


}
