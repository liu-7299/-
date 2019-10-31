package com.fh.admin.biz.log;

import com.fh.admin.param.LogParam;
import com.fh.admin.po.Log;
import com.fh.admin.vo.log.LogVo;

import java.util.List;

public interface ILogService {

    void addLog(Log log);

    Long queryLogCount(LogParam log);

    List<LogVo> queryLogByPage(LogParam log);
}
