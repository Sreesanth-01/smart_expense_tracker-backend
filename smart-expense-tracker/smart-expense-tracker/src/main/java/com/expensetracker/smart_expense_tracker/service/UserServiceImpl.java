package com.expensetracker.smart_expense_tracker.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.expensetracker.smart_expense_tracker.dto.LoginRequest;
import com.expensetracker.smart_expense_tracker.dto.SignUpRequest;
import com.expensetracker.smart_expense_tracker.model.User;
import com.expensetracker.smart_expense_tracker.repository.UserRepo;
import com.expensetracker.smart_expense_tracker.security.JwtUtil;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepo userRepo,PasswordEncoder passwordEncoder,JwtUtil jwtUtil){
        this.userRepo=userRepo;
        this.passwordEncoder=passwordEncoder;
        this.jwtUtil = jwtUtil;
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

    @Override
    public String login(LoginRequest loginRequest){

        User user = userRepo.findByEmail(loginRequest.getEmail())
                    .orElseThrow(()->new RuntimeException("Inavlid email or password"));
                
        boolean passwordMatches = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());

        if(!passwordMatches){
            throw new RuntimeException("Invalid email or password");
        }

        return jwtUtil.generateToken(loginRequest.getEmail());
    }
}
