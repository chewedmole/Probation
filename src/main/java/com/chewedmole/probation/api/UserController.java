package com.chewedmole.probation.api;

import com.chewedmole.probation.dto.RqChangeName;
import com.chewedmole.probation.dto.RqCreateUser;
import com.chewedmole.probation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("create-user")
    public ResponseEntity<?> createUser(RqCreateUser rq){
        return userService.createUser(rq);
    }

    @GetMapping("find-user-by-id")
    public ResponseEntity<?> findUserById(Long id){
        return userService.findUserById(id);
    }

    @GetMapping("get-all-users")
    public ResponseEntity<?> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam String email, @RequestParam String password){
        return userService.deleteUser(email, password);
    }

    @PostMapping("change-name")
    public ResponseEntity<?> changeName(@RequestParam String email, @RequestParam String password, @RequestBody RqChangeName rq){
        return userService.changeName(email, password, rq);
    }
}
