package com.aoshine.demo.service;

import com.aoshine.demo.dao.RegisterDao;
import com.aoshine.demo.entity.response.db.User;
import com.aoshine.demo.service.RegisterService;
import com.aoshine.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class RegisterService {

    @Autowired
    private RegisterDao registerDao;

    public boolean register(User user) throws IOException {
        user.setPassword(Util.encryptPassword(user.getUserid(), user.getPassword()));
        return registerDao.register(user);
    }
}

