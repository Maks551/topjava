<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
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
    <table>
        <thead>
            <th>#</th>
            <th>Date/Time</th>
            <th>Description</th>
            <th>Calories</th>
            <th colspan=2>Action</th>
        </thead>

        <%--@elvariable id="mealList" type="java.util.List"--%>
        <%--@elvariable id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"--%>
        <tbody>

        <c:forEach var="meal" items="${mealList}">
            <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="date" />
            <fmt:formatDate value="${parsedDate}" var="newParsedDate" type="date" pattern="dd.MM.yyyy HH:mm" />
            <c:if test="${meal.exceed}"><tr style="color: red"></c:if>
            <c:if test="${!meal.exceed}"><tr style="color: green"></c:if>
                <td>${meal.id}</td>
                <td>${newParsedDate}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td>
                    <c:if test="${meal.exceed}">
                        <a style="color: red" href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a>
                    </c:if>
                    <c:if test="${!meal.exceed}">
                        <a style="color: green" href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a>
                    </c:if>
                </td>
                <td>
                    <c:if test="${meal.exceed}">
                        <a style="color: red" href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a>
                    </c:if>
                    <c:if test="${!meal.exceed}">
                        <a style="color: green" href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <h3><a href="meals?action=insert&id=<c:out value="${meal.id}"/> ">Add Meal</a></h3>
</body>
</html>