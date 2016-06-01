package com.cpw.util;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.cpw.dao.PrinterDao;
import com.cpw.dao.TaskDao;
import com.cpw.vo.PrinterVO;
import com.cpw.vo.TaskVO;

/**
 * 打印线程,每个打印线程在运行时，需对
 * 
 * @author epeicen
 *
 */
public class PrinterThread extends Thread {

	private PrinterVO printer;
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

	public PrinterThread(PrinterVO pri) {
		this.printer = pri;
	}

	@Override
	public void run() {
		// 1.更新printer status 为'B'
		PrinterVO pri = new PrinterVO();
		pri.setPrinterId(printer.getPrinterId());
		pri.setStatus("B");
		getPrinterDao().updatePriStatus(pri);
		System.out.println("printer: " + printer.getPrinterId() + "---start work---");
		// 2.创建一个timertask
		Timer time = new Timer();
		time.schedule(new PrinterTimerTask(), 0, 1000);
		super.run();
	}

	/**
	 * 打印机的timertask，每秒打印多张 查询当前打印机对应是否有正在打印的task，有的话更新task；
	 * 没有的话在剩余的task状态为W的task中选中一个更改状态并更新lastnum； 若没有状态为P和W时，更改printer的状态为F
	 * 
	 * @author epeicen
	 *
	 */
	class PrinterTimerTask extends TimerTask {

		@Override
		public void run() {
			// 查询当前打印机对应是否有正在打印的task
			TaskVO task = new TaskVO();
			task.setPrinterId(printer.getPrinterId());
			task.setStatus("P");
			List<TaskVO> printingTaskList = getTaskDao().queryTaskList(task);
			// 有的话更新task,根据打印机速度更新task剩余张数，若剩余张数小于打印速度，就将剩余张数置为0，并修改task
			// status为D
			if (printingTaskList != null && printingTaskList.size() > 0) {
				TaskVO printingTask = printingTaskList.get(0);
				TaskVO tempTask = new TaskVO();
				tempTask.setTaskId(printingTask.getTaskId());
				int lastNum = printingTask.getLastNum();
				if (lastNum <= printer.getSpeed()) {
					lastNum = 0;
					tempTask.setStatus("D");
				} else {
					lastNum -= printer.getSpeed();
				}
				tempTask.setLastNum(lastNum);
				getTaskDao().updateTask(tempTask);
				System.out.println("printer: " + printer.getPrinterId() + "---taskid:" + printingTask.getTaskId()
						+ "---lastNum:" + lastNum);
			} else {
				// 查询当前打印机对应是否有需要打印的task
				task.setStatus("W");
				List<TaskVO> waitTaskList = getTaskDao().queryTaskList(task);
				if (waitTaskList != null && waitTaskList.size() > 0) {
					// 在剩余的task状态为W的task中选中一个更改状态并更新lastnum
					TaskVO waitTask = waitTaskList.get(0);
					TaskVO tempTask = new TaskVO();
					tempTask.setTaskId(waitTask.getTaskId());
					tempTask.setStatus("P");
					int lastNum = waitTask.getLastNum();
					if (lastNum <= printer.getSpeed()) {
						lastNum = 0;
						tempTask.setStatus("D");
					} else {
						lastNum -= printer.getSpeed();
					}
					tempTask.setLastNum(lastNum);
					getTaskDao().updateTask(tempTask);
					System.out.println("printer: " + printer.getPrinterId() + "---taskid:" + waitTask.getTaskId()
							+ "---lastNum:" + lastNum);
				} else {
					// 若没有状态为P和W时，更改printer的状态为F
					PrinterVO tempPri = new PrinterVO();
					tempPri.setPrinterId(printer.getPrinterId());
					tempPri.setStatus("F");
					getPrinterDao().updatePriStatus(tempPri);
					this.cancel();
					System.out.println("printer: " + printer.getPrinterId() + "---work done---free---");
				}
			}
		}

	}

}
