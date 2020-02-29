package org.worldbuild.cloud.auth.JPA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.worldbuild.cloud.auth.JPA.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {

    UserInfo save(UserInfo userInfo);

    UserInfo findByUsername(String username);
}
