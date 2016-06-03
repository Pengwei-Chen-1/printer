<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Title</title>
<script LANGUAGE="JavaScript">
	window.onload = function() {
		document.myform.submit();
	}
</script>
</head>
<body>
	<form name="myform" action="printerAction.do" method="post">
		<input name="type" type="hidden" value="init"/>
		<!--  请输入<input name="msg" type="text" /> <input type="submit" value="提交">-->
	</form>
</body>
</html>