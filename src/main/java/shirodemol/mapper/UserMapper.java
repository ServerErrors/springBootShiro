package shirodemol.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

import shirodemol.domain.User;
@Mapper
public interface UserMapper{
    User findByUserName(String name);
    void save (User user);
    List<User> loadAll();
}
