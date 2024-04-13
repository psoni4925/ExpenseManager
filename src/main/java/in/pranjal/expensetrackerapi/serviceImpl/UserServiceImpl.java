package in.pranjal.expensetrackerapi.serviceImpl;

import in.pranjal.expensetrackerapi.entity.User;
import in.pranjal.expensetrackerapi.entity.UserModel;
import in.pranjal.expensetrackerapi.exception.ItemAlreadyExistException;
import in.pranjal.expensetrackerapi.repository.UserRepository;
import in.pranjal.expensetrackerapi.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private UserRepository userRepository;
    @Override
    public User createUser(UserModel user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new ItemAlreadyExistException(("User is already register with email:"+ user.getEmail()));
        }
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public User readUser() {
        Long userId = getLoggedInUser().getId();
       return userRepository.findById(userId).orElseThrow( () -> new ResourceAccessException("user not found for the id:"+ userId));
    }

    @Override
    public User updateUser(UserModel userModel) {
        User existingUser = readUser();
        existingUser.setName(userModel.getName() != null ? userModel.getName() : existingUser.getName());
        existingUser.setEmail(userModel.getEmail() != null ? userModel.getEmail() : existingUser.getEmail());
        existingUser.setPassword(userModel.getPassword() != null ? bcryptEncoder.encode(userModel.getPassword()) : existingUser.getPassword());
        existingUser.setAge(userModel.getAge() != null ? userModel.getAge() : existingUser.getAge());
        return userRepository.save(existingUser);


    }

    @Override
    public void deleteuser() {
        User user = readUser();
        userRepository.delete(user);
    }

    @Override
    public User getLoggedInUser() {

       Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();

       String email = authentication.getName();

       return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with given email is not found"+ email));
    }
}
