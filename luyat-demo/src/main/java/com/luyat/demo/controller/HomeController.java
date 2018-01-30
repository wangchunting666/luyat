package com.luyat.demo.controller;

import com.luyat.demo.service.DemoApiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Herdsric-M-003 on 2018/1/29.
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    private static Logger logger = LogManager.getLogger(HomeController.class.getName());

    @Autowired
    private DemoApiService demoApiService;

    @ResponseBody
    @RequestMapping("/permissionMenus")
    public Object permissionMenus(){
        return demoApiService.getMenusByName(null);
    }
}
