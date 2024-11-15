<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" type="image/x-icon" href="/images/SiSt.ico">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/resources/cdn-main/example.css">
<script src="/resources/cdn-main/example.js"></script>
<style>
 span.material-symbols-outlined{
    vertical-align: text-bottom;
 }  
</style>
</head>
<body>
<header>
  <h1 class="main"><a href="#" style="position: absolute;top:30px;">Jam HOme</a></h1>
  <ul>  
    <li><a href="#">로그인</a></li>
    <li><a href="#">회원가입</a></li>
  </ul>
</header>
<div>
  <xmp class="code"> 
	  list.jsp
  </xmp>
  
  <table>
 	<caption style="text-align:right;">
  		<a href="/board/register">글쓰기</a>
  	</caption>
  	<thead>
  		<tr>
           <th>#번호</th>
           <th>제목</th>
           <th>작성자</th>
           <th>작성일</th>
           <th>수정일</th>        
         </tr>
      </thead>
      <tbody>
      	<c:choose>
      		<c:when test="${ empty list }">
	      		<tr>
	      			<td colspan="5">no board....</td>
	      		</tr>
      		</c:when>
      		<c:otherwise>
      			<c:forEach items="${ list }" var="board">
      				<tr>
      					<td><c:out value="${ board.bno }"></c:out></td>
      					<td><a href="/board/get?bno=${ board.bno }"><c:out value="${ board.title }"></c:out></a></td>
      					<%-- <td><a href="/board/get/${ board.bno }"><c:out value="${ board.title }"></c:out></a></td> --%>
      					<td><c:out value="${ board.writer }"></c:out></td>
      					<td><fmt:formatDate value="${ board.regdate }" pattern="yyyy-MM-dd"/></td>
      					<td><fmt:formatDate value="${ board.updatedate }" pattern="yyyy-MM-dd"/></td>
      				</tr>
      			</c:forEach>
      		</c:otherwise>
      	</c:choose>
      </tbody>
  </table>
  
  <script>
  // rttr.addFlashAttribute("result", board.getBno());
  $(function(){
	  var result = '<c:out value="${result}"/>';
	  var del = '<c:out value="${del}"/>';
	  
	  checkModal(result);
	  
	  // 뒤로 가기 X
	  history.replaceState({},null,null);
	  
	  function checkModal(result){
		  if( result == ""||history.state)return;
		  if( parseInt(result) > 0 ){
			  alert(`\${result}번이 등록되었습니다.`);
		  } // if
		  if (result == "SUCCESS"){
			  alert(`\${del}번이 삭제되었습니다.`);
			  //alert(`<c:out value="${del}"/>번이 삭제되었습니다.`);
		  }
	  } // checkModal
	  

  })
  </script>
  
  
</div> 
</body>
</html>







 