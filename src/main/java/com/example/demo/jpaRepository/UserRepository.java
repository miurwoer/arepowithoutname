package com.example.demo.jpaRepository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Asuka on 2018/3/5.
 */

public interface UserRepository extends JpaRepository<User,Integer>{

    //通过用户名和密码查询
    List<User> findByUsernameAndPassword(String username, String password);
}
