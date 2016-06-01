<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	function change() {
		var str = document.getElementById("myText").value;
		document.getElementById("myText").value = str.replace(/\D/gi, "");
	}
</script>
<title>Insert title here</title>
</head>
<body>
	<form name="myform" action="printerAction.do" method="post">
		printer Name:<input name="printerName" type="text" value="${printer.printerName}" /><br/>
		printer Speed:<input id="myText" name="speed" type="text" value="${printer.speed}"
			onkeyup="change();" /><br/>
		<input type="submit" value="submit" />
		<input name="printerId" type="hidden" value="${printer.printerId}"/>
		<input name="type" type="hidden" value="edit"/>
	</form>
</body>
</html>