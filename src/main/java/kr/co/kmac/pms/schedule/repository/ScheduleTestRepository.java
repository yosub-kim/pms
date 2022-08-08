package kr.co.kmac.pms.schedule.repository;

import kr.co.kmac.pms.schedule.domain.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class ScheduleTestRepository extends JdbcDaoSupport {

	private HolidayByMonth holidayByMonth;

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		holidayByMonth = new HolidayByMonth(getDataSource());
	}

	protected class HolidayByMonth extends MappingSqlQuery {
		protected HolidayByMonth(DataSource ds, String query) {
			super(ds, query);
		}

		protected HolidayByMonth(DataSource ds) {
			super(ds, "" + " SELECT [hyear], [hmonth], [hday], [hName] " + " FROM [newKmacPms].[dbo].[holliday] "
					+ " WHERE hyear=? AND hmonth=? " + " ORDER BY hday");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Holiday h = new Holiday();

			h.setHyear(rs.getInt("hyear"));
			h.setHmonth(rs.getInt("hmonth"));
			h.setHday(rs.getInt("hday"));
			h.setHName(rs.getString("hName"));

			return h;
		}
	}

	protected void initDao() throws Exception {
		this.holidayByMonth = new HolidayByMonth(getDataSource());
	}

	public List<Holiday> getHolidayListByMonth(String year, String month) throws DataAccessException {
		List<Holiday> resultList = holidayByMonth.execute(new Object[] { year, month });
		return resultList;
	}

	public int numOfCustomer(String projectCode) throws DataAccessException {
		try {
			return getJdbcTemplate().queryForObject("		SELECT COUNT(seq) cnt " + "		FROM projectCsrInfo "
					+ "		WHERE projectcode =" + projectCode + "", Integer.class);
		} catch (Exception e) {
			return 0;
		}
	}

}
