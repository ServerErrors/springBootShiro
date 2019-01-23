package shirodemol.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(String username, String password) {
        //获取subject对象
        Subject subject = SecurityUtils.getSubject();
        //判断是否已经登录
        if (!subject.isAuthenticated()) {
            try {
                //没有登录，将username，password封装到token对象中
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                subject.login(token);
                return "main";
            } catch (UnknownAccountException e) {
                System.out.println("用户不存在");
                e.printStackTrace();
            } catch (IncorrectCredentialsException e) {
                System.out.println("密码错误！");
                e.printStackTrace();
            } catch (LockedAccountException e) {
                System.out.println("用户被锁定错误！");
                e.printStackTrace();
            } catch (AuthenticationException e) {
                System.out.println("系统错误！");
                e.printStackTrace();
            }
        }
        return "login";
    }
    @RequestMapping("/logout")
    public String logout(){
        return "login";
    }
}
