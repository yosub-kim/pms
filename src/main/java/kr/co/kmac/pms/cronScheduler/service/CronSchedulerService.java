package kr.co.kmac.pms.cronScheduler.service;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kmac.pms.cronScheduler.mapper.CronSchedulerMapper;

@Service
@Transactional(readOnly = true)
public class CronSchedulerService {
	
	@Autowired
	private CronSchedulerMapper cronSchedulerMapper;
	
	/**
	 * TODO 신규인력 예산시스템 권한 자동등록
	 * 매일 오전 5시 실행
	 * @author 손호일
	 * @version 
	 */
	@Scheduled(cron = "0 0 5 * * *")
	public void DWPM_INSERT() {
	
		System.out.println("cron ==============");
		//this.cronSchedulerMapper.DWPM_INSERT();
	}
}
