package com.user.lms.domain;

import com.user.lms.entity.Role;
import com.user.lms.entity.User;
import com.user.lms.entity.UserRoles;
import com.user.lms.models.LaborerModel;
import com.user.lms.repository.RoleRepository;
import com.user.lms.repository.UserRepository;
import com.user.lms.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LaborersService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private UserRoleRepository userRoleRepository;

    @Transactional
    public void deleteUser(Long userId) {
        this.userRoleRepository.deleteUserRolesByUserId(userId);
        this.userRepository.deleteById(userId);
    }


    @Transactional
    public User addLabour(LaborerModel laborer){
        Role role = this.roleRepository.findByName("LABOUR");
        User labour = new User();
        labour.setLastName(laborer.getLastName());
        labour.setFirstName(laborer.getFirstName());
        labour.setEmail(laborer.getEmail());
        labour.setMobileNo(laborer.getMobileNumber());
        labour.setIsVerified(true);
        labour.setIsApproved(true);

        labour = this.userRepository.saveAndFlush(labour);
        UserRoles userRoles = new UserRoles();
        userRoles.setRole(role);
        userRoles.setUser(labour);
        this.userRoleRepository.saveAndFlush(userRoles);

        return  labour;
    }

    @Transactional
    public String editLabour(LaborerModel laborer){
        Optional<User> optionalUser =  this.userRepository.findById(laborer.getId());
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setMobileNo(laborer.getMobileNumber());
            user.setFirstName(laborer.getFirstName());
            user.setLastName(laborer.getLastName());
            user.setEmail(laborer.getEmail());
            this.userRepository.saveAndFlush(user);
        }
        return "Updated";
    }

    public LaborerModel getLabourById(String id){
        Optional<User> optionalUser =  this.userRepository.findById(Long.parseLong(id));
        if(optionalUser.isPresent()){
           User user =   optionalUser.get();
           LaborerModel laborerModel = new LaborerModel();
           laborerModel.setId(user.getId());
           laborerModel.setEmail(user.getEmail());
           laborerModel.setMobileNumber(user.getMobileNo());
           laborerModel.setLastName(user.getLastName());
           laborerModel.setFirstName(user.getFirstName());
           return  laborerModel;
        }
        return null;
    }
}
