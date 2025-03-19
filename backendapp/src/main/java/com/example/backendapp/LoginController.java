package com.example.backendapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping(value = "/test", params = {"userName", "password"})
    public LoginResponse getUserById(
                               @RequestParam(name = "userName") String userName,
                               @RequestParam(name = "password") String plainPassword) {
        List<User> userRepo= userRepository.findAll();
        var user = userRepo.stream().filter(userdata -> userdata.getUserName().equals(userName)).findFirst();

        var passwordUser=user.map(Value-> Value.getPassword()).get();

        if (passwordEncoder.matches(plainPassword,passwordUser)) {
            return new LoginResponse(user.get().getUserName(), user.get().getRole());
        }

        return new LoginResponse();
    }


}
