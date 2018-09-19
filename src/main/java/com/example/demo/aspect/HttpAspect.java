package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Asuka on 2018/1/14.
 */
@Aspect
@Component
public class HttpAspect {

    private static final Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * com.example.demo.controller..*.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void doBefore(JoinPoint jp){
        /*logger.info("已经进入前置通知");
        //request对象
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //url
        logger.info("url={}",request.getRequestURL());
        //method
        logger.info("method{}",request.getMethod());
        //ip
        logger.info("ip={}",request.getRemoteAddr());
        //类名+类方法
        logger.info("classMethod={}",jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName());
        //参数
        logger.info("args={}",jp.getArgs());*/

    }

    @After("pointCut()")
    public void doAfter(){
        logger.info("已经进入后置通知");
    }

     /*@Around("pointCut()")
    public Object doAround(ProceedingJoinPoint jp){
       logger.info("进入环绕通知");
        Object object = null;
        long startTime = System.currentTimeMillis();
        try {
            object = jp.proceed(jp.getArgs());
        } catch (Throwable throwable) {
            logger.error("fsdl",throwable);
            JSONObject jo = new JSONObject();
            jo.put("code", "-9");
            jo.put("info", throwable.getMessage());
            object = jo;
        }
        long endTime = System.currentTimeMillis();
        if (object != null){
            logger.info("方法执行结果"+object.toString());
        }
        logger.info("方法执行时间"+(endTime-startTime)+"ms");
        return object;
    }*/
}
