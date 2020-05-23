package cn.hrs.dao.impl;

import cn.hrs.dao.UserDao;
import cn.hrs.domian.Administrator;
import cn.hrs.domian.User;
import cn.hrs.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSoure());

    @Override
    public List<User> findAll() {
        String sql = "select * from user";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public Administrator findAdministrator(String username, String password) {
        try {
            String sql = "select * from administrator where username = ? and password = ?";
            Administrator administrator = template.queryForObject(sql, new BeanPropertyRowMapper<Administrator>(Administrator.class),
                    username,password);
            return administrator;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void add(User user) {
        String sql = "insert into user values(null,?,?,?,?,?,?)";
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail());
    }

    @Override
    public void delete(int parseInt) {
        // 1.定义sql
        String sql = "delete from user where id = ?";
        // 2.执行sql
        template.update(sql,parseInt);
    }

    @Override
    public User findById(int parseInt) {
        // 1.定义sql
        String sql = "select * from user where id = ?";
        // 2.执行并返回sql
        return template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),parseInt);
    }

    @Override
    public void update(User user) {
        // 1.定义sql
        String sql = "update user set name = ?,gender = ?,age = ?,address = ?,qq = ?,email = ? where id = ?";
        // 2.执行sql
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail(),user.getId());
    }

    @Override
    public int findTotalCount(Map<String,String[]> condition) {
        // 1.定义模板初始化sql
        String sql = "select count(*) from user where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);

        // 2.遍历map集合
        Set<String> keySet = condition.keySet();
        // 定义list集合用于装填value
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet){
            // 排除current和rows参数
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            String value = condition.get(key)[0];
            // 判断key是否有值
            if (value != null && !"".equals(value)){
                // 有值
                sb.append(" and "+key+" like ? "); // 拼字符串时注意空格
                params.add("%"+value+"%");
            }
        }
        sql = sb.toString();

        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {
        // 1.定义模板初始化sql
        String sql = "select * from user where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);

        // 2.遍历map集合
        Set<String> keySet = condition.keySet();
        // 定义list集合用于装填value
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet){
            // 排除current和rows参数
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            String value = condition.get(key)[0];
            if (value != null && !"".equals(value)){
                // 有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");
            }
        }
        // 添加分页查询
        sb.append(" limit ?,?");
        params.add(start);
        params.add(rows);
        sql = sb.toString();

        return template.query(sql,new BeanPropertyRowMapper<User>(User.class),params.toArray());
    }
}