package al.polis.appserver.controller;

import al.polis.appserver.dto.LoginRequestDto;
import al.polis.appserver.dto.LoginResponseDto;
import al.polis.appserver.model.User;
import al.polis.appserver.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto login) {
        String enteredUsername = login.getUsername().trim();
        String enteredPassword = login.getPassword().trim();

        Optional<User> userOpt = userRepo.findByUsername(enteredUsername);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(enteredPassword)) {
                return ResponseEntity.ok(new LoginResponseDto("success", user.getRole()));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
