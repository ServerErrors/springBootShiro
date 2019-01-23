package shirodemol.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import shirodemol.domain.User;
import shirodemol.service.IUserService;
import shirodemol.utils.MD5Util;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/index")
    public String index() {
        return "anon";
    }

    //    @RequiresPermissions("user:save")
    @RequestMapping("/save")
    @ResponseBody
    public String save() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword(MD5Util.getMD5Password("123"));
        userService.save(user);
        return "success";
    }

    //    @RequiresPermissions("user:findAll")
    @RequestMapping("/findAll")
    @ResponseBody
    public List<User> loadAll() {
        return userService.loadAll();
    }
}
