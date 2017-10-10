<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.Department" %>
<%@ page import="java.util.List" %>
<jsp:include page="../includes/start.jsp"/>

<a class="btn btn-primary" href="/department/add">Add new department</a>
<%
    List<Department> departments = (List<Department>) request.getAttribute("departments");
    if (departments.size() == 0) {
%>
<div class="alert alert-danger" role="alert">There are no departments. Add one to begin.</div>
<%
} else {
%>
<h3>Current departments:</h3>
<ul>
    <%--@elvariable id="departments" type="java.util.List<models.Department>"--%>
    <c:forEach items="${departments}" var="department">
        <li><a href="/department/${department.id}">${department.name}</a></li>
    </c:forEach>

</ul>
<%}%>
<jsp:include page="../includes/end.jsp"/>