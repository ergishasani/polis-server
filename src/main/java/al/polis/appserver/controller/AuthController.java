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
        Optional<User> userOpt = userRepo.findByUsername(login.getUsername());

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(login.getPassword())) {
            return ResponseEntity.ok(new LoginResponseDto("success", userOpt.get().getRole()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
