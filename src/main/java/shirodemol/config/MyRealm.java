package shirodemol.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import shirodemol.domain.User;
import shirodemol.service.IUserService;

@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private IUserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken upt = (UsernamePasswordToken) authenticationToken;//获取前台传过来的用户名，密码等参数
        String username = upt.getUsername();//获取用户名，可以到数据库查询到相应用户返回
        User user = userService.findByUserName(username);
        if(user == null){
            return null;
        }
        String hashedCredentials = user.getPassword();
        ByteSource salt = ByteSource.Util.bytes("CGC_crm");//盐值
        return new SimpleAuthenticationInfo(username, hashedCredentials, salt, getName());
    }

    /*    public static void main(String[] args) {
            String algorithmName = "MD5"; //加密算法
            String password = "123";//要加密的内容
            int hashIterations =10000;//重复加密次数
            ByteSource salt = ByteSource.Util.bytes("CGC_crm");//盐值
            SimpleHash simpleHash = new SimpleHash(algorithmName,password,salt,hashIterations);
            String md5Password = simpleHash.toString();//加密以后得到密码
            System.out.println(md5Password);
        }*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();//拿到身份标识就是上面的principal
        //todo 通过该身份标识拿到该用户所有权限
        //模拟从数据库拿到该用户权限
        List<String> userPermissions = Collections.singletonList("user:save");
        //List<String> userPermissions = new ArrayList<>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(userPermissions);
        return info;
    }
}
