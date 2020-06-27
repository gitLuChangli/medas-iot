package com.foxconn.iot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.UserEntity;
import com.foxconn.iot.entity.UserVo;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findById(long id);
	
	UserEntity findByNo(String no);
	
	@Query(value = "select new com.foxconn.iot.entity.UserVo(a.id, a.no, a.name, a.email, a.openId, a.icivetId, a.phone, a.ext, a.avatarUrl, a.status, a.createOn, a.company.id, a.company.name) from UserEntity a")
	Page<UserVo> query(Pageable pageable);
	
	@Query(value = "select new com.foxconn.iot.entity.UserVo(a.id, a.no, a.name, a.email, a.openId, a.icivetId, a.phone, a.ext, a.avatarUrl, a.status, a.createOn, a.company.id, a.company.name) "
			+ "from UserEntity a where a.company.id in (select b.descendant from CompanyRelationEntity b where b.ancestor=:companyid)")
	Page<UserVo> queryByCompanyId(@Param("companyid") long companyid, Pageable pageble);

	@Modifying
	@Query(value = "update UserEntity a set a.status=:status where a.id=:id")
	void updateStatusById(@Param("status") int status, @Param("id") long id);
	
	@Modifying
	@Query(value = "update UserEntity a set a.pwd=:pwd where a.id=:id")
	void updatePwdById(@Param("pwd") String pwd, @Param("id") long id);
	
	@Query(value = "select b.ancestor from UserEntity a inner join CompanyRelationEntity b on a.company.id = b.descendant where a.id=:userid order by b.depth desc")
	List<Long> queryCompanyRelations(@Param("userid") long userid);
}
