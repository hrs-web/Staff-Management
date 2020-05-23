package cn.hrs.service.impl;

import cn.hrs.dao.UserDao;
import cn.hrs.dao.impl.UserDaoImpl;
import cn.hrs.domian.Administrator;
import cn.hrs.domian.PageBean;
import cn.hrs.domian.User;
import cn.hrs.service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();
    @Override
    public List<User> findAll() {
        // 1.调用dao完成查询
        return dao.findAll();
    }
    @Override
    public Administrator login(Administrator administrator) {
        return dao.findAdministrator(administrator.getUsername(),administrator.getPassword());
    }

    @Override
    public void addUser(User user) {
        dao.add(user);
    }

    @Override
    public void deleteUser(String id) {
        dao.delete(Integer.parseInt(id));
    }

    @Override
    public User findUserById(String id) {
        return dao.findById(Integer.parseInt(id));
    }

    @Override
    public void updateUser(User user) {
        dao.update(user);
    }

    @Override
    public void delSelectedUser(String[] ids) {
        // 判断ids是否为空
        if (ids != null && ids.length > 0){
            // 遍历数组
            for (String id : ids){
                dao.delete(Integer.parseInt(id));
            }
        }
    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String,String[]> condition) {
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);

        if (currentPage <= 0){
            currentPage = 1;
        }
        // 1.创建空的PageBean对象
        PageBean<User> pb = new PageBean<>();

        // 2.设置参数
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);

        // 3.调用dao查询总记录数
        int totalCount = dao.findTotalCount(condition);
        pb.setTotalCount(totalCount);

        // 4.调用dao查询用户数据封装list集合
        // 计算开始的记录索引
        int start = (currentPage-1) * rows;
        List<User> list = dao.findByPage(start,rows,condition);
        pb.setList(list);

        // 5.计算总页码
        int totalPage = (totalCount % rows) == 0 ? (totalCount/rows) : (totalCount/rows) + 1;
        pb.setTotalPage(totalPage);

        return pb;
    }

}
