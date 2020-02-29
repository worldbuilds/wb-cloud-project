package org.worldbuild.cloud.auth.JPA.service;

import org.worldbuild.cloud.auth.JPA.entity.UserInfo;

public interface UserInfoService {

    UserInfo save(UserInfo userInfo);

    UserInfo findById(Integer userId);
}



