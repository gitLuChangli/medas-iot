package com.foxconn.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foxconn.iot.entity.UpdateTaskEntity;

public interface UpdateTaskRepository extends JpaRepository<UpdateTaskEntity, Long> {

}
