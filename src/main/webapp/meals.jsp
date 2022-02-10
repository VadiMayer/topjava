<%--
  Created by IntelliJ IDEA.
  User: Вадим
  Date: 05.02.2022
  Time: 18:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3></h3>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Дата/Время</th>
            <th>Описание</th>
            <th>Калории</th>
        </tr>
        <c:forEach var="meal" items="${list}">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr style="color:${meal.excess ? "green" : "red"}">
                <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"/>
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" var="dateTime" value="${parsedDateTime}"/>
                <th>${dateTime}</th>
                <th>${meal.description}</th>
                <th>${meal.calories}</th>
            </tr>
        </c:forEach>
        </thead>
</table>

</body>
</html>
