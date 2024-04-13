package in.pranjal.expensetrackerapi.security;

import in.pranjal.expensetrackerapi.entity.User;
import in.pranjal.expensetrackerapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailService implements UserDetailsService {



    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User existingUser = userRepository
                .findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found for the the email"+ email));

        return new org.springframework.security.core.userdetails.User(existingUser.getEmail(), existingUser.getPassword(),new ArrayList<>());
    }
}
