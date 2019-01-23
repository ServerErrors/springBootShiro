package shirodemol.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfigration {
    //设置比对器
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");//散列算法:MD2、MD5、SHA-1、SHA-256、SHA-384、SHA-512等。
        hashedCredentialsMatcher.setHashIterations(10000);//散列的次数，默认1次， 设置两次相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }

    //将自己的验证方式加入容器
    @Bean
    public MyRealm myShiroRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myRealm;
    }

    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager(HashedCredentialsMatcher hashedCredentialsMatcher) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm(hashedCredentialsMatcher));
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String,String> map = new HashMap<String, String>();
        //登录页面可以匿名访问
        map.put("/login.html", "anon");
        //登录处理也需要匿名访问
        map.put("/login/login", "anon");
        //不需要权限的url可以匿名访问
        map.put("/user/index", "anon");
        //不需要权限的页面可以匿名访问
        map.put("/anon.html", "anon");
        //登出
        map.put("/login/logout","logout");
        //对应的url需要相关权限
        //map.put("/user/save", "perms[user:save]");
        //对所有用户认证
        map.put("/**","authc");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/main.html");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/unpower.html");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    //加入注解的使用
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
