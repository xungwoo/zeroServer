package com.thirtygames.zero.common.etc.cache.redis;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.thirtygames.zero.common.model.security.AdminUser;


@Repository
@Slf4j
public class UserRepository {
	
    @Cacheable(value = "adminUser", key = "#id")
    public AdminUser getUser(String id) {
    	AdminUser user = new AdminUser();
    	user.setName("xmanTest");
    	
    	log.debug("This user Cache?????????????????????????????????");
    	
 
//        user.setUsername(id);
//        user.setPassword(id);
//        user.setEmail("sunghyouk.bae@gmail.com");
// 
//        user.getHomeAddress().setPhone("999-9999");
//        user.getOfficeAddress().setPhone("555-5555");
// 
//        for (int i = 0; i < favoriteMovieSize; i++)
//            user.getFavoriteMovies().add("Favorite Movie Number-" + i);
// 
//        if (UserRepository.log.isDebugEnabled())
//            UserRepository.log.debug("Create User...");
 
        return user;
    }
}