package com.fh.shop.api.member.biz;

import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.member.po.Member;

public interface IMemberService {

    ServerResponse addMember(Member member);

    ServerResponse queryMemberByUserName(String userName);

    ServerResponse queryMemberByEmail(String email);

    ServerResponse queryMemberByPhone(String phone);

    ServerResponse login(Member member);
}
