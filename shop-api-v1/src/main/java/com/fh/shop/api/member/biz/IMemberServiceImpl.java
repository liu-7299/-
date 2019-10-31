package com.fh.shop.api.member.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.commons.ResponseEnum;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.member.mapper.IMemberMapper;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.UUID;
import java.util.regex.Pattern;

@Service("memberService")
@Transactional(rollbackFor = Exception.class)
public class IMemberServiceImpl implements IMemberService {

    @Autowired
    private IMemberMapper memberMapper;

    @Override
    @Transactional(readOnly = true)
    public ServerResponse login(Member member) {
        String phone = member.getPhone();
        String phoneCode = member.getPhoneCode();
        String userName = member.getUserName();
        String password = member.getPassword();
        Member memberInfo = null;
        if(StringUtils.isEmpty(phone)){
            //用户、密码不能为空
            if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
                return ServerResponse.error(ResponseEnum.USER_OR_PASS_IS_NULL);
            }
            QueryWrapper<Member> wrapper = new QueryWrapper<>();
            wrapper.eq("userName",userName);
            memberInfo = memberMapper.selectOne(wrapper);
            //用户不存在
            if(memberInfo == null){
                return ServerResponse.error(ResponseEnum.USER_OR_PASS_IS_ERROR);
            }
            //密码错误
            if(!Md5Util.md5(Md5Util.md5(password) + memberInfo.getSalt()).equals(memberInfo.getPassword())){
                return ServerResponse.error(ResponseEnum.USER_OR_PASS_IS_ERROR);
            }
        }
        if(StringUtils.isNotEmpty(phone)){
            if(StringUtils.isEmpty(phoneCode)) return ServerResponse.error();
            QueryWrapper<Member> wrapper = new QueryWrapper<>();
            wrapper.eq("phone",phone);
            memberInfo = memberMapper.selectOne(wrapper);
            //用户不存在
            if(memberInfo == null){
                return ServerResponse.error(ResponseEnum.USER_OR_PASS_IS_ERROR);
            }
            //验证码错误
            String str = RedisUtil.get(KeyUtil.getPhoneKey(phone));
            if(!phoneCode.equalsIgnoreCase(str)){
                return ServerResponse.error();
            }
        }
        String uuid = UUID.randomUUID().toString();
        //放入redis
        RedisUtil.setex(KeyUtil.getUserKey(userName,uuid),"1",SystemCount.EXIET_TIME);
        //Vo对象
        MemberVo memberVo = new MemberVo();
        memberVo.setId(memberInfo.getId());
        memberVo.setRealName(memberInfo.getRealName());
        memberVo.setUserName(userName);
        memberVo.setUuid(uuid);
        //json串
        String memberJson = JSONObject.toJSONString(memberVo);
        //转码 encode 解码 decode
        String encode = Base64.getEncoder().encodeToString((memberJson.getBytes()));
        //内容↑encode.secret↓签名
        String secret = Md5Util.sign(encode, SystemCount.LOGIN_SECRET);
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
        return ServerResponse.success(encode + "." + secret);
    }

    @Override
    public ServerResponse addMember(Member member) {
        String userName = member.getUserName();
        String password = member.getPassword();
        String passwordP = member.getPasswordP();
        String email = member.getEmail();
        String phone = member.getPhone();
        String phoneCode = member.getPhoneCode();
        if(
                StringUtils.isEmpty(userName) ||
                StringUtils.isEmpty(email) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(passwordP) ||
                StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(phoneCode)
                ){//信息不能为空
            return ServerResponse.error(ResponseEnum.PARAM_IS_NULL);
        }
        //两次密码不一致
        if(!password.equals(passwordP)) return ServerResponse.error(ResponseEnum.PASS_IS_NO_FIT);
        //获取验证码
        String newCode = RedisUtil.get(KeyUtil.getPhoneKey(member.getPhone()));
        //验证码过时或未发送验证码
        if(StringUtils.isEmpty(newCode)) return ServerResponse.error(ResponseEnum.CODE_IS_NULL);
        //账号唯一性
        QueryWrapper wrapperUserName = new QueryWrapper();
        wrapperUserName.eq("userName",userName);
        Member memberInfo = memberMapper.selectOne(wrapperUserName);
        if(null != memberInfo) return ServerResponse.error(ResponseEnum.USER_IS_EXIET);
        //邮箱唯一性
        QueryWrapper wrapperEmail = new QueryWrapper();
        wrapperEmail.eq("email",email);
        memberInfo = memberMapper.selectOne(wrapperEmail);
        if(null != memberInfo) return ServerResponse.error(ResponseEnum.EMAIL_IS_EXIET);
        //手机唯一性
        QueryWrapper wrapperPhone = new QueryWrapper();
        wrapperPhone.eq("userName",userName);
        memberInfo = memberMapper.selectOne(wrapperPhone);
        if(null != memberInfo) return ServerResponse.error(ResponseEnum.PHONE_IS_EXIET);
        //都验证成功 生成盐
        String salt = UUID.randomUUID().toString();
        member.setSalt(salt);
        member.setPassword(Md5Util.md5(Md5Util.md5(password) + salt));
        memberMapper.insert(member);
        return ServerResponse.success(ResponseEnum.USER_SUCCESS);
    }

    @Override
    @Transactional(readOnly = true)
    public ServerResponse queryMemberByUserName(String userName) {
        if(StringUtils.isEmpty(userName)) return ServerResponse.error(ResponseEnum.PARAM_IS_NULL);
        if(userName.contains(" ")) return ServerResponse.error(ResponseEnum.IS_CONTAINS_ERROR);
        //定义正则
        String pattern = "[a-zA-Z][a-zA-Z0-9]{5,9}";//或者"^1(3|5|7|8)\\d{9}"
        //不符合用户名规则
        if(!Pattern.matches(pattern,userName)) return ServerResponse.error(ResponseEnum.USER_IS_ERROR);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("userName",userName);
        Member member = memberMapper.selectOne(wrapper);
        if(null != member) return ServerResponse.error(ResponseEnum.USER_IS_EXIET);
        return ServerResponse.success();
    }

    @Override
    @Transactional(readOnly = true)
    public ServerResponse queryMemberByEmail(String email) {
        if(StringUtils.isEmpty(email)) return ServerResponse.error(ResponseEnum.PARAM_IS_NULL);
        if(email.contains(" ")) return ServerResponse.error(ResponseEnum.IS_CONTAINS_ERROR);
        //定义正则
        String pattern = "\\S+[@]\\S{2,6}[.][c][o][m]";//或者"^1(3|5|7|8)\\d{9}"
        //不符合邮箱规则
        if(!Pattern.matches(pattern,email)) return ServerResponse.error(ResponseEnum.EMAIL_IS_ERROR);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("email",email);
        Member member = memberMapper.selectOne(wrapper);
        if(null != member) return ServerResponse.error(ResponseEnum.EMAIL_IS_EXIET);
        return ServerResponse.success();
    }

    @Override
    @Transactional(readOnly = true)
    public ServerResponse queryMemberByPhone(String phone) {
        if(StringUtils.isEmpty(phone)) return ServerResponse.error(ResponseEnum.PARAM_IS_NULL);
        if(phone.contains(" ")) return ServerResponse.error(ResponseEnum.IS_CONTAINS_ERROR);
        //定义正则
        String pattern = "^1[3578]\\d{9}";//或者"^1(3|5|7|8)\\d{9}"
        //不符合手机号规则
        if(!Pattern.matches(pattern,phone)) return ServerResponse.error(ResponseEnum.PHONE_IS_ERROR);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("phone",phone);
        Member member = memberMapper.selectOne(wrapper);
        if(null != member) return ServerResponse.error(ResponseEnum.PHONE_IS_EXIET);
        return ServerResponse.success();
    }

}
