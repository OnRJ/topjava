<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="d" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%--
  Created by IntelliJ IDEA.
  User: Natasha
  Date: 15.06.2021
  Time: 21:01
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>

<form action="meals" method="post">

    <input type="hidden" value="${meal.id}" name="id">
    <p>
        <label for="dateTime">DateTime:</label>
        <input type="datetime-local" value="${meal.dateTime}" id="dateTime" name="dateTime">
    </p>

    <p>
        <label for="description">Description:</label>
        <input type="text" value="${meal.description}" id="description" name="description">
    </p>

    <p>
        <label for="calories">Calories:</label>
        <input type="text" value="${meal.calories}" id="calories" name="calories"/>
    </p>

    <p>
        <input type="submit" value="Save">
        <input type="button" value="Cancel" onclick="history.back();">
    </p>
</form>

</body>
</html>
