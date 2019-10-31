package com.fh.admin.biz.log;

import com.fh.admin.mapper.ILogMapper;
import com.fh.admin.param.LogParam;
import com.fh.admin.po.Log;
import com.fh.admin.util.DateUtil;
import com.fh.admin.vo.log.LogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("logService")
public class ILogServiceImpl implements ILogService {

    @Autowired
    private ILogMapper logMapper;

    @Override
    public void addLog(Log log) {
        logMapper.addLog(log);
    }

    @Override
    public Long queryLogCount(LogParam log) {
        return logMapper.queryLogCount(log);
    }

    @Override
    public List<LogVo> queryLogByPage(LogParam log) {
        List<Log> list = logMapper.queryLogByPage(log);
        List<LogVo> lit = new ArrayList<>();
        for(Log lg : list){
            LogVo logVo = toVo(lg);
            lit.add(logVo);
        }
        return lit;
    }

    public LogVo toVo(Log log){
        LogVo logVo = new LogVo();
        logVo.setId(log.getId());
        logVo.setUserName(log.getUserName());
        logVo.setRealName(log.getRealName());
        logVo.setInfo(log.getInfo());
        logVo.setStatus(log.getStatus());
        logVo.setErrorMsg(log.getErrorMsg());
        logVo.setCreateTime(DateUtil.dateToString(log.getCreateTime(),DateUtil.STRING_H_M_S));
        logVo.setDetail(log.getDetail());
        logVo.setContent(log.getContent());
        return logVo;
    }

}
