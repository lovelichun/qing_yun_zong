package com.qingyunzong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingyunzong.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
