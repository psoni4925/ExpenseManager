package in.pranjal.expensetrackerapi.service;

import in.pranjal.expensetrackerapi.entity.User;
import in.pranjal.expensetrackerapi.entity.UserModel;

public interface UserService {

    User createUser(UserModel user);

    User readUser();

    User updateUser(UserModel userModel);

    void deleteuser();

    User getLoggedInUser();
}
