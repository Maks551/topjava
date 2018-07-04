<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%--@elvariable id="meal" type="ru.javawebinar.topjava.model.Meal"--%>
    <form method="POST" action="meals" name="frmAddMeal">
        Meal ID : <input type="text" readonly="readonly" name="mealId"
                         value="<c:out value="${meal.id}" />" /><br />
        Date Time : <input
            type="datetime-local" name="date"
            value="<c:out value="${meal.dateTime}" />" /> <br />
        Description : <input
            type="text" name="description"
            value="<c:out value="${meal.description}" />" /> <br />
        Calories : <input type="text" name="calories"
                       value="<c:out value="${meal.calories}" />" /> <br /> <input
            type="submit" value="Submit" />
    </form>
</body>
</html>
