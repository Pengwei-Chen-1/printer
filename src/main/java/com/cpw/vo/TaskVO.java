package com.cpw.vo;

/**
 * 任务实体
 * 
 * @author epeicen
 *
 */
public class TaskVO {
	private int taskId;
	private int userId;
	private int printerId;
	private int totalNum;
	private int lastNum;
	private String status;
	private int lastNumCount;
	private String printerName;

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public int getLastNumCount() {
		return lastNumCount;
	}

	public void setLastNumCount(int lastNumCount) {
		this.lastNumCount = lastNumCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPrinterId() {
		return printerId;
	}

	public void setPrinterId(int printerId) {
		this.printerId = printerId;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getLastNum() {
		return lastNum;
	}

	public void setLastNum(int lastNum) {
		this.lastNum = lastNum;
	}

}
