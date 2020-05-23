package cn.hrs.dao;

import cn.hrs.domian.Administrator;
import cn.hrs.domian.User;

import java.util.List;
import java.util.Map;

/**
 * 用户操作的dao
 */
public interface UserDao {
    List<User> findAll();

    Administrator findAdministrator(String username,String password);


    void add(User user);

    void delete(int parseInt);

    User findById(int parseInt);

    void update(User user);

    int findTotalCount(Map<String,String[]> condition);

    List<User> findByPage(int start, int rows, Map<String, String[]> condition);
}
