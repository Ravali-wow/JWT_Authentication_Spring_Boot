package com.bootai.boot.repository;

//import java.io.Serializable;
import com.bootai.boot.entity.UserInfo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
    
    Optional<UserInfo> findByEmail(String email);
}
