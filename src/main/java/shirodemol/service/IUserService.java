package shirodemol.service;

import java.util.List;

import shirodemol.domain.User;

public interface IUserService {
    User findByUserName(String name);
    void save(User user);
    List<User> loadAll();
}
