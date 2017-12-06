package cn.net.metadata.rule.repo;

import com.bstek.urule.console.DefaultUser;
import com.bstek.urule.console.EnvironmentProvider;
import com.bstek.urule.console.User;
import com.bstek.urule.console.servlet.RequestContext;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaowu on 3/7/16.
 */
@Component("environmentProvider")
public class EnvironmentProviderImpl implements EnvironmentProvider {
    @Override
    public User getLoginUser(RequestContext requestContext) {
        //return UserUtils.getUser();

        DefaultUser user = new DefaultUser();
        user.setCompanyId("bstek");
        user.setUsername("admin");
        user.setAdmin(true);
        return user;
    }

    @Override
    public List<User> getUsers() {
//        List<User> users = Lists.newArrayList();
//        users.addAll(UserUtils.getAllUsers());
//        return users;

        DefaultUser user = new DefaultUser();
        user.setCompanyId("bstek");
        user.setUsername("user");
        List<User> users=new ArrayList<User>();
        users.add(user);
        return users;
    }
}
