package cn.hrs.service;

import cn.hrs.domian.Administrator;
import cn.hrs.domian.PageBean;
import cn.hrs.domian.User;

import java.util.List;
import java.util.Map;

/**
 * 用户管理接口
 */
public interface UserService {
    /**
     * 查询所有用户信息
     * @return
     */
    List<User> findAll();

    /**
     * 登录方法
     * @param administrator
     * @return
     */
    Administrator login(Administrator administrator);

    /**
     * 添加用户
     */
    void addUser(User user);

    /**
     * 删除用户
     * @param id
     */
    void deleteUser(String id);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    User findUserById(String id);

    /**
     * 修改用户
     * @param user
     */
    void updateUser(User user);

    /**
     *选中删除
     * @param ids
     */
    void delSelectedUser(String[] ids);

    /**
     * 分页条件查询
     * @param currentPage
     * @param rows
     * @return
     */
    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);
}
