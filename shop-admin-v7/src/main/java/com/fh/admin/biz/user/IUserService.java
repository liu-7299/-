package com.fh.admin.biz.user;

import com.fh.admin.commons.ServerResponse;
import com.fh.admin.param.UpdatePass;
import com.fh.admin.param.UserParam;
import com.fh.admin.po.User;
import com.fh.admin.vo.user.UserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IUserService {

    void addUser(User user, String[] roleId);

    List<UserVo> queryUser();

    Long queryUserCount(UserParam user);

    List<UserVo> queryUserByPage(UserParam user);

    UserVo queryUserById(Long id);

    void updateUser(User user, String[] roleId);

    void deleteUserById(Long id);

    void deleteAll(String[] user);

    User queryUserByName(String userName);

    void updateUserByTime(User userInfo);

    void updateUserByErrorTime(User userInfo);

    User selectUserByName(User user);

    ServerResponse updateUserStatus(Long id);

    ServerResponse updateUserPassword(UpdatePass up, HttpServletRequest request);

    ServerResponse updateResetUserPass(Long id);

    ServerResponse updateForgetPassword(User user);

    List<User> queryUserByPam(UserParam userParam);
}
