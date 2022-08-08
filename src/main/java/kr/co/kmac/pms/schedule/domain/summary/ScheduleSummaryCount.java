package kr.co.kmac.pms.schedule.domain.summary;

import lombok.Data;

@Data
public class ScheduleSummaryCount {

	private int t1;// [내부일정]
	private int t2;// [외부일정]
	private int t3;// [고객정보]
	private int t4;// [프로젝트]
	private int t5;// [교육참석]
	private int t6;// [휴가]
	private int t7;// [Up-day]
	private int t8;// [개인휴무]

	private int totalCount;

	public void addCount(ScheduleSummary scheduleSummary) {
		this.t1 = this.t1 + scheduleSummary.getT1();
		this.t2 = this.t2 + scheduleSummary.getT2();
		this.t3 = this.t3 + scheduleSummary.getT3();
		this.t4 = this.t4 + scheduleSummary.getT4();
		this.t5 = this.t5 + scheduleSummary.getT5();
		this.t6 = this.t6 + scheduleSummary.getT6();
		this.t7 = this.t7 + scheduleSummary.getT7();
		this.t8 = this.t8 + scheduleSummary.getT8();
		this.totalCount = this.totalCount + scheduleSummary.getTotalCount();
	}

	public int getTotalCountCal() {
		return this.t1 + this.t2 + this.t3 + this.t4 + this.t5 + this.t6 + this.t7 + this.t8;
	}
}
