package com.foxconn.iot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findById(long id);
	
	Page<UserEntity> findByStatus(int status, Pageable pageable);

	UserEntity findByNo(String no);

	@Query(value = "select a from UserEntity a inner join CompanyRelationEntity b on a.company.id=b.descendant where b.ancestor=:companyid and a.status=:status")
	Page<UserEntity> queryByCompanyIdAndStatus(@Param("companyid") long companyid, @Param("status") int status, Pageable pageble);

	@Modifying
	@Query(value = "update UserEntity a set a.status=:status where a.id=:id")
	void updateStatusById(@Param("status") int status, @Param("id") long id);
	
	@Modifying
	@Query(value = "update UserEntity a set a.pwd=:pwd where a.id=:id")
	void updatePwdById(@Param("pwd") String pwd, @Param("id") long id);
	
	@Query(value = "select b.ancestor from UserEntity a inner join CompanyRelationEntity b on a.company.id = b.descendant where a.id=:userid order by b.depth desc")
	List<Long> queryCompanyRelations(@Param("userid") long userid);
}
