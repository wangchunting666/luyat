package com.luyat.demo.controller;

import com.luyat.common.result.ResultObj;
import com.luyat.common.result.ResultStatus;
import com.luyat.common.util.Md5Utils;
import com.luyat.common.util.ParamUtils;
import com.luyat.common.util.RSAUtils;
import com.luyat.common.util.RegexUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by Herdsric-M-003 on 2018/1/26.
 */
@Controller
@RequestMapping("/login")
public class DemoController {
    private static Logger logger = LogManager.getLogger(DemoController.class.getName());

    @RequestMapping(value = "/rdsPwd",method = RequestMethod.POST)
    public void rdsPwd(HttpServletResponse response) {
        try {
            PrintWriter writer = response.getWriter();
            String publicKey = RSAUtils.generateBase64PublicKey();
            writer.write(publicKey);
        } catch (Exception e) {
            logger.error("获取公钥数据失败",e);
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){return "login";}

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){return "/demo/index";}

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(HttpServletRequest request){
        Map<String,Object> paramMap = ParamUtils.generateOperMap(request);
        //参数验证
        Object usernameObj = paramMap.get("username");
        Object passwordObj = paramMap.get("password");
        if(usernameObj == null || passwordObj == null) {
            return new ResultObj(ResultStatus.failure, "用户名或密码为空!", 1, null).getMap();
        }
        String usernameStr = RSAUtils.decryptBase64(usernameObj.toString());
        String passwordStr = RSAUtils.decryptBase64(passwordObj.toString());
        paramMap.put("username",usernameStr);
        paramMap.put("password",passwordStr);
        boolean vusername = RegexUtils.matchs(paramMap, "username", "^[a-zA-Z]\\w{5,17}$");
        boolean vpassword = RegexUtils.matchs(paramMap, "password", "^[a-zA-Z]\\w{5,17}$");
        if(!vusername || !vpassword) {
            return new ResultObj(ResultStatus.failure, "用户名或密码为空!", 2, null).getMap();
        }
        //用户登录
        UsernamePasswordToken token = new UsernamePasswordToken(usernameStr, Md5Utils.string2MD5(passwordStr));
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
        return new ResultObj(ResultStatus.success, "登录成功!", 0, null).getMap();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        Session session = SecurityUtils.getSubject().getSession();
        String sessionId = session.getId().toString();
        // shiro退出登录
        SecurityUtils.getSubject().logout();
        // 跳回原地址
        String redirectUrl = request.getHeader("Referer");
        if (null == redirectUrl) {
            redirectUrl = "/";
        }
        return "redirect:" + redirectUrl;
    }
}
