package in.pranjal.expensetrackerapi.controller;

import in.pranjal.expensetrackerapi.entity.AuthModel;
import in.pranjal.expensetrackerapi.entity.JwtResponse;
import in.pranjal.expensetrackerapi.entity.User;
import in.pranjal.expensetrackerapi.entity.UserModel;
import in.pranjal.expensetrackerapi.security.CustomUserDetailService;
import in.pranjal.expensetrackerapi.service.UserService;
import in.pranjal.expensetrackerapi.util.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<User> save(@Valid @RequestBody UserModel user){
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthModel authModel) throws Exception{
       // Authentication authentication =

        authenticate(authModel.getEmail() , authModel.getPassword());

        //We need to generate the JWT Token

        final UserDetails userDetails = userDetailService.loadUserByUsername(authModel.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        //SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<JwtResponse>(new JwtResponse(token),HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        }
        catch(DisabledException e){
                 throw new Exception("User disabled");
        }
        catch(BadCredentialsException e){
            throw new Exception("Bad Credential");
        }

    }
}
