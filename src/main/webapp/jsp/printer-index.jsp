<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
	function update(printerId){
		document.getElementById("printerId").value=printerId;
		document.getElementById("type").value="update";
		document.myform.action="printerAction.do";
		document.myform.submit();
	}
	
	function del(printerId){
		document.getElementById("printerId").value=printerId;
		document.getElementById("type").value="del";
		document.myform.action="printerAction.do";
		document.myform.submit();
	}
	
	/**
	 * 控制输入框只能输入数字
	 */
	function change() {
		var str = document.getElementById("myText").value;
		document.getElementById("myText").value = str.replace(/\D/gi, "");
	}
</script>
<title>printer info list</title>
</head>
<body style="text-align: center;">
	<h1>printer info list</h1>
	<table align="center" width="50%" bordercolor="#000000" border="1"
		cellspacing="0">
		<tr>
			<th>Printer Name</th>
			<th>Print Speed</th>
			<th>Printer Status</th>
			<th>Operate</th>
		</tr>
		<c:forEach var="printer" items="${list}">
			<tr>
				<td style="display: none;">${printer.printerId}</td>
				<td>${printer.printerName}</td>
				<td>${printer.speed}</td>
				<td>${printer.status}</td>
				<td>
				<input type="button" value="update"
					onclick="update(${printer.printerId})" />
				<!-- <input type="button"
					value="del" onclick="del(${printer.printerId})" /></td> -->
			</tr>
		</c:forEach>
	</table>
	<br/>
	<form name="addform" action="printerAction.do" method="post">
		printer Name:<input name="printerName" type="text" value="" /><br/>
		printer Speed:<input id="myText" name="speed" type="text" value=""
			onkeyup="change();" /><br/>
		<input name="type" type="hidden" value="add"/>
		<input type="submit" value="add" />
		<input type="reset" value="reset"/>
	</form>
	<form name="myform" action="" method="post">
		<input id="printerId" name="printerId" type="hidden" value="" />
		<input id="type" name="type" type="hidden" value="" />
	</form>
</body>
</html>