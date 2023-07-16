package com.chewedmole.probation.user.api;

import com.chewedmole.probation.user.dto.RqChangeName;
import com.chewedmole.probation.user.dto.RqCreateUser;
import com.chewedmole.probation.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/usr/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("create-user")
    public ResponseEntity<?> createUser(@RequestBody RqCreateUser rq){
        return userService.createUser(rq);
    }

    @GetMapping("find-user-by-id")
    public ResponseEntity<?> findUserById(Long id){
        return userService.findUserById(id);
    }

    @DeleteMapping("delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam String email, @RequestParam String password){
        return userService.deleteUser(email, password);
    }

    @PostMapping("change-name")
    public ResponseEntity<?> changeName(@RequestParam String email, @RequestParam String password, @RequestBody RqChangeName rq){
        return userService.changeName(email, password, rq);
    }

    @GetMapping("get-all-users")
    public ResponseEntity<?> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("validate-user-by-id")
    public Boolean validateUserById(@RequestParam Long id){
        return userService.validateUserById(id);
    }

    @GetMapping("get-user-info")
    public String getUserInfo(@RequestParam Long id){
        return userService.getUserInfo(id);
    }

    @GetMapping("get-user-password")
    public String getUserPassword(@RequestParam Long id){
        return userService.getUserPassword(id);
    }
}
