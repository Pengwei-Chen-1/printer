package com.cpw.vo;

/**
 * 打印机
 * 
 * @author epeicen
 *
 */
public class PrinterVO {

	private int printerId;
	private String printerName;
	private int speed;
	private String status;

	public int getPrinterId() {
		return printerId;
	}

	public void setPrinterId(int printerId) {
		this.printerId = printerId;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printName) {
		this.printerName = printName;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
