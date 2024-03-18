package com.user.lms.controller;

import com.user.lms.domain.AuthService;
import com.user.lms.domain.UsersService;
import com.user.lms.entity.User;
import com.user.lms.models.UserDetailsModel;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class UserRestApiController {
    @Autowired
    private UsersService usersService;


    @GetMapping("/users/admin")
    @ResponseBody
    public List<UserDetailsModel> loadAllUsersForAdmin(){
      return this.usersService.loadAllUsersForAdmin();
    }

    @GetMapping("/truckProvider/admin")
    @ResponseBody
    public List<UserDetailsModel> loadAllTruckProviderForAdmin(){
        return this.usersService.loadAllTruckProviderForAdmin();
    }



}
