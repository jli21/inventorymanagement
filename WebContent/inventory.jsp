<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Computer Store</title>
		
		<style type="text/css">
			<%@ include file="css/styles.css" %>
		</style>
	</head>
	<body>
		<div>
			<h1>Inventory Management</h1>
			
			<div class="header">
				<a href="${pageContext.request.contextPath}/" class="header-button">VIEW ALL</a>
				<a href="${pageContext.request.contextPath}/add" class="header-button">ADD A COMPUTER</a>
			</div>
			<div>
			<form action="search">	
			<label>Search Bar   <input type="text" name="search_bar" /></label> 
			<input type="submit" value="Search" name="sumbit" class="button"/></label> 
                    <label>
                        <label><input class="radio" type="radio" name="search_attribute" value="name">&nbsp; Name &nbsp; &nbsp; </label>
                        <label><input class="radio" type="radio" name="search_attribute" value="description">&nbsp; Description &nbsp; &nbsp; </label>
                        <label><input class="radio" type="radio" name="search_attribute" value="price">&nbsp; Price &nbsp; &nbsp; </label>
		 </form>
			
			</div> <br>
		</div>
		<div>
			<table border="1">
				<tr>
					<th>Name</th>
					<th>Description</th>
					<th>Quantity</th>
					<th>Price</th>
				</tr>
				<c:forEach var="computer" items="${computers}">
					<tr>
						<td><c:out value="${computer.name}" /></td>
						<td><c:out value="${computer.type}" /></td>
						<td><c:out value="${computer.quantity}" /></td>
						<td><c:out value="${computer.price}" /></td>
						<td>
							<div>
								<a href="${pageContext.request.contextPath}/update?action=rent&id=${computer.id}" class="button">BUY</a>
								<a href="${pageContext.request.contextPath}/edit?id=${computer.id}" class="button">EDIT</a>
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>