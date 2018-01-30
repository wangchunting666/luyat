package com.luyat.demo.configuration;

import com.luyat.demo.service.DemoApiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by Herdsric-M-003 on 2018/1/22.
 */
public class ShiroRealm extends AuthorizingRealm {
    private static Logger logger = LogManager.getLogger(ShiroRealm.class.getName());
    @Autowired
    private DemoApiService demoApiService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("权限认证方法：ShiroRealm.doGetAuthenticationInfo()");
        String username= (String)SecurityUtils.getSubject().getPrincipal();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("username",username);

        // 当前用户所有角色
        List<Map<String,Object>> upmsRoles = demoApiService.getRoleByName(paramMap);
        Set<String> roles = new HashSet<>();
        for (Map<String,Object> upmsRole : upmsRoles) {
            Object name = upmsRole.get("name");
            if(name != null && !"".equals(name.toString())) {
                roles.add(upmsRole.get("name").toString());
            }
        }

        // 当前用户所有权限
        List<Map<String,Object>> upmsPermissions = demoApiService.getPermissionByName(paramMap);
        Set<String> permissions = new HashSet<>();
        for (Map<String,Object> upmsPermission : upmsPermissions) {
            Object permission_value = upmsPermission.get("permission_value");
            if(permission_value != null && !"".equals(permission_value.toString())) {
                permissions.add(upmsPermission.get("permission_value").toString());
            }
        }

        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.setStringPermissions(permissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("登陆认证方法：ShiroRealm.doGetAuthenticationInfo()");
        String username = (String)authenticationToken.getPrincipal();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("username",username);
        Map<String,Object> userMap = demoApiService.getUserByName(paramMap);
        if(null == userMap || userMap.size() == 0){
            throw new UnknownAccountException();
        }
        if (!"100".equals(userMap.get("locked").toString())) {
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userMap.get("username"),
                userMap.get("password"),
                ByteSource.Util.bytes(username),
                getName());
        return authenticationInfo;
    }
}
