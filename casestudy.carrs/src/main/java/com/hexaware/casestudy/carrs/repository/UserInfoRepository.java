package com.hexaware.casestudy.carrs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.casestudy.carrs.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{

	public UserInfo findByUserName(String userName);
	
	@Query("SELECT u FROM UserInfo u WHERE u.userName = :username")
	Optional<UserInfo> findByUsername(@Param("username") String username);


}
