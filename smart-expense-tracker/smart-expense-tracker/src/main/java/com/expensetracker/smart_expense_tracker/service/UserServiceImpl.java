package com.expensetracker.smart_expense_tracker.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.expensetracker.smart_expense_tracker.dto.SignUpRequest;
import com.expensetracker.smart_expense_tracker.model.User;
import com.expensetracker.smart_expense_tracker.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo,PasswordEncoder passwordEncoder){
        this.userRepo=userRepo;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public void registerUser(SignUpRequest signUpRequest){
        if(userRepo.existsByEmail(signUpRequest.getEmail())){
            throw new RuntimeException("Email already registered.");
        }

        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        userRepo.save(user);

    }
}
