package com.user.lms.domain;

import com.user.lms.entity.User;
import com.user.lms.models.ChangePasswordModel;
import com.user.lms.models.ChangePasswordResponseModel;
import com.user.lms.models.UserDetailsModel;
import com.user.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public int getUserCountForAdmin(){
        return (int) this.userRepository.countUsers(Long.parseLong("1"));
    }

    public int getTPCountForAdmin(){
        return (int) this.userRepository.countUsers(Long.parseLong("3"));
    }

    public int getLabourCountForAdmin(){
        return (int) this.userRepository.countUsers(Long.parseLong("4"));
    }


    public List<UserDetailsModel> loadAllUsersForAdmin(){
        List<User> users = userRepository.findAllByRoleId((long) 1);
        return users.stream().map(UserDetailsModel::fromEntity).collect(Collectors.toList());
    }

    public List<UserDetailsModel> loadAllTruckProviderForAdmin(){
        List<User> users = userRepository.findAllByRoleId((long) 3);
        return users.stream().map(UserDetailsModel::fromEntity).collect(Collectors.toList());
    }

    public int getLabourCountForTP() {
        return (int) this.userRepository.countUsers(Long.parseLong("4"));
    }


    public ChangePasswordResponseModel changePassword(Principal principal, ChangePasswordModel changePasswordModel) {
        User user = this.userRepository.findExistingUser(principal.getName());
        ChangePasswordResponseModel changePasswordResponseModel = new ChangePasswordResponseModel();
        if (user != null) {
            if (passwordEncoder.matches(changePasswordModel.getOldPassword(), user.getPassword())) {

                if (!changePasswordModel.getOldPassword().equals(changePasswordModel.getNewPassword())) {
                    user.setPassword(passwordEncoder.encode(changePasswordModel.getNewPassword()));
                    userRepository.save(user);
                } else {
                    changePasswordResponseModel.setIsError(true);
                    changePasswordResponseModel.setErrorMessage("New password should be different from the old password ");
                    changePasswordResponseModel.setIsOldError(false);
                    return changePasswordResponseModel;
                }
            } else {
                changePasswordResponseModel.setIsError(true);
                changePasswordResponseModel.setErrorMessage("Old password is incorrect");
                changePasswordResponseModel.setIsOldError(true);
                return changePasswordResponseModel;
            }
        }
        return changePasswordResponseModel;
    }
}
