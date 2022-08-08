package kr.co.kmac.pms.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kmac.pms.schedule.domain.PersonInout;
import kr.co.kmac.pms.schedule.domain.ScheduleDetail;
import kr.co.kmac.pms.schedule.domain.ScheduleSearchParam;
import kr.co.kmac.pms.schedule.mapper.PersonalInoutMapper;

@Service
@Transactional
public class PersonalInoutService {

	@Autowired
	private PersonalInoutMapper personalInoutMapper;

	public int createPersonalInout(PersonInout personInout) {
		return personalInoutMapper.createPersonalInout(personInout);
	}

	public int createPersonalInout(List<PersonInout> personInoutList) {
		int a = 0;
		for (PersonInout personInout : personInoutList) {
			a = personalInoutMapper.createPersonalInout(personInout);
		}
		return a;
	}

	public List<ScheduleDetail> getPersonalInoutByMonth(ScheduleSearchParam searchParam) {
		return personalInoutMapper.getPersonalInoutByMonth(searchParam);
	}

	public PersonInout getPersonalInout(ScheduleSearchParam searchParam) {
		return personalInoutMapper.getPersonalInout(searchParam);
	}

	public int updatePersonalInout(PersonInout personInout) {
		return personalInoutMapper.updatePersonalInout(personInout);
	}

	public int removePersonalInout(ScheduleSearchParam searchParam) {
		return personalInoutMapper.removePersonalInout(searchParam);
	}

}
