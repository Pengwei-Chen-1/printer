package com.cpw.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.cpw.vo.PrinterVO;

public class PrinterDao extends JdbcDaoSupport {

	/**
	 * 获取所有的打印机信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PrinterVO> queryAll() {
		String sql = "SELECT P.PRINTERID,P.PRINTERNAME,P.PRINTERSPEED,P.STATUS FROM PRINTER P";
		return super.getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet result, int num) throws SQLException {
				PrinterVO p = new PrinterVO();
				p.setPrinterId(result.getInt("PRINTERID"));
				p.setPrinterName(result.getString("PRINTERNAME"));
				p.setSpeed(result.getInt("PRINTERSPEED"));
				p.setStatus(result.getString("STATUS"));
				return p;
			}

		});
	}

	/**
	 * 根据id获取打印机信息
	 * 
	 * @param printerId
	 * @return
	 */
	public PrinterVO queryPrinterById(int printerId) {
		String sql = "SELECT * FROM PRINTER WHERE PRINTERID=" + printerId;
		return (PrinterVO) getJdbcTemplate().query(sql, new ResultSetExtractor() {

			public PrinterVO extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					PrinterVO pri = new PrinterVO();
					pri.setPrinterId(rs.getInt("PRINTERID"));
					pri.setPrinterName(rs.getString("PRINTERNAME"));
					pri.setSpeed(rs.getInt("PRINTERSPEED"));
					pri.setStatus(rs.getString("STATUS"));
					return pri;
				}
				return null;
			}
		});
	}

	public int saveOrUpdate(PrinterVO pri) {
		StringBuffer sb = new StringBuffer();
		if (pri.getPrinterId() > 0) {
			sb.append("UPDATE PRINTER SET ");
			sb.append(" PRINTERNAME = '" + pri.getPrinterName() + "',");
			sb.append(" PRINTERSPEED = " + pri.getSpeed());
			sb.append(" WHERE PRINTERID = " + pri.getPrinterId());
			return getJdbcTemplate().update(sb.toString());
		} else {
			sb.append("INSERT INTO PRINTER(PRINTERID,PRINTERNAME,PRINTERSPEED,STATUS) VALUES(NULL,?,?,'F')");
			Object[] obj = new Object[] { pri.getPrinterName(), pri.getSpeed() };
			return getJdbcTemplate().update(sb.toString(), obj);
		}
	}

	/**
	 * 修改打印机状态
	 * 
	 * @param pri
	 * @return
	 */
	public int updatePriStatus(PrinterVO pri) {
		String sql = "UPDATE PRINTER P SET P.STATUS = '" + pri.getStatus() + "' WHERE P.PRINTERID = "
				+ pri.getPrinterId();
		return getJdbcTemplate().update(sql);
	}

	/**
	 * 修改全部的打印机状态
	 * 
	 * @param pri
	 * @return
	 */
	public int updateAllPriStatus(PrinterVO pri) {
		String sql = "UPDATE PRINTER P SET P.STATUS = '" + pri.getStatus()+"'";
		return getJdbcTemplate().update(sql);
	}
}
