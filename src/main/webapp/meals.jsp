<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
    <style>
        td {
            padding: 10px;
            text-align: left;
        }
    </style>
    <h3><a href="index.html">Home</a></h3>
    <h2 align="center">Meal</h2>
    <br/>
    <table>
        <thead>
            <th>#</th>
            <th>Дата/Час</th>
            <th>Опис</th>
            <th>Калорії</th>
        </thead>

        <%--@elvariable id="mealList" type="java.util.List"--%>
        <%--@elvariable id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"--%>
        <tbody>

            <c:forEach var="meal" items="${mealList}">
                <c:if test="${meal.exceed}"><tr style="color: red"></c:if>
                <c:if test="${!meal.exceed}"><tr style="color: green"></c:if>
                    <td>#</td>
                    <td>${meal.dateTime.toLocalDate()}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>