package com.fh.admin.biz.user;

import com.fh.admin.commons.ResponseEnum;
import com.fh.admin.commons.ServerResponse;
import com.fh.admin.mapper.IUserMapper;
import com.fh.admin.param.UpdatePass;
import com.fh.admin.param.UserParam;
import com.fh.admin.po.User;
import com.fh.admin.util.*;
import com.fh.admin.vo.user.UserVo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("userService")
public class IUserServiceImpl implements IUserService {

    @Autowired
    private IUserMapper userMapper;

    @Override
    public User queryUserByName(String userName) {
        return userMapper.queryUserByName(userName);
    }

    @Override
    public void updateUserByTime(User userInfo) {
        userMapper.updateUserByTime(userInfo);
    }

    @Override
    public void updateUserByErrorTime(User userInfo) {
        userMapper.updateUserByErrorTime(userInfo);
    }

    @Override
    public User selectUserByName(User user) {
        return userMapper.selectUserByName(user);
    }

    @Override
    public ServerResponse updateUserStatus(Long id) {
        userMapper.updateUserStatus(id,0);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse updateUserPassword(UpdatePass up,HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute(SystemCount.LOGIN_USER);
        if(up.getOldPassword() == null || up.getPassword() == null || up.getNewPassword() == null){
            return ServerResponse.error(ResponseEnum.UPDATE_PASS_ISNULL);
        }
        if(!up.getPassword().equals(up.getNewPassword())){
            return ServerResponse.error(ResponseEnum.UPDATE_PASS_ISNO);
        }
        if(!user.getPassword().equals(Md5Util.passToMd5Pass(up.getOldPassword(),user.getSalt()))){
            return ServerResponse.error(ResponseEnum.UPDATE_PASS_ISNOOLD);
        }
        userMapper.updateUserPassword(user.getId(),Md5Util.passToMd5Pass(up.getPassword(),user.getSalt()));
        return ServerResponse.success();
    }

    @Override
    public ServerResponse updateResetUserPass(Long id) {
        User user1 = userMapper.queryUserById(id);
        User user = userMapper.queryUserByName(user1.getUserName());
        String password = Md5Util.passToMd5Pass(SystemCount.DEFSULT_USER_PASSWORD,user.getSalt());
        userMapper.updateUserPassword(id,password);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse updateForgetPassword(User user) {
        User user1 = userMapper.queryUserByName(user.getUserName());
        if(StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getEmail())){
            return ServerResponse.error(ResponseEnum.UPDATE_PASS_ISNULL);
        }
        if(user1 == null){
            return ServerResponse.error(ResponseEnum.LOGION_ERROR_USER_NULL);
        }
        if(!user1.getEmail().equals(user.getEmail())){
            return ServerResponse.error(ResponseEnum.RESET_PASS_ISNO);
        }
        //String[] arr = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        /*for(int i=0;i<6;i++){
            String s = String.valueOf(Math.floor(Math.random() * arr.length));
            Integer random = Integer.valueOf(s.substring(0,s.indexOf(".")));
            newPassword += arr[random];
        }*/
        String newPassword = RandomStringUtils.randomAlphanumeric(6);
        userMapper.updateUserPassword(user1.getId(),Md5Util.passToMd5Pass(newPassword,user1.getSalt()));
        //Sendmail.toQQSendmail(newPassword,user1);
        String updateUserPasswordByEmailContent = PathUtil.getUpdateUserPasswordByEmailContent(user1.getRealName(), newPassword);
        MailUtil.sendmail(user1.getEmail(),user1.getUserName(),"更新密码成功！",updateUserPasswordByEmailContent);
        return ServerResponse.success();
    }

    @Override
    public void addUser(User user,String[] roleId) {
        user.setSalt(String.valueOf(UUID.randomUUID()));
        user.setPassword(Md5Util.passToMd5Pass(user.getPassword(),user.getSalt()));
        userMapper.addUser(user);
        getAddRoleOrUser(user.getId(),roleId);
    }

    public void getAddRoleOrUser(Long id,String[] roleId){
        if(roleId != null && roleId.length > 0){
            for(int i=0;i<roleId.length;i++){
                Map map = new HashMap();
                map.put("uuid",id);
                map.put("rrid",roleId[i]);
                userMapper.addRoleOrUser(map);
            }
        }
    }
    @Override
    public List<UserVo> queryUser() {
        List<User> list = userMapper.queryUser();
        List<UserVo> listVo = new ArrayList<UserVo>();
        for (User user : list){
            List<String> listStr = userMapper.queryRoleNameByUid(user.getId());
            UserVo use = getUserToUserVo(user);
            if(null != listStr && listStr.size() > 0){
                use.setRoleName(StringUtils.join(listStr,","));
            }
            listVo.add(use);
        }
        return listVo;
    }

    @Override
    public Long queryUserCount(UserParam user) {
        return userMapper.queryUserCount(user);
    }

    public List<User> queryUserByPam(UserParam userParam) {
        List<User> list = userMapper.queryUserByPam(userParam);
        return list;
    }

    @Override
    public List<UserVo> queryUserByPage(UserParam user) {
        List<User> list = userMapper.queryUserByPage(user);
        List<UserVo> llist = new ArrayList<UserVo>();
        for (User uuser : list){
            List<String> listt = userMapper.queryRoleNameByUid(uuser.getId());
            UserVo use = getUserToUserVo(uuser);
            if(null != listt && listt.size() > 0){
                String str = StringUtils.join(listt,",");
                use.setRoleName(str);
            }
            llist.add(use);
        }
        return llist;
    }

    @Override
    public UserVo queryUserById(Long id) {
        User user = userMapper.queryUserById(id);
        UserVo userVo = getUserToUserVo(user);
        userVo.setList(userMapper.queryRoleById(id));
        return userVo;
    }

    @Override
    public void updateUser(User user,String[] roleId) {
        userMapper.updateUser(user);
        userMapper.deleteRoleOrUserById(user.getId());
        getAddRoleOrUser(user.getId(),roleId);
    }

    @Override
    public void deleteUserById(Long id) {
        userMapper.deleteUserById(id);
    }

    @Override
    public void deleteAll(String[] str) {
        userMapper.deleteAll(str);
    }

    public UserVo getUserToUserVo(User user){
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setRealName(user.getRealName());
        userVo.setUserName(user.getUserName());
        userVo.setAge(user.getAge());
        userVo.setSex(user.getSex());
        userVo.setPhone(user.getPhone());
        userVo.setEmail(user.getEmail());
        userVo.setPay(user.getPay());
        userVo.setEntryTime(DateUtil.dateToString(user.getEntryTime(),DateUtil.STRING_Y_M_D));
        userVo.setImgPath(user.getImgPath());
        boolean equals = DateUtil.dateToString(new Date(), DateUtil.STRING_Y_M_D).equals(DateUtil.dateToString(new Date(), DateUtil.STRING_Y_M_D));
        userVo.setStatus(equals?SystemCount.LOGIN_ERROR_COUNT==user.getErrorCount():false);
        userVo.setRegion1(user.getRegion1());
        userVo.setRegion2(user.getRegion2());
        userVo.setRegion3(user.getRegion3());
        userVo.setRegionName(user.getRegionName());
        return userVo;
    }

}
