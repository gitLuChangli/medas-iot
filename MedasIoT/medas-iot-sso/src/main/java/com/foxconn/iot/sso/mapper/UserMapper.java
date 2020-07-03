package com.foxconn.iot.sso.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import com.foxconn.iot.sso.entity.User;

@Mapper
public interface UserMapper {

	@Select("select a.name from tb_role a left join tb_user_role b on a.id = b.role_id where b.user_id=#{userid}")
	List<String> queryRolesByUserId(@Param("userid") long userid);
	
	@Select("select * from tb_user where user_no=#{no}")
	@Results({
		@Result(property="id", column = "id", javaType = Long.class),
		@Result(property = "no", column = "user_no", javaType = String.class),
		@Result(property = "password", column = "pwd", javaType = String.class),
		@Result(property = "roles", column = "id", many = @Many(select = "queryRolesByUserId", fetchType = FetchType.EAGER)),
		@Result(property = "companyId", column = "company_id", javaType = Long.class),
		@Result(property = "status", column = "status", javaType = Integer.class),
		@Result(property = "modify", column = "modify", javaType = Integer.class)
	})
	User findByNO(@Param("no") String no);
}
