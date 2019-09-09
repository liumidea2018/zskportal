package com.haohe.zskportal.web;

import com.haohe.zskportal.sys.model.User;
import com.haohe.zskportal.sys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public User query(@PathVariable("id") long id){
        log.debug("日志。。。");
        return userService.getById(id);
    }


}
