package test;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.rms.entity.CarouselOccupyingInfo;
import com.thinkgem.jeesite.modules.rms.service.CarouselOccupyingInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Created by wjp on 2017/5/10.
 */
public class SpringGetbeanText extends BaseJunit4Test {
    //FlightDynamicService flightDynamicService = ContextLoader.getCurrentWebApplicationContext().getBean(FlightDynamicService.class);
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void getBeanDefinitionNames() {
        //获取spring装配的bean个数
//        int count = applicationContext.getBeanDefinitionNames().length;
        //逐个打印出spring自动装配的bean。根据我的测试，类名第一个字母小写即bean的名字
//        for (int i = 0; i < count; i++) {
//            System.out.println(applicationContext.getBeanDefinitionNames()[i]);
//        }

        List list = ((CarouselOccupyingInfoService)SpringContextHolder.getBean("carouselOccupyingInfoService")).findList(new CarouselOccupyingInfo());
        System.out.println(list);
    }
}
