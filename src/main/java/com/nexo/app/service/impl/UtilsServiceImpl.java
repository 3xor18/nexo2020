package com.nexo.app.service.impl;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexo.app.service.UtilsService;

@Service
@Transactional
public class UtilsServiceImpl implements UtilsService {

	@Override
	public ZonedDateTime giveToday() {
		ZonedDateTime today = ZonedDateTime.now(ZoneId.systemDefault());
		return today;
	}

}
