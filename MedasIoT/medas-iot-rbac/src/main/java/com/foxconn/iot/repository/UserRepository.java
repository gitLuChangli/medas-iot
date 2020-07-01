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
	@Query(value = "update UserEntity a set a.pwd=:pwd, a.modify=1 where a.id=:id")
	void updatePwdById(@Param("pwd") String pwd, @Param("id") long id);
	
	@Modifying
	@Query(value = "update UserEntity a set a.pwd=:pwd, a.modify=0 where a.id=:id")
	void resetPwd(@Param("pwd") String pwd, @Param("id") long id);
	
	@Query(value = "select b.ancestor from UserEntity a inner join CompanyRelationEntity b on a.company.id = b.descendant where a.id=:userid order by b.depth desc")
	List<Long> queryCompanyRelations(@Param("userid") long userid);
	
	@Query(value = "select a.name from tb_role a left join tb_user_role b on a.id = b.role_id where b.user_id=:userid and a.status=0", nativeQuery = true)
	List<Object> queryRoles(@Param("userid") long userid);
	
	@Query(value = "select a.name from tb_permission a left join tb_role_permission b on a.id = b.permission_id left join tb_user_role c on b.role_id = c.role_id left join tb_role d on c.role_id=d.id where c.user_id=userid and a.status=0 and d.status=0", nativeQuery = true)
	List<Object> queryPermissions(@Param("userid") long userid);
}
