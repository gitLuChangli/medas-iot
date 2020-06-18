package com.foxconn.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foxconn.iot.entity.ApplicationVersionEntity;

public interface ApplicationVersionRepository extends JpaRepository<ApplicationVersionEntity, Long> {

}
