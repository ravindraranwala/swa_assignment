<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search page</title>
<style>
#customers {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	width: 100%;
	border-collapse: collapse;
}

#customers td,#customers th {
	font-size: 1em;
	border: 1px solid #98bf21;
	padding: 3px 7px 2px 7px;
}

#customers th {
	font-size: 1.1em;
	text-align: left;
	padding-top: 5px;
	padding-bottom: 4px;
	background-color: #A7C942;
	color: #ffffff;
}

#customers tr.alt td {
	color: #000000;
	background-color: #EAF2D3;
}
</style>

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
	<%--
		try{
			String searchResult = request.getParameter("searchResult");
			if (searchResult != null && !searchResult.isEmpty()) {
		response.setContentType(s)
		response.getWriter().println(searchResult);
		
			}finally{
		response.getWriter().close()
			}
		}
	--%>

	<table id="customers">
		<c:forEach items="${searchResult}" var="result">
			<tr>
				<td>${result.title}</td>
				<td>${result.content}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>