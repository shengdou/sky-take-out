package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author Moyu
 * @version 1.0
 * @description: TODO
 * @date 2024/4/23 17:00
 */
@Aspect
@Component
public class AutoFillAspect {
    /**
     * @description: 定义切入点
     * @param:
     * @return:
     * @author Moyu
     * @date: 2024/4/23 17:03
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)" )
    public void autoFillPointcut(){};

    @Before("autoFillPointcut()")
    public void autoFill(JoinPoint joinPoint) {
        //获取操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperationType operationType = signature.getMethod().getAnnotation(AutoFill.class).value();
        //获取实体类
        Object[] args = joinPoint.getArgs();
        if (args==null || args.length==0){
            return;
        }
        Object entity = args[0];

        // 获取用户与时间
        LocalDateTime now = LocalDateTime.now();
        Long id = BaseContext.getCurrentId();

        //获取实体类方法
        try {
            Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
            Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
            Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

            if (operationType == OperationType.INSERT) {
                setCreateTime.invoke(entity,now);
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,id);
                setCreateUser.invoke(entity,id);
            }
            else if (operationType == OperationType.UPDATE) {
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
