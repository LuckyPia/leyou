package com.leyou.user.mapper;

import com.leyou.user.pojo.Role;
import com.leyou.user.pojo.User;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {

    @Select("select * from role r,user_role ur where r.RoleId = ur.Rid and ur.Uid = #{id}")
    List<Role> getUserRoleByUid(Long id);
}
