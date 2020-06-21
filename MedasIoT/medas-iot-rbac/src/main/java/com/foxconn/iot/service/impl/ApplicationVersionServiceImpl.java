package com.foxconn.iot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.foxconn.iot.dto.ApplicationVersionDto;
import com.foxconn.iot.entity.ApplicationEntity;
import com.foxconn.iot.entity.ApplicationVersionEntity;
import com.foxconn.iot.entity.ApplicationVersionVo;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.ApplicationRepository;
import com.foxconn.iot.repository.ApplicationVersionRepository;
import com.foxconn.iot.service.ApplicationVersionService;
import com.foxconn.iot.support.Snowflaker;

@Service
public class ApplicationVersionServiceImpl implements ApplicationVersionService {

	@Autowired
	private ApplicationVersionRepository applicationVersionRepository;
	@Autowired
	private ApplicationRepository applicationRepository;
	@Autowired
	private RestTemplate restTemplate;
	@Value("${iot.support.file-server}")
	private String fileServer;

	@Override
	public void create(ApplicationVersionDto version) {
		ApplicationVersionEntity entity = new ApplicationVersionEntity();
		BeanUtils.copyProperties(version, entity);
		entity.setId(Snowflaker.getId());
		if (version.getApplicationId() != null && version.getApplicationId() > 0) {
			ApplicationEntity application = applicationRepository.findById((long) version.getApplicationId());
			if (application == null) {
				throw new BizException("Invalid application");
			}
			entity.setApplication(application);
		}
		/** 移动应用 */
		String url = String.format("%s/move", fileServer);
		MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
		requestEntity.add("type", "/app/");
		requestEntity.add("file", version.getLink());
		JSONObject json = restTemplate.postForEntity(url, requestEntity, JSONObject.class).getBody();
		if (json == null || !json.containsKey("code") || json.getInteger("code") != 1) {
			throw new BizException("Move application failed");
		}
		entity.setLink(json.getString("filePath"));
		applicationVersionRepository.save(entity);
	}

	@Override
	public void save(ApplicationVersionDto version) {
		ApplicationVersionEntity entity = applicationVersionRepository.findById(version.getId());
		if (entity == null) {
			throw new BizException("Invalid application version");
		}
		entity.setDetails(version.getDetails());
		if (!version.getLink().equalsIgnoreCase(entity.getLink())) {
			/** 刪除之前的应用 */
			String del = String.format("%s/delete?file=%s", fileServer, entity.getLink());
			restTemplate.delete(del);

			/** 移动应用 */
			String url = String.format("%s/move", fileServer);
			MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
			requestEntity.add("type", "/app/");
			requestEntity.add("file", version.getLink());
			JSONObject json = restTemplate.postForEntity(url, requestEntity, JSONObject.class).getBody();
			if (json == null || !json.containsKey("code") || json.getInteger("code") != 1) {
				throw new BizException("Move application failed");
			}
			entity.setLink(json.getString("filePath"));
		}
		applicationVersionRepository.save(entity);
	}

	@Override
	public List<ApplicationVersionDto> queryByApplication(long appid) {
		List<ApplicationVersionVo> vos = applicationVersionRepository.queryByApplication(appid);
		List<ApplicationVersionDto> dtos = new ArrayList<>();
		for (ApplicationVersionVo vo : vos) {
			ApplicationVersionDto dto = new ApplicationVersionDto();
			BeanUtils.copyProperties(vo, dto);
			dtos.add(dto);
		}
		return dtos;
	}
}
