package com.fh.admin.section;

import com.alibaba.fastjson.JSONObject;
import com.fh.admin.biz.log.ILogService;
import com.fh.admin.commons.WebContext;
import com.fh.admin.po.Log;
import com.fh.admin.po.User;
import com.fh.admin.servlet.DistributedSession;
import com.fh.admin.util.KeyUtil;
import com.fh.admin.util.RedisUtil;
import com.fh.admin.util.SystemCount;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

//切面类
public class LogSection {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogSection.class);

    @Resource(name="logService")
    private ILogService logService;

    //横切逻辑
    public Object doLog(ProceedingJoinPoint pjp){
        //WebContext.getThread()
        HttpServletRequest request = WebContext.getThread();
        HttpServletResponse response = WebContext.getThread1();
        //获取类名
        String className = pjp.getTarget().getClass().getCanonicalName();
        //获取方法名
        String methodName = pjp.getSignature().getName();
        //具体执行的方法
        Object proceed = null;
        //获取用户信息
    String sessionId = DistributedSession.getSessionId(request, response);
        //User user = (User)request.getSession().getAttribute(SystemCount.LOGIN_USER);
        String users = RedisUtil.get(KeyUtil.buildUserKey(sessionId));
        User user = JSONObject.parseObject(users, User.class);
        String userName = user.getUserName();
        String realName = user.getRealName();
        Map<String, String[]> parMap = request.getParameterMap();//参数集合
        Iterator<Map.Entry<String, String[]>> iterator = parMap.entrySet().iterator();
        //获取注解信息
        MethodSignature methodSig = (MethodSignature) pjp.getSignature();
        Method method = methodSig.getMethod();
        String content = "";
        if(method.isAnnotationPresent(com.fh.admin.commons.Log.class)){
            com.fh.admin.commons.Log annotation = method.getAnnotation(com.fh.admin.commons.Log.class);
            content = annotation.value();
        }
        try {
            proceed = pjp.proceed();
            LOGGER.info("执行"+className+"类的 ##### "+methodName+"方法");
            addLog(userName,realName,className,methodName,iterator,"",content);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            addLog(userName,realName,className,methodName,iterator,throwable.getMessage(),content);
            throw new RuntimeException();
        }
        return proceed;
    }

    public void addLog(String userName,String realName,String className,String methodName,Iterator<Map.Entry<String, String[]>> iterator,String errorMsg,String content){
        Log log = new Log();
        log.setUserName(userName);
        log.setRealName(realName);
        String sstr = "失败";
        Integer status = SystemCount.LOG_STATUS_ERROR;
        if("".equals(errorMsg)){
            sstr = "成功";
            status = SystemCount.LOG_STATUS_SUCCESS;
        }
        log.setInfo("执行了"+className+"类的"+methodName+"方法，"+sstr);
        StringBuilder str = new StringBuilder();
        while(iterator.hasNext()){
            Map.Entry<String, String[]> next = iterator.next();
            String key = next.getKey();
            String[] value = next.getValue();
            str.append("#").append(key).append("=").append(StringUtils.join(value));
        }
        log.setDetail(str.toString());
        log.setStatus(status);
        log.setErrorMsg(errorMsg);
        log.setCreateTime(new Date());
        log.setContent(content);
        logService.addLog(log);
    }

}
