package com.foxconn.iot.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foxconn.iot.core.dto.WxServiceAccountDto;
import com.foxconn.iot.core.entity.WxServiceAccount;
import com.foxconn.iot.core.exception.BizException;
import com.foxconn.iot.core.exception.ErrorCode;
import com.foxconn.iot.core.repository.WxServiceAccountRepository;
import com.foxconn.iot.core.service.WxServiceAccountService;

@Service
public class WxServiceAccountServiceImpl implements WxServiceAccountService {

	@Autowired
	private WxServiceAccountRepository wxServiceAccountRepository;

	@Override
	public WxServiceAccountDto create(WxServiceAccountDto account) {
		WxServiceAccount wxServiceAccount = new WxServiceAccount();
		BeanUtils.copyProperties(account, wxServiceAccount);
		wxServiceAccountRepository.save(wxServiceAccount);
		account.setId(wxServiceAccount.getId());
		return account;
	}

	@Override
	public WxServiceAccountDto findByAppId(String appId) {
		WxServiceAccount account = wxServiceAccountRepository.findByAppId(appId);
		WxServiceAccountDto dto = new WxServiceAccountDto();
		BeanUtils.copyProperties(account, dto);
		return dto;
	}

	@Override
	public WxServiceAccountDto findById(long id) {
		WxServiceAccount account = wxServiceAccountRepository.findById(id);
		WxServiceAccountDto dto = new WxServiceAccountDto();
		BeanUtils.copyProperties(account, dto);
		return dto;
	}
	
	@Override
	public Page<WxServiceAccountDto> findAll(Pageable pageable) {
		Page<WxServiceAccount> accounts = wxServiceAccountRepository.findAll(pageable);
		List<WxServiceAccountDto> dtos = new ArrayList<>();
		BeanUtils.copyProperties(accounts, dtos);
		return new PageImpl<>(dtos, pageable, accounts.getTotalElements());
	}

	@Override
	@Transactional
	public int updateStautsById(int status, long id) {
		return wxServiceAccountRepository.updateStatusById(status, id);
	}

	@Override
	public WxServiceAccountDto save(WxServiceAccountDto account) {
		WxServiceAccount wxAccount = wxServiceAccountRepository.findByAppId(account.getAppId());
		if (wxAccount == null) {
			throw new BizException(ErrorCode.NOT_FOUND, "无效的微信公众平台服务号ID");			
		}
		wxAccount.setSecret(account.getSecret());
		wxAccount.setName(account.getName());
		wxAccount = wxServiceAccountRepository.save(wxAccount);
		WxServiceAccountDto dto = new WxServiceAccountDto();
		BeanUtils.copyProperties(wxAccount, dto);
		return dto;
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		wxServiceAccountRepository.deleteById(id);;
	}

}
