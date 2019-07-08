package com.leyou.user.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.utils.CodecUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;


    public Boolean checkData(String data, Integer type) {
        //判断数据类型
        User record = new User();
        switch (type) {
            case 1:
                record.setUsername(data);
                break;
            case 2:
                record.setPhone(data);
                break;
            default:
                throw new LyException(ExceptionEnum.INVALID_USER_DATA_TYPE);
        }
        return userMapper.selectCount(record) == 0;
    }

    public void sendCode(String phone) {

    }

    public void register(User user) {
        // 1.校验验证码

        // 2.密码加密
        String salt = CodecUtils.generateSalt();
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        user.setSalt(salt);
        user.setCreationTime(new Timestamp(System.currentTimeMillis()));
        // 3.写入数据库
        userMapper.insert(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = new User();
        u.setUsername(username);
        User user = userMapper.selectOne(u);
        if (user == null) {
            throw new UsernameNotFoundException("账户不存在");
        }
        user.setRoles(userMapper.getUserRoleByUid(user.getUserId()));
        return user;
    }
}
