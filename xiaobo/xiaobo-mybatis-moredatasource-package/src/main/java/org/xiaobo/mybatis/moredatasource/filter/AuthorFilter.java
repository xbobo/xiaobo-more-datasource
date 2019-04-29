//package org.xiaobo.mybatis.moredatasource.filter;
//
//import static java.lang.Long.parseLong;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import com.puxinwangxiao.erp.core.config.AudienceConfig;
//import com.puxinwangxiao.erp.core.exception.TokenException;
//import com.puxinwangxiao.erp.core.util.JwtUtil;
//
//import io.jsonwebtoken.Claims;
//
///**
// * 功能描述
// *
// * @author chenjian@pxjy.com
// * @date 2019/1/10 15:56
// */
//@Component
//@WebFilter(urlPatterns = "/", filterName = "authorFilter")
//@Order(Integer.MAX_VALUE - 1)
//public class AuthorFilter implements Filter {
//    @Autowired
//    private AudienceConfig audience;
//    private static final Logger logger = LoggerFactory.getLogger(AuthorFilter.class);
//
//    private static final String[] URL_FILTER_LIST = {"/actuator", "/api/bjy","/api/user/login","/api/other/upload",
//            "/api/youzan","/instances", "/error", "swagger", "/druid/", "/v2/api-docs", "/download_file","/check_user","api/V1.0/normalLogin","/api/v1.0/public_course/detail",
//            "/api/v1.0/public_course/course_wx"};
//
//    private static final String OPTIONS = "OPTIONS";
//
//    private static final String AUTH_HEADER_START = "bearer;";
//
//    private static final String TOKEN_USER_KEY = "userid";
//
//    @Value("${author.checkToken}")
//    private boolean checkToken;
//
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request=(HttpServletRequest) servletRequest;
//        HttpServletResponse response=(HttpServletResponse)servletResponse;
//        String url=request.getRequestURI();
//        String method=request.getMethod();
//
//        logger.debug("url = {},method = {}",url,method);
//        try {
//            checkToken(request);
//            filterChain.doFilter(servletRequest, servletResponse);
//            logger.debug("url = {} Over",url);
//        }
//
//        catch (Exception e){
//            logger.error("",e);
//            dueException(response,e.getMessage(),Result.ERROR);
//        }
//    }
//
//
//    private void checkToken(HttpServletRequest req) {
//        String url = req.getRequestURI();
//        String method = req.getMethod();
//
//        //通过配置,跳过检测token
//        if (!checkToken) {
//            return;
//        }
//
//        //跳过OPTIONS
//        if (OPTIONS.equals(method)) {
//            return;
//        }
//    logger.info(url);
//        //跳过不需要检测的API
//        for (String s : URL_FILTER_LIST) {
//            if (url.contains(s)) {
//                return;
//            }
//        }
//
//
//        final String authHeader = req.getHeader("authorization");
//        if (authHeader == null || !authHeader.startsWith(AUTH_HEADER_START)) {
//            throw new TokenException("无效token");
//        }
//        final String token = authHeader.substring(7);
//
//        final Claims claims = JwtUtil.parseJWT(token, audience.getBase64Secret());
//        if (claims == null) {
//            throw new TokenException("无效token");
//        }
//        if (claims.get(TOKEN_USER_KEY) == null) {
//            throw new TokenException("用户已失效，重新登陆");
//        }
//        String id = claims.get(TOKEN_USER_KEY).toString();
//        long user_id = parseLong(id);
//
//        req.setAttribute("user_id", user_id);
//    }
//
//
//
//    private void dueException(HttpServletResponse response, String error, int status) {
//        response.setStatus(200);
//        response.setHeader("Content-type", "application/json;charset=UTF-8");
//        try {
//            response.getWriter().write((new Result(status,"服务器错误:"+error,null)).toString());
//        } catch (Exception e) {
//            logger.error("", e);
//        }
//    }
//
//}
