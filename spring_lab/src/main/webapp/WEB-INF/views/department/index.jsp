<jsp:useBean id="department" scope="request" type="com.sith.spring_lab.models.Department"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/start.jsp"/>
<c:choose>
    <c:when test="${department==null}">
        <div class="alert alert-danger" role="alert">There is no department with id ${id}!</div>
    </c:when>
    <c:otherwise>
        <a class="btn btn-primary" role="button" href="/faculty/add/${id}">Add new faculty</a>
        <a class="btn btn-warning" role="button" href="/department/edit/${id}">Edit</a>
        <form action="/department/delete/${id}" method=post style="display:inline;">
            <button class="btn btn-danger" type="submit" role="button">Delete</button>
        </form>
        <dl class="row">
            <dt class="col-sm-3">Name:</dt>
            <dd class="col-sm-9">${department.name}</dd>
            <dt class="col-sm-3">Phone number:</dt>
            <dd class="col-sm-9">${department.phoneNumber}</dd>
            <dt class="col-sm-3">Created at:</dt>
            <dd class="col-sm-9">${department.creationDate}</dd>
            <c:choose>
                <c:when test="${department.facultiesCount > 0}">
                    <dt class="col-sm-3">Current faculties:</dt>
                    <dd class="col-sm-9">
                        <ul>
                            <c:forEach var="faculty" items="${department.faculties}">
                                <li><a href="/faculty/${faculty.id}">${faculty.name}</a></li>
                            </c:forEach>
                        </ul>
                    </dd>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-warning" role="alert">This department has no faculties. Add one!</div>
                </c:otherwise>
            </c:choose>
        </dl>
    </c:otherwise>
</c:choose>
<jsp:include page="../includes/end.jsp"/>