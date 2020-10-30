package com.bishe.service;

import com.bishe.entity.Admin;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface AdminService {
    public List<Admin> queryAll();
    public String queryAdmin(String username, String password, String code, HttpSession session);
}
