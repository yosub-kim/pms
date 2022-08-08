package kr.co.kmac.pms.common.security.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import kr.co.kmac.pms.common.domain.PmsUserDetail;

@Repository
public class SecurityRepository extends JdbcDaoSupport {

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	public PmsUserDetail getUserByUserId(String id) throws DataAccessException {
		String query = " SELECT * from expertpool where userId = '" + id + "' ";

		PmsUserDetail res = (PmsUserDetail) getJdbcTemplate().queryForObject(query, new RowMapper<PmsUserDetail>() {
			@Override
			public PmsUserDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
				PmsUserDetail u = new PmsUserDetail();
				u.setEnabled(rs.getBoolean("enable"));
				u.setName(rs.getString("name"));
				u.setPassword(rs.getString("password"));
				u.setEmail(rs.getString("email"));
				u.setPosition(rs.getString("companyPosition"));
				u.setSsn(rs.getString("ssn"));
				u.setLoginId(rs.getString("userId"));
				u.setAccountNonLocked(rs.getBoolean("accountNonLocked"));
				u.setCredentialsNonExpired(rs.getBoolean("accountNonLocked"));
				u.setJobClass(rs.getString("jobClass"));
				u.setDept(rs.getString("dept"));
				u.setUserId(rs.getString("userId"));
				u.setCompanyPosition(rs.getString("companyPosition"));

				return u;
			}

		});
		return res;
	}

	public String getEncPassword(String password) throws DataAccessException {
		//String query = "select SecureDB.DBSEC.ENCRYPT_PWD('" + password + "')";
		//return (String) getJdbcTemplate().queryForObject(query, String.class);
		return password;
	}

}
