package com.fh.admin.biz.member;

import com.fh.admin.commons.ServerResponse;
import com.fh.admin.param.MemberParam;

public interface IMemberService {
    ServerResponse queryMemberByPage(MemberParam memberParam);
}
