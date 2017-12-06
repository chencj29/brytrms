package cn.net.metadata.annotation;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ams.entity.CheckAspect;
import com.thinkgem.jeesite.modules.ams.entity.common.Message;
import com.thinkgem.jeesite.modules.ams.service.CheckAspectService;
import com.thinkgem.jeesite.modules.sys.entity.Log;
import com.thinkgem.jeesite.modules.sys.utils.LogUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by wjp on 2017/3/20.
 */
@Aspect
@Component
public class CheckAsp {

    @Resource
    private CheckAspectService checkAspectService;

    /**
     * 资源系统中check注解分别设在3个类中，其中：
     * 1.ResourceSharingService.class 为资源分配（包括自动、手动分配，强制分配，取消，设置占用时间）；
     * 2.FlightPlanPairService.class 为修改保障类型（包括更新、添加保障类型）；
     * 3.ResourceMockDistInfoService.class 为模拟分配（包括模拟分配发布）；
     *
     */
    @Pointcut("execution(@Check * *(..))")
    public void controllerAspect() {
    }

    /**
     * 环绕通知 阻止未授权用户操作，并提交审核申请；
     *
     * @param joinPoint 切点
     */
    @Around("controllerAspect()")
    public Object doAround(JoinPoint joinPoint) {
        try {
            //检查是否为check注解
            if (!((MethodSignature) joinPoint.getSignature()).getMethod().isAnnotationPresent(Check.class)) {
                return ((ProceedingJoinPoint) joinPoint).proceed();
            }

            System.out.println("-----------------------checkAsp--------------------------");

            //拦截的实体类
            Object target = joinPoint.getTarget();
            //拦截的方法名称
            String methodName = joinPoint.getSignature().getName();
            //拦截的方法参数
            Object[] arguments = joinPoint.getArgs();
            //动作描述
            String operationName = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Check.class).operationName();

            //如果存在权限标识时 执行原程序并跳出
            if(SecurityUtils.getSubject().isPermitted("resource:checkAsp:"+methodName)){
                return ((ProceedingJoinPoint) joinPoint).proceed();
            }

            for (Object argument : arguments) {      //通用消息提示
                if (argument instanceof Message) {
                    if (((Message) argument).getCode() == 3) {  //标记用于运行原方法
                        ((ProceedingJoinPoint) joinPoint).proceed();
                        return null;
                    } else if (StringUtils.equals(methodName, "aircraftNumManual")) {
                        Map<String, Object> variables = new HashMap<>();
                        variables.put("success", false);
                        variables.put("message", "你的操作申请已提交,正在审核中……");
                        variables.put("errorType", -3);
                        ((Message) argument).setResult(variables);
                    } else {
                        ((Message) argument).setMsg(-3, "你的操作申请已提交,正在审核中……");
                    }
                } else if (argument instanceof String && StringUtils.equals((String) argument, "wjpMockFlag")) {//标记用于运行原方法
                    return ((ProceedingJoinPoint) joinPoint).proceed(new Object[]{arguments[0], UUID.randomUUID().toString()});
                }
            }

            //保存审核相关
            CheckAspect checkAspect = new CheckAspect();
            checkAspect.setMethod(methodName);
            checkAspect.setDescription(operationName);
            checkAspect.setLog(getLog("check"));
            checkAspectService.addCheck(checkAspect, arguments);

        } catch (Exception e) {
            e.printStackTrace();
            return new Message(-3, e.getMessage());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return new Message(-3, "你的操作申请已提交,正在审核中……");
    }

    //保存日志
    private Log getLog(String msg) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Log log = LogUtils.toLog(request, msg);
        log.setId(UUID.randomUUID().toString());
        new LogUtils.SaveLogThread(log, null, null).start();
        return log;
    }

}
