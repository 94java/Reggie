package top.hellocode.filter;

import com.alibaba.fastjson.JSON;
import org.springframework.util.AntPathMatcher;
import top.hellocode.common.BaseContext;
import top.hellocode.common.R;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author HelloCode
 * @site https://www.hellocode.top
 * @date 2022年11月07日 15:10
 */
@WebFilter("/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取本次请求uri
        String requestURI = request.getRequestURI();

        // 定义直接放行的URI
        String[] urls = {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/login"
        };

        // 判断本次请求是否需要处理
        Boolean check = check(requestURI,urls);
        // 不需要处理，直接放行
        if(check){
            filterChain.doFilter(request,response);
            return;
        }

        // 需要处理，如果已经登录，直接放行
        Long id = (Long) request.getSession().getAttribute("employee");
        Long user = (Long) request.getSession().getAttribute("user");
        if(!Objects.isNull(id)){
            BaseContext.setCurrentId(id);
            filterChain.doFilter(request,response);
            return;
        }
        if(!Objects.isNull(user)){
            BaseContext.setCurrentId(user);
            filterChain.doFilter(request,response);
            return;
        }
        // 需要处理，未登录，返回未登录
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }
    /**
     * @Description: 路径校验
     * @param: requestURI
     * @return: java.lang.Boolean
     */
    private Boolean check(String requestURI,String[] urls) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
