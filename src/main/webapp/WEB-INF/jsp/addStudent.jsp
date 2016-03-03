<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LarkU</title>
<link type="text/css" rel="stylesheet" media="all"
	href="${pageContext.request.contextPath}/css/larkU.css">
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/larkUHeader.jsp" />
	<form
		action="${pageContext.request.contextPath}/registration/webapp/admin/addStudent"
		method="post">
		<table border="1" width="200">
			<tr>
				<td>Name</td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td>Phone Number</td>
				<td><input type="text" name="phoneNumber" /></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><select id="statusInput" name="status" name="status">
						<option value="FULL_TIME">Full Time</option>
						<option value="PART_TIME">Part time</option>
						<option value="HIBERNATING">Hibernating</option>
				</select></td>
			</tr>
			<tr>
				<td><input type="submit" name="addStudent" value="Add Student" /></td>
			</tr>
		</table>
	</form>
	<br/> <a href="${pageContext.request.contextPath}/index.jsp">Home</a></td>
</body>
</html>