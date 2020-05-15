package com.foxconn.iot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.CompanyEntity;
import com.foxconn.iot.entity.CompanyRelationVo;
import com.foxconn.iot.entity.RoleEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

	CompanyEntity findById(long id);

	CompanyEntity findByCode(String code);

	@Modifying
	@Query(value = "update CompanyEntity a set a.status=:status where a.id=:id")
	void updateStatusById(@Param("status") int status, @Param("id") long id);

	@Modifying
	@Query(value = "update CompanyEntity a set a.roles=:roles where a.id=:id")
	void updateRolesById(@Param("roles") List<RoleEntity> roles, @Param("id") long id);
	
	@Query(value = "select new com.foxconn.iot.entity.CompanyRelationVo(a.id, a.code, a.name, a.details, "
			+ "a.region, a.province, a.city, a.county, a.address, a.area, a.status, a.createOn, b.ancestor, b.depth, b.root)"
			+ "from CompanyEntity a left join CompanyRelationEntity b on a.id=b.descendant order by b.depth, a.id asc")
	List<CompanyRelationVo> queryDescendants();
	
	@Query(value = "select new com.foxconn.iot.entity.CompanyRelationVo(a.id, a.code, a.name, a.details, "
			+ "a.region, a.province, a.city, a.county, a.address, a.area, a.status, a.createOn, b.ancestor, b.depth, b.root)"
			+ "from CompanyEntity a left join CompanyRelationEntity b on a.id=b.descendant where a.id in (select b.descendant from b where b.ancestor=:ancestor) order by b.depth, a.id asc")
	List<CompanyRelationVo> queryDescendantsByAncestor(@Param("ancestor") long ancestor);
}
