package in.pranjal.expensetrackerapi.controller;

import in.pranjal.expensetrackerapi.entity.User;
import in.pranjal.expensetrackerapi.entity.UserModel;
import in.pranjal.expensetrackerapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/profile")
    public ResponseEntity<User> readUser(){
        return new ResponseEntity<>(userService.readUser(),HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateUser(@RequestBody UserModel userModel){
        return new ResponseEntity<>(userService.updateUser(userModel),HttpStatus.OK);
    }

    @DeleteMapping("/deactivate")
    public ResponseEntity<HttpStatus> deleteUser(){
        userService.deleteuser();
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

}
