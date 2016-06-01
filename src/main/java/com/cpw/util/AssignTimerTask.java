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
 * 产生随机数，并且分配对应的打印机
 * 
 * @author epeicen
 *
 */
public class AssignTimerTask extends TimerTask {

	private TaskDao taskDao;
	private PrinterDao printerDao;

	private PrinterDao getPrinterDao() {
		if (printerDao == null)
			printerDao = (PrinterDao) SpringContextHolder.getBean("printerDao");
		return printerDao;
	}

	private TaskDao getTaskDao() {
		if (taskDao == null)
			taskDao = (TaskDao) SpringContextHolder.getBean("taskDao");
		return taskDao;
	}

	@Override
	public void run() {
		// 产生随机数
		double d = Math.random() * 100;
		// 随机数
		int ran = new Double(d).intValue() + 50;
		// 随机分配用户id
		int userRan = ran % 3 + 1;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		System.out.println("随机数产生: " + ran + "---time: " + sf.format(new Date()));
		// 查询所有的task中用户对应的剩余打印数
		List<TaskVO> taskList = getTaskDao().queryLastNum();
		// 获取所有的打印机信息
		List<PrinterVO> printerList = getPrinterDao().queryAll();
		// 遍历查询剩余时间最少的打印机
		int tempTime = 0;
		PrinterVO tempPri = new PrinterVO();
		for (PrinterVO pri : printerList) {
			int lastNum = getLastNumByPrinterId(pri.getPrinterId(), taskList);
			int time = (lastNum + ran) / pri.getSpeed();
			if (tempTime == 0 || time < tempTime) {
				tempTime = time;
				tempPri = pri;
			}
		}
		// 创建新的task
		TaskVO task = new TaskVO();
		task.setPrinterId(tempPri.getPrinterId());
		task.setUserId(userRan);
		task.setTotalNum(ran);
		task.setLastNum(ran);
		task.setStatus("W");
		// 插入task表中
		getTaskDao().saveOrUpdate(task);
		// 查询当前打印机，若打印机状态为空闲时，开启该打印机线程进行打印
		if (StringUtils.equals(tempPri.getStatus(), "F")) {
			Thread t = new PrinterThread(tempPri);
			t.start();
		}
	}

	/**
	 * 根据打印机id查询对应剩余打印张数
	 * 
	 * @param printerId
	 * @param taskList
	 * @return
	 */
	private int getLastNumByPrinterId(int printerId, List<TaskVO> taskList) {
		int num = 0;
		for (TaskVO task : taskList) {
			if (task.getPrinterId() == printerId)
				return task.getLastNumCount();
		}
		return num;
	}

}
