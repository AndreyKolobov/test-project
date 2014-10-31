<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.sql.ResultSetMetaData"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Table</title>
<LINK REL=STYLESHEET HREF="styles/style.css" TYPE="text/css">
</head>
<body>
	<%
		String query = (String) session.getAttribute("selectQuery");
	%>
	<H1><%=query%></H1>
	<form method="post" action="Controller">
		<center>
			<table border="1">
				<%
					int columnCount = (Integer) request.getAttribute("numberOfColumns");
					List<String> tableInfo = (List<String>) request
							.getAttribute("data");
					ResultSetMetaData rsmd = (ResultSetMetaData) request
							.getAttribute("ResultSetMetaData");
				%>
				<tr>
					<%
						for (int i = 1; i <= columnCount; i++) {
					%>
					<th><%=rsmd.getColumnName(i)%> <%
 	}
 %>
				</tr>
				<%
					int k = 0;
					while (k < tableInfo.size()) {
						//String column = it.next();
				%>
				<tr>
					<%
						for (int i = 1; i <= columnCount; i++) {
					%>
					<td style="background-color: #66CCFF;"><%=tableInfo.get(k)%></td>
					<%
						k++;
							}
					%>
				</tr>
				<%
					}
				%>
			</table>
		</center>
	</form>
</body>
</html>