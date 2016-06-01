package com.cpw.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.cpw.vo.UserInfoVO;

public class UserInfoDao extends JdbcDaoSupport {

	@SuppressWarnings("unchecked")
	public List<UserInfoVO> queryAll() {
		String sql = "SELECT U.USERID,U.USERNAME FROM USERINFO U";
		return super.getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int num) throws SQLException {
				UserInfoVO user = new UserInfoVO();
				user.setUserId(rs.getInt("USERID"));
				user.setUserName(rs.getString("USERNAME"));
				return user;
			}
		});
	}
}
