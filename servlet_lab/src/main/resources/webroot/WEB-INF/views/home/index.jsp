<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/start.jsp"/>

<a class="btn btn-primary" href="/department/add">Add new department</a>

<c:choose>
    <c:when test="${departments.size() == 0}">
        <div class="alert alert-danger" role="alert">There are no departments. Add one to begin.</div>
    </c:when>
    <c:otherwise>
        <h3>Current departments:</h3>
        <ul>
                <%--@elvariable id="departments" type="java.util.List<models.Department>"--%>
            <c:forEach items="${departments}" var="department">
                <li><a href="/department/${department.id}">${department.name}</a></li>
            </c:forEach>
        </ul>
    </c:otherwise>
</c:choose>

<jsp:include page="../includes/end.jsp"/>