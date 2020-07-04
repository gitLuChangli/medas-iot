package com.foxconn.iot.sso.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ResourceMapper {

	@Select("select a.name from tb_role a left join tb_role_permission b on a.id = b.role_id left join tb_permission_res c on b.permission_id = c.permission_id left join tb_res d on c.res_id = d.id where d.url=#{url} and d.method=#{method}")
	List<String> queryRoles(@Param("url") String url, @Param("method") String method);
}
