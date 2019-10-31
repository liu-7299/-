package com.fh.admin.mapper;

import com.fh.admin.param.LogParam;
import com.fh.admin.po.Log;

import java.util.List;

public interface ILogMapper {

    void addLog(Log log);

    Long queryLogCount(LogParam log);

    List<Log> queryLogByPage(LogParam log);
}
