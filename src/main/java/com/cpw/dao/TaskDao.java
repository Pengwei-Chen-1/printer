package com.cpw.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.cpw.vo.TaskVO;

public class TaskDao extends JdbcDaoSupport {

	@SuppressWarnings("unchecked")
	public List<TaskVO> queryAll() {
		String sql = "SELECT T.TASKID,T.USERID,T.TOTALNUM,T.LASTNUM,T.PRINTERID,T.STATUS FROM TASK T";
		return super.getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int num) throws SQLException {
				TaskVO task = new TaskVO();
				task.setTaskId(rs.getInt("TASKID"));
				task.setUserId(rs.getInt("USERID"));
				task.setPrinterId(rs.getInt("PRINTERID"));
				task.setTotalNum(rs.getInt("TOTALNUM"));
				task.setLastNum(rs.getInt("LASTNUM"));
				task.setStatus(rs.getString("STATUS"));
				return task;
			}
		});
	}

	/**
	 * 获取每个用户对应剩余的需要打印的张数
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TaskVO> queryLastNum() {
		String sql = "SELECT PRINTERID,SUM(LASTNUM) AS LASTNUMCOUNT FROM TASK T WHERE T.STATUS IN ('W','P') GROUP BY PRINTERID";
		return getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int num) throws SQLException {
				TaskVO task = new TaskVO();
				task.setLastNumCount(rs.getInt("LASTNUMCOUNT"));
				task.setPrinterId(rs.getInt("PRINTERID"));
				return task;
			}
		});
	}

	public int saveOrUpdate(TaskVO task) {
		StringBuffer sb = new StringBuffer();
		if (task.getTaskId() > 0) {
			// sb.append("UPDATE PRINTER SET ");
			// sb.append(" PRINTERNAME = '" + pri.getPrinterName() + "',");
			// sb.append(" PRINTERSPEED = " + pri.getSpeed());
			// sb.append(" WHERE PRINTERID = " + pri.getPrinterId());
			return getJdbcTemplate().update(sb.toString());
		} else {
			sb.append("INSERT INTO TASK(TASKID,USERID,PRINTERID,TOTALNUM,LASTNUM,STATUS) VALUES(NULL,?,?,?,?,?)");
			Object[] obj = new Object[] { task.getUserId(), task.getPrinterId(), task.getTotalNum(), task.getLastNum(),
					task.getStatus() };
			return getJdbcTemplate().update(sb.toString(), obj);
		}
	}

	/**
	 * 查询列表
	 * 
	 * @param task
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TaskVO> queryTaskList(TaskVO task) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT T.TASKID,T.USERID,T.TOTALNUM,T.LASTNUM,T.PRINTERID,T.STATUS FROM TASK T");
		sb.append(" WHERE 1=1");
		if (task.getTaskId() > 0)
			sb.append(" AND T.TASKID = " + task.getTaskId());
		if (task.getPrinterId() > 0)
			sb.append(" AND T.PRINTERID = " + task.getPrinterId());
		if (task.getUserId() > 0)
			sb.append(" AND T.USERID = " + task.getUserId());
		if (task.getTotalNum() > 0)
			sb.append(" AND T.TOTALNUM = " + task.getTotalNum());
		if (task.getLastNum() > 0)
			sb.append(" AND T.LASTNUM = " + task.getLastNum());
		if (StringUtils.isNotBlank(task.getStatus()))
			sb.append(" AND T.STATUS = '" + task.getStatus() + "'");
		return super.getJdbcTemplate().query(sb.toString(), new RowMapper() {

			public Object mapRow(ResultSet rs, int num) throws SQLException {
				TaskVO task = new TaskVO();
				task.setTaskId(rs.getInt("TASKID"));
				task.setUserId(rs.getInt("USERID"));
				task.setPrinterId(rs.getInt("PRINTERID"));
				task.setTotalNum(rs.getInt("TOTALNUM"));
				task.setLastNum(rs.getInt("LASTNUM"));
				task.setStatus(rs.getString("STATUS"));
				return task;
			}
		});
	}

	/**
	 * 修改task信息
	 * 
	 * @param task
	 * @return
	 */
	public int updateTask(TaskVO task) {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE TASK T SET ");
		if (task.getPrinterId() > 0)
			sb.append(" T.PRINTERID = " + task.getPrinterId() + ",");
		if (task.getUserId() > 0)
			sb.append(" T.USERID = " + task.getUserId() + ",");
		if (task.getTotalNum() > 0)
			sb.append(" T.TOTALNUM = " + task.getTotalNum() + ",");
		if (task.getLastNum() >= 0)
			sb.append(" T.LASTNUM = " + task.getLastNum() + ",");
		if (StringUtils.isNotBlank(task.getStatus()))
			sb.append(" T.STATUS = '" + task.getStatus() + "',");
		sb = sb.delete(sb.length() - 1, sb.length());
		sb.append(" where T.TASKID =" + task.getTaskId());
		return getJdbcTemplate().update(sb.toString());
	}

	@SuppressWarnings("unchecked")
	public List<TaskVO> queryPrinterTaskList() {
		String sql = "SELECT P.PRINTERNAME,T.TASKID,T.TOTALNUM,T.LASTNUM,T.PRINTERID,T.USERID FROM TASK T , PRINTER P"
				+ " WHERE T.STATUS = 'P' AND T.PRINTERID = P.PRINTERID";
		return getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int num) throws SQLException {
				TaskVO task = new TaskVO();
				task.setPrinterName(rs.getString("PRINTERNAME"));
				task.setPrinterId(rs.getInt("PRINTERID"));
				task.setTaskId(rs.getInt("TASKID"));
				task.setUserId(rs.getInt("USERID"));
				task.setTotalNum(rs.getInt("TOTALNUM"));
				task.setLastNum(rs.getInt("LASTNUM"));
				return task;
			}
		});
	}
}
