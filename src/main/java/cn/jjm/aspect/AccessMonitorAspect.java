package cn.jjm.aspect;

import cn.jjm.annotation.EnableMonitorConfiguration;
import cn.jjm.service.AccessMonitorService;
import com.alibaba.fastjson.JSON;
import cn.jjm.annotation.AccessMonitor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 *
 * @author jiangjingming
 * @date 2017/10/26
 */
@ConditionalOnBean(annotation = EnableMonitorConfiguration.class)
@AutoConfigureAfter({AccessMonitorService.class})
@Component
@Aspect
@Slf4j
public class AccessMonitorAspect {

    @Autowired
    private AccessMonitorService accessMonitorService;


    /**
     * 注解切面
     */
    @Pointcut("@annotation(cn.jjm.annotation.AccessMonitor)")
    public void AccessMonitorAspect() {
    }

    @Before("AccessMonitorAspect()")
    public void beforeAccess(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method me = methodSignature.getMethod();
        Object[] args = joinPoint.getArgs();
        AccessMonitor accessMonitor = me.getAnnotation(AccessMonitor.class);
        //注解方法名称
        String methodName = accessMonitor.methodName();

        // 获取用户请求方法的参数并序列化为JSON格式字符串
        String methodParams = JSON.toJSONString(args);
        //方法签名
        String methodSige = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();

        cn.jjm.dao.AccessMonitor accessMonitorDao = new cn.jjm.dao.AccessMonitor();
        accessMonitorDao.setMethodSignature(methodSige);
        accessMonitorDao.setMethodName(methodName);
        accessMonitorDao.setMethodParams(methodParams);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String accessDate = df.format(new Date());
        accessMonitorDao.setAccessDate(accessDate);

        accessMonitorService.saveAccessMonitor(accessMonitorDao);


    }


}
