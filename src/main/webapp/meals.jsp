<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/functions" prefix="f" %>
<%--
  Created by IntelliJ IDEA.
  User: Natasha
  Date: 13.06.2021
  Time: 00:36
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="meals?action=edit">Add Meal</a>
<table>
    <thead>
    <tr>
        <td>Date</td>
        <td>Description</td>
        <td>Calories</td>
        <td></td>
        <td></td>
    </tr>
    </thead>
    <jsp:useBean id="mealList" scope="request" type="java.util.List"/>
    <c:forEach items="${mealList}" var="meal">
        <tr class="${meal.excess eq true ? 'red' : 'green'}">
            <td><p>${f:formatDateTime(meal.dateTime)}</p></td>
            <td><p>${meal.description}</p></td>
            <td><p>${meal.calories}</p></td>
            <td><p><a href="meals?action=edit&id=<c:out value="${meal.id}"/>">Update</a></p></td>
            <td><p><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a></p></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>