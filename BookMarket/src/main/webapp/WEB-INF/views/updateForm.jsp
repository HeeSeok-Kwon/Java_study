<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet" />
<title>도서 상세 정보</title>
</head>
<body>
	<nav class="navbar navbar-expand navbar-dark bg-dark">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="<c:url value="/books" />">Home</a>
			</div>
		</div>
	</nav>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">도서 수정</h1>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-md-4">
				<img src="<c:url value="/resources/images/${book.fileName}" />" alt="image" style="width:100%" />
			</div>
		</div>
		
		<div class="col-md-7">
		
			<form:form modelAttribute="updateBook" 
					   action="./update?${_csrf.parameterName}=${_csrf.token}"
					   class="form-horizontal"
					   enctype="multipart/form-data">
				<fieldset>
					<div class="form-group row">
						<label class="col-sm-2 control-label">도서ID</label>
						<div class="col-sm-6" style="pading-top:10px;">
							<form:input id="bookId" path="bookId" type="hidden"
								class="form-contorl" value="${book.bookId}" />
							<span class="badge badge-info">${book.bookId}</span>
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 control-label">도서명</label>
						<div class="col-sm-6">
							<form:input path="name"
								class="form-contorl" value="${book.name}" />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 control-label">가격</label>
						<div class="col-sm-6">
							<form:input path="unitPrice"
								class="form-contorl" value="${book.unitPrice}" />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 control-label">저자</label>
						<div class="col-sm-6">
							<form:input path="author"
								class="form-contorl" value="${book.author}" />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 control-label">상세정보</label>
						<div class="col-sm-10">
							<!-- path가 아닌 id와 name을 지정해줄 것 -->
							<textarea id="description" name="description" cols="50" rows="2"
								class="form-contorl">${book.description}</textarea>
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 control-label">출판사</label>
						<div class="col-sm-6">
							<form:input path="publisher"
								class="form-contorl" value="${book.publisher}" />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 control-label">분야</label>
						<div class="col-sm-6">
							<form:input path="category"
								class="form-contorl" value="${book.category}" />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 control-label">재고수</label>
						<div class="col-sm-6">
							<form:input path="unitsInStock"
								class="form-contorl" value="${book.unitsInStock}" />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 control-label">출판일</label>
						<div class="col-sm-6">
							<form:input path="releaseDate"
								class="form-contorl" value="${book.releaseDate}" />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 control-label">상태</label>
						<div class="col-sm-4">
							<form:radiobutton path="condition" value="New" />New
							<form:radiobutton path="condition" value="Old" />Old
							<form:radiobutton path="condition" value="E-Book" />E-Book
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 control-label">도서이미지</label>
						<div class="col-sm-10">
							<form:input path="bookImage" type="file" class="form-control" />
						</div>
					</div>
					<div class="form-group row">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" class="btn btn-primary" value="수정" />
							<a href="<c:url value="/books" />" class="btn btn-primary">취소</a>
						</div>
					</div>
				</fieldset>
			</form:form>
		</div>
		<hr />
		<footer class="container">
			<p>&copy; BookMarket</p>
		</footer>
		
	</div>

</body>
</html>