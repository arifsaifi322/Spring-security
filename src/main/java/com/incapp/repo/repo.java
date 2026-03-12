package com.incapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incapp.entity.my_user;

@Repository
public interface repo extends JpaRepository<my_user, String>{

}
