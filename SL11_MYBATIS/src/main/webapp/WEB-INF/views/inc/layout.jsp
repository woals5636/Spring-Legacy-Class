<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><tiles:getAsString name="title"/></title>
<link href="index.css" type="text/css" rel="stylesheet" />
</head>
<body>

	<!-- header 시작 -->
	<tiles:insertAttribute name="header" />
	<!-- header 종료 -->
	<div id="main">
		<!-- content 부분 -->
		<tiles:insertAttribute name="content" />
	</div>
	<!-- footer 시작 -->
	<tiles:insertAttribute name="footer" />
	<!-- footer 종료 -->
</body>
</html>
