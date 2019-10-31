package com.fh.admin.mapper;

import com.fh.admin.param.UserParam;
import com.fh.admin.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IUserMapper { 

    void addUser(User user);

    List<User> queryUser();

    Long queryUserCount(UserParam user);

    List<User> queryUserByPage(UserParam user);

    User queryUserById(Long id);

    void updateUser(User user);

    void deleteUserById(Long id);

    void deleteAll(String[] ids);

    List<String> queryRoleNameByUid(Long id);

    void addRoleOrUser(Map map);

    void deleteRoleOrUserById(Long id);

    List<Integer> queryRoleById(Long id);

    User queryUserByName(String userName);

    void updateUserByTime(User userInfo);

    void updateUserByErrorTime(User userInfo);

    User selectUserByName(User user);

    void updateUserStatus(@Param("id") Long id, @Param("errorCount") Integer errorCount);

    void updateUserPassword(Long id, String password);

    List<User> queryUserByPam(UserParam userParam);
}
