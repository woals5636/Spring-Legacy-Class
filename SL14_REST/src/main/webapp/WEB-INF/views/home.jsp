<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Hello world! - ${ user }</h1>

	<P>The time on the server is ${serverTime}.</P>

	<h3>
		<a href="/time">/time</a>
	</h3>

	<h3>
		<a href="/scott/getText">/scott/getText</a>
	</h3>

	<h3>
		<a href="/scott/getText2">/scott/getText2</a>
	</h3>
	<h3>
		<a href="/scott/employees">/scott/employees</a>
	</h3>

	<h3>
		존재하는 사원 : <a href="/scott/employee/7369">/scott/employee/7369</a><br>
	</h3>

	<h3>
		존재하지 않는 사원 : <a href="/scott/employee/9999">/scott/employee/9999</a><br>
	</h3>

</body>
</html>
