package com.cpw.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.cpw.dao.PrinterDao;
import com.cpw.util.AssignTimerTask;
import com.cpw.util.ScanTimerTask;
import com.cpw.vo.PrinterVO;

@SuppressWarnings("deprecation")
public class PrinterController extends SimpleFormController {

	private String viewpage;
	private String updatePage;
	private PrinterDao printerDao;

	public String getUpdatePage() {
		return updatePage;
	}

	public void setUpdatePage(String updatePage) {
		this.updatePage = updatePage;
	}

	public PrinterDao getPrinterDao() {
		return printerDao;
	}

	public void setPrinterDao(PrinterDao printerDao) {
		this.printerDao = printerDao;
	}

	public String getViewpage() {
		return viewpage;
	}

	public void setViewpage(String viewpage) {
		this.viewpage = viewpage;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		List<PrinterVO> printerList = new ArrayList<PrinterVO>();
		Map<String, Object> mp = new HashMap<String, Object>();
		int printerId = 0;
		String id = request.getParameter("printerId");
		if (StringUtils.isNotBlank(id))
			printerId = Integer.parseInt(request.getParameter("printerId"));
		String type = request.getParameter("type");
		if (StringUtils.equals(type, "update")) {
			PrinterVO pri = new PrinterVO();
			pri = printerDao.queryPrinterById(printerId);
			mp.put("printer", pri);
			return new ModelAndView(getUpdatePage(), mp);
		} else if (StringUtils.equals(type, "del")) {

		} else if (StringUtils.equals(type, "add")) {
			String printerName = request.getParameter("printerName");
			int printerSpeed = 0;
			String speed = request.getParameter("speed");
			if (StringUtils.isNotBlank(speed))
				printerSpeed = Integer.parseInt(speed);
			PrinterVO tempPri = new PrinterVO();
			tempPri.setPrinterName(printerName);
			tempPri.setSpeed(printerSpeed);
			printerDao.saveOrUpdate(tempPri);
		} else if (StringUtils.equals(type, "edit")) {
			String printerName = request.getParameter("printerName");
			int printerSpeed = 0;
			String speed = request.getParameter("speed");
			if (StringUtils.isNotBlank(speed))
				printerSpeed = Integer.parseInt(speed);
			PrinterVO tempPri = new PrinterVO();
			tempPri.setPrinterId(printerId);
			tempPri.setPrinterName(printerName);
			tempPri.setSpeed(printerSpeed);
			printerDao.saveOrUpdate(tempPri);
		} else if (StringUtils.equals(type, "init")) {
			PrinterVO pri = new PrinterVO();
			pri.setStatus("F");
			// 修改全部printer status为P
			getPrinterDao().updateAllPriStatus(pri);
			Timer time = new Timer();
			time.schedule(new AssignTimerTask(), 0, 10 * 1000);
			// 每分钟的第30s将当前所有的task信息打印出来
			Calendar calendar = Calendar.getInstance();
			int s = calendar.get(Calendar.SECOND);
			if (s > 30)
				calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);
			calendar.set(Calendar.SECOND, 30);
			Timer scanTimer = new Timer();
			scanTimer.schedule(new ScanTimerTask(), calendar.getTime(), 60 * 1000);
		}
		printerList = printerDao.queryAll();
		mp.put("test", "cpw");
		mp.put("list", printerList);
		return new ModelAndView(getViewpage(), mp);
	}

}
