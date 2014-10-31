<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import= "a00911667.core.util.CookieUtilities"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to COMP 3613</title>
<LINK REL=STYLESHEET HREF="styles/style.css" TYPE="text/css">
<SCRIPT language="javascript" type="text/javascript">
function submitIt(myForm) {
	errMsg = "";
	if (myForm.queryString.value == "") {
		errMsg = "Please, fill in your query";
	}
	
	if (errMsg != "") {
		alert(errMsg);
		myForm.queryString.value.focus();
		return false;
	}
	return true;
}

</SCRIPT>
</head>
<body>
<% String cookie = CookieUtilities.getCookieValue(request, "query", "");%>
	<H1>Welcome to COMP 3613:</H1>
	<H2>Andrey Kolobov A00911667</H2>
	<FORM method="post" name="form" action="Controller" onSubmit="return submitIt(this)">
		<CENTER>
		<H1>Database table MetaData</H1>
		<input type="text" name="queryString" value="<%=cookie%>"> <br>
		<INPUT TYPE="SUBMIT" value="Enter SELECT query">
 		</CENTER>
	</FORM>
</body>
</html>