package org.example.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 可以在初始化方法中获取配置参数，如果有的话
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 设置请求字符编码为 UTF-8
        request.setCharacterEncoding("UTF-8");
        // 设置响应字符编码为 UTF-8
        response.setCharacterEncoding("UTF-8");

        // 继续处理请求
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 过滤器销毁时执行的操作
    }
}
