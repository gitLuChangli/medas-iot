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
import com.mysql.cj.util.StringUtils;

import io.netty.util.internal.StringUtil;

@Service
public class WxServiceTemplateServiceImpl implements WxServiceTemplateService {

	@Autowired
	private WxServiceTemplateRepository wxServiceTemplateRepository;

	@Autowired
	private WxServiceAccountRepository wxServiceAccountRepository;

	@Override
	public WxServiceTemplateDto create(WxServiceTemplateDto template) {
		WxServiceAccount account = wxServiceAccountRepository.findByAppId(template.getAccountId());
		if (account == null) {
			throw new BizException(ErrorCode.ENTITY_NOT_FOUND);
		}

		WxServiceTemplate wxTemplate = new WxServiceTemplate();
		BeanUtils.copyProperties(template, wxTemplate);
		wxTemplate.setAccount(account);
		wxServiceTemplateRepository.save(wxTemplate);
		template.setId(wxTemplate.getId());
		return template;
	}

	@Override
	public WxServiceTemplateDto save(WxServiceTemplateDto template) {
		WxServiceTemplate wxTemplate = wxServiceTemplateRepository.findByTemplateId(template.getTemplateId());
		if (wxTemplate == null) {
			throw new BizException(ErrorCode.ENTITY_NOT_FOUND);
		}

		if (!StringUtil.isNullOrEmpty(template.getName())) {
			wxTemplate.setName(template.getName());
		}
		if (!StringUtils.isNullOrEmpty(template.getFormat())) {
			wxTemplate.setFormat(template.getFormat());
		}

		if (!StringUtils.isNullOrEmpty(template.getAccountId())) {
			WxServiceAccount account = wxServiceAccountRepository.findByAppId(template.getAccountId());
			if (account == null) {
				throw new BizException(ErrorCode.ENTITY_NOT_FOUND);
			}

			wxTemplate.setAccount(account);
		}
		wxServiceTemplateRepository.save(wxTemplate);
		template.setId(wxTemplate.getId());
		return template;
	}

	@Override
	public WxServiceTemplateDto findById(long id) {
		WxServiceTemplate template = wxServiceTemplateRepository.findById(id);
		WxServiceTemplateDto dto = new WxServiceTemplateDto();
		BeanUtils.copyProperties(template, dto);
		return dto;
	}

	@Override
	public List<WxServiceTemplateDto> findByWxServiceAccountId(long wxServiceAccountId) {
		List<WxServiceTemplate> templates = wxServiceTemplateRepository.queryByWxServiceAccountId(wxServiceAccountId);
		List<WxServiceTemplateDto> dtos = new ArrayList<>();
		for (WxServiceTemplate template : templates) {
			WxServiceTemplateDto dto = new WxServiceTemplateDto();
			BeanUtils.copyProperties(template, dto);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	@Transactional
	public int udpateStatusById(int status, long id) {
		return wxServiceTemplateRepository.updateStatusById(status, id);
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		wxServiceTemplateRepository.deleteById(id);
	}
}
