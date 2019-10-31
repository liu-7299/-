package com.fh.admin.mapper;

import com.fh.admin.param.MemberParam;
import com.fh.admin.po.Member;

import java.util.List;

public interface IMemberMapper {
    Long queryMemberCount(MemberParam memberParam);

    List<Member> queryMemberByPage(MemberParam memberParam);
}
