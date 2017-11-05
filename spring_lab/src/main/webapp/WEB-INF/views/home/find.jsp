<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/start.jsp"/>
<h4>Departments: </h4>
<ul>
    <%--@elvariable id="departments" type="java.util.List<models.Department>"--%>
    <c:forEach items="${departments}" var="department">
        <li><a href="/department/${department.id}">${department.name}</a></li>
    </c:forEach>
</ul>
<h4>Faculties: </h4>
<ul>
    <%--@elvariable id="faculties" type="java.util.List<models.Faculty>"--%>
    <c:forEach items="${faculties}" var="faculty">
        <li><a href="/faculty/${faculty.id}">${faculty.name}</a></li>
    </c:forEach>
</ul>
<jsp:include page="../includes/end.jsp"/>