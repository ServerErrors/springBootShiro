package shirodemol.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import shirodemol.domain.User;
import shirodemol.mapper.UserMapper;
import shirodemol.service.IUserService;
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String name) {
        return userMapper.findByUserName(name);
    }

    @Override
    public void save(User user) {
        userMapper.save(user);
    }

    @Override
    public List<User> loadAll() {
        return userMapper.loadAll();
    }
}
