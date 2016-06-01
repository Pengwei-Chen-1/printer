package com.cpw.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;

import com.cpw.dao.PrinterDao;
import com.cpw.dao.TaskDao;
import com.cpw.vo.PrinterVO;
import com.cpw.vo.TaskVO;

/**
 * 每分钟的第30s扫描所有的打印任务并输出任务当前信息
 * 
 * @author epeicen
 *
 */
public class ScanTimerTask extends TimerTask {

	private PrinterDao printerDao;
	private TaskDao taskDao;

	private TaskDao getTaskDao() {
		if (taskDao == null)
			taskDao = (TaskDao) SpringContextHolder.getBean("taskDao");
		return taskDao;
	}

	private PrinterDao getPrinterDao() {
		if (printerDao == null)
			printerDao = (PrinterDao) SpringContextHolder.getBean("printerDao");
		return printerDao;
	}

	@Override
	public void run() {
		List<TaskVO> taskList = getTaskDao().queryPrinterTaskList();
		List<PrinterVO> printerList = getPrinterDao().queryAll();
		StringBuffer sb = new StringBuffer();
		for (TaskVO task : taskList) {
			sb.append("打印机:" + task.getPrinterName() + ",当前任务编号:" + task.getTaskId() + ",任务总张数:" + task.getTotalNum()
					+ ",剩余打印张数:" + task.getLastNum() + "\n");
		}
		for (PrinterVO printer : printerList) {
			if (isIn(printer.getPrinterId(), taskList))
				continue;
			if (StringUtils.equals(printer.getStatus(), "F"))
				sb.append("打印机:" + printer.getPrinterName() + ",状态: 空闲\n");
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
		System.out.println("************" + sf.format(new Date()) + "***************");
		System.out.println(sb.toString());
		System.out.println("****************************************************");
	}

	private boolean isIn(int printerId, List<TaskVO> taskList) {
		for (TaskVO taskVO : taskList) {
			if (taskVO.getPrinterId() == printerId)
				return true;
		}
		return false;
	}
}
