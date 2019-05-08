package com.wwg.mapper;

import com.wwg.entity.UserInfo;
import com.wwg.markerMapper.CommonMapper;

import java.util.List;


public interface UserInfoMapper extends CommonMapper<UserInfo>{
    List<UserInfo> searchAll();
}
