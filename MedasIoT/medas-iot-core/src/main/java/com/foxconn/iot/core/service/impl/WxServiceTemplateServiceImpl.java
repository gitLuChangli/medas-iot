package com.foxconn.iot.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.iot.core.dto.WxServiceTemplateDto;
import com.foxconn.iot.core.entity.WxServiceAccount;
import com.foxconn.iot.core.entity.WxServiceTemplate;
import com.foxconn.iot.core.exception.BizException;
import com.foxconn.iot.core.exception.ErrorCode;
import com.foxconn.iot.core.repository.WxServiceAccountRepository;
import com.foxconn.iot.core.repository.WxServiceTemplateRepository;
import com.foxconn.iot.core.service.WxServiceTemplateService;

@Service
public class WxServiceTemplateServiceImpl implements WxServiceTemplateService {

	@Autowired
	private WxServiceTemplateRepository wxServiceTemplateRepository;
	
	@Autowired
	private WxServiceAccountRepository wxServiceAccountRepository;
	
	@Override
	public WxServiceTemplateDto save(WxServiceTemplateDto template) {
		WxServiceAccount account = wxServiceAccountRepository.findByAppId(template.getAccountId());
		if (account == null) {
			throw new BizException(ErrorCode.NOT_FOUND, "无效的微信公众平台服务号ID");
		}
		
		WxServiceTemplate wxTemplate = new WxServiceTemplate();
		BeanUtils.copyProperties(template, wxTemplate);
		wxServiceTemplateRepository.save(wxTemplate);
		template.setId(wxTemplate.getId());
		return template;
	}

	@Override
	public WxServiceTemplateDto findByTemplateId(String templateId) {
		WxServiceTemplate template = wxServiceTemplateRepository.findByTemplateId(templateId);
		WxServiceTemplateDto dto = new WxServiceTemplateDto();
		BeanUtils.copyProperties(template, dto);
		return dto;
	}

	@Override
	public List<WxServiceTemplateDto> findByServiceId(String serviceId) {
		List<WxServiceTemplate> templates = wxServiceTemplateRepository.queryByServiceAppId(serviceId);
		List<WxServiceTemplateDto> dtos = new ArrayList<>();
 		BeanUtils.copyProperties(templates, dtos);
		return dtos;
	}

	@Override
	@Transactional
	public int udpateStatusByTemplateId(int status, String templateId) {
		return wxServiceTemplateRepository.updateStatusByTemplateId(status, templateId);
	}

}
