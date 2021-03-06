package com.bishe.contorler;

import com.bishe.entity.UserMap;
import com.bishe.service.UserSercvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserSercvice userService;
    @RequestMapping("/queryAll")
    @ResponseBody
    public List<UserMap> queryAll(){
        List<UserMap> userMaps = userService.queryAll();
        return userMaps;
    }
    @RequestMapping("/query")
    @ResponseBody
    public List<Integer> query(){
        List<Integer> query = userService.query();
        return query;
    }
}
