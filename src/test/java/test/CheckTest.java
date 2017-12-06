package test;

import cn.net.metadata.annotation.Check;
import com.thinkgem.jeesite.modules.rms.service.FlightPlanPairService;
import com.thinkgem.jeesite.modules.rms.service.ResourceMockDistInfoService;
import com.thinkgem.jeesite.modules.rms.service.ResourceSharingService;
import com.thinkgem.jeesite.modules.rms.web.ResourceSharingController;
import org.junit.Test;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by wjp on 2017/4/6.
 */
public class CheckTest {
    @Test
    public void testSpringContext(){
        Set<Method> methods = ReflectionUtils.getAllMethods(ResourceSharingService.class, method -> method.isAnnotationPresent(Check.class));
        Set<Method> methods2 = ReflectionUtils.getAllMethods(FlightPlanPairService.class, method -> method.isAnnotationPresent(Check.class));
        Set<Method> methods3 = ReflectionUtils.getAllMethods(ResourceMockDistInfoService.class, method -> method.isAnnotationPresent(Check.class));
        methods2.addAll(methods);
        methods3.addAll(methods2);
        methods3.forEach(method -> {
            Check check = method.getAnnotation(Check.class);
            System.out.println(method.getName()+":\""+check.operationName()+"\",");
        });
    }
}
