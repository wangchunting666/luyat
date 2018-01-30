package com.luyat.demo.controller;

import com.luyat.common.util.ParamUtils;
import com.luyat.demo.service.DemoUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Herdsric-M-003 on 2018/1/29.
 */
@Controller
@RequestMapping("/demo/user")
public class DemoUserController {
    private static Logger logger = LogManager.getLogger(DemoUserController.class.getName());
    @Autowired
    private DemoUserService demoUserService;

    @RequiresPermissions("upms:user:read")
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){return "/demo/user/index";}

    @RequiresPermissions("upms:user:read")
    @ResponseBody
    @RequestMapping(value = "/data",method = RequestMethod.POST)
    public Object getData(HttpServletRequest request){
        Map<String,Object> paramMap = ParamUtils.generateOperMap(request);
        return demoUserService.getDataService(paramMap);
    }
}
