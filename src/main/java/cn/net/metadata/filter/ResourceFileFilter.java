package cn.net.metadata.filter;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourceFileFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(ResourceFileFilter.class);
    private static String resorceVer = "_v=" + Global.getJsVer();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //请求地址
        String requestURL = req.getRequestURL().toString();
        //请求参数
        String queryStr = req.getQueryString();

        //处理静态资源缓存问题
        if (requestURL != null && (requestURL.endsWith(".js") || requestURL.endsWith(".css"))) {    // static resource
            String newURL = null;
            if (StringUtils.isNotBlank(queryStr) && queryStr.trim().indexOf(resorceVer) == -1) {
                resp.sendRedirect(requestURL + "?" + resorceVer);
                return;
            }
            if (StringUtils.isBlank(queryStr)) {
                resp.sendRedirect(requestURL + "?" + resorceVer);
                return;
            }
        }

        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("js、css文件添加版本错误：" + e.toString());
        }
        return;
    }

    @Override
    public void destroy() {

    }
}
