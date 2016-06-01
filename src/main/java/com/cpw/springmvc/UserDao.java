package com.cpw.springmvc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

@SuppressWarnings("all")
public class UserDao extends JdbcDaoSupport {
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	// 此方法把USEMBO表对应的字段查询出来依次放入userPO中
	public Collection<UserPO> doquery() {
		String sql = "SELECT T.USERID,T.USERNAME,T.USERAGE FROM USERMBO T";
		return super.getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int num) throws SQLException {
				UserPO user = new UserPO();
				user.setUserId(rs.getInt("USERID"));
				user.setUserName(rs.getString("USERNAME"));
				user.setUserAge(rs.getInt("USERAGE"));
				return user;
			}
		});
	}
}
