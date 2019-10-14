package com.baizhi.service;

import com.baizhi.entity.User;
import com.baizhi.entity.UserMap;
import com.baizhi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class UserSercviceImpl implements UserSercvice {
    @Autowired
    UserMapper userMapper;
    @Override
    public List<UserMap> queryAll() {
        List<UserMap> userMaps = userMapper.queryAll();
        return userMaps;
    }

    @Override
    public List<Integer> query() {
        List<Integer> query = userMapper.query();
        ArrayList<Integer> list = new ArrayList<>();
        for (Integer integer : query) {
            list.add(integer);
        }
        return list;
    }


}
