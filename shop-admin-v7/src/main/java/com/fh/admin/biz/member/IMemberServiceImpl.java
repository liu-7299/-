package com.fh.admin.biz.member;

import com.fh.admin.commons.DataTableMap;
import com.fh.admin.commons.ServerResponse;
import com.fh.admin.mapper.IMemberMapper;
import com.fh.admin.param.MemberParam;
import com.fh.admin.po.Member;
import com.fh.admin.util.DateUtil;
import com.fh.admin.vo.member.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("memberService")
public class IMemberServiceImpl implements IMemberService {

    @Autowired
    private IMemberMapper memberMapper;

    @Override
    public ServerResponse queryMemberByPage(MemberParam memberParam) {
        Long count = memberMapper.queryMemberCount(memberParam);
        List<Member> list = memberMapper.queryMemberByPage(memberParam);
        return ServerResponse.success(new DataTableMap(memberParam.getDraw(),count,count,toVo(list)));
    }

    private List<MemberVo> toVo(List<Member> mems){
        List<MemberVo> members = new ArrayList<>();
        for (Member mem : mems) {
            MemberVo member = new MemberVo();
            member.setId(mem.getId());
            member.setUserName(mem.getUserName());
            member.setRealName(mem.getRealName());
            member.setEmail(mem.getEmail());
            member.setPhone(mem.getPhone());
            member.setBirthday(DateUtil.dateToString(mem.getBirthday(),DateUtil.STRING_Y_M_D));
            member.setRegionName(mem.getRegionName());
            members.add(member);
        }
        return members;
    }

}
