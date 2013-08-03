<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search page</title>
</head>
<body>
	<form action="/search" method="post">
		<div>
			<textarea name="query" rows="3" cols="60"></textarea>
		</div>
		<div>
			<input type="submit" value="Post Query" />
		</div>
	</form>
	<br />
	<br />

	<%
		String searchResult = request.getParameter("searchResult");
		if (searchResult != null && !searchResult.isEmpty()) {
			response.getWriter().println(searchResult);
		}
	%>
</body>
</html>