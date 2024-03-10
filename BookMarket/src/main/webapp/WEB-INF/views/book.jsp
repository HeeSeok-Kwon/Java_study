<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/resources/js/controllers.js"></script>
<title>도서 상세 정보</title>
</head>
<body>
	<nav class="navbar navbar-expand navbar-dark bg-dark">
		<div class="container">
			<div class="navbar-header">
				<!-- href="./home" 경로를 찾지 못해 에러가 출력됨 -->
				<!-- <a class="navbar-brand" href="./home">Home</a> -->
				<a class="navbar-brand" href="<c:url value="/books" />">Home</a>
			</div>
		</div>
	</nav>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">도서 정보</h1>	
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-md-4">
				<!-- <img src="<c:url value="/resources/images/${book.bookId}.png" />" style="witdh:100%" /> -->
				<c:choose>
					<c:when test="${book.getBookImage() == null}">
						<!-- <img 
							src="<c:url value="/resources/images/${book.getBookId()}.png"/>" 
							style="width:100%" /> -->
						<img 
							src="<c:url value="/resources/images/${book.fileName}"/>" 
							style="width:100%" />
					</c:when>
					<c:otherwise>
						<img 
						src="<c:url value="/resources/images/${book.fileName}"/>" 
						style="width:100%" />
					</c:otherwise>
				</c:choose>
			</div>
			<div class="col-md-8">
				<h3>${book.name}</h3>
				<p>${book.description}</p>
				<br />
				<p><b>도서코드 : </b><span class="badge badge-info">${book.bookId}</span></p>
				<p><b>저자 : </b>${book.author}</p>
				<p><b>출판사 : </b>${book.publisher}</p>
				<p><b>출판일 : </b>${book.releaseDate}</p>
				<p><b>분류 : </b>${book.category}</p>
				<p><b>재고수 : </b>${book.unitsInStock}</p>
				<h4>${book.unitPrice}원</h4>
				<br />
				<form:form name="addForm" method="put">
				<p>
					<a href="javascript:addToCart('../cart/add/${book.bookId}')" class="btn btn-primary">도서주문 &raquo;</a>
					<a href="<c:url value='/cart' />" class="btn btn-warning">장바구니 &raquo;</a>
					<a href="<c:url value='/books' />" class="btn btn-primary">도서 목록 &raquo;</a>
					<sec:authorize access="isAuthenticated()">
						<a href="<c:url value='/books/update?id=${book.bookId}' />" class="btn btn-success">수정 &raquo;</a>
						<a href="<c:url value="javascript:deleteConfirm('${book.bookId}')" />" class="btn btn-danger">삭제 &raquo;</a>
					</sec:authorize>
				</p>
				</form:form>
			</div>
		</div>
	</div>
	<hr />
	<footer class="container">
		<p>&copy; WebMarket</p>
	</footer>

</body>
</html>