package com.daeng.nyang.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daeng.nyang.dto.Apply;

@Repository
public interface ApplyRepo extends JpaRepository<Apply, String>{

}
