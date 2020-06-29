package com.foxconn.iot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.ResourceRelationEntity;

public interface ResourceRelationRepository extends JpaRepository<ResourceRelationEntity, Integer> {

	@Query(value = "select a.ancestor from ResourceRelationEntity a where a.descendant=:descendant order by a.depth desc")
	List<Long> queryAncestorsByDescendant(@Param("descendant") long descendant);

	@Query(value = "select a.descendant from ResourceRelationEntity a where a.ancestor=:ancestor order by a.depth desc")
	List<Long> queryDescendantByAncestor(@Param("ancestor") long ancestor);

	@Modifying
	@Query(value = "delete from ResourceRelationEntity a where a.ancestor=:ancestor")
	void deleteByAncestor(@Param("ancestor") long ancestor);

	@Modifying
	@Query(value = "delete from ResourceRelationEntity a where a.descendant=:descendant")
	void deleteByDescendant(@Param("descendant") long descendant);
	
	@Query(value = "select a from ResourceRelationEntity a order by a.descendant, a.depth desc")
	List<ResourceRelationEntity> findAll();
	
	@Query(value = "select a from ResourceRelationEntity a left join ResourceEntity b on a.descendant=b.id where b.type=:type order by a.descendant, a.depth desc")
	List<ResourceRelationEntity> findByType(@Param("type") int type);
}
