<jsp:useBean id="department" scope="request" type="com.sith.spring_lab.models.Department"/>
<jsp:useBean id="faculty" scope="request" type="com.sith.spring_lab.models.Faculty"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/start.jsp"/>
<c:choose>
    <c:when test="${faculty==null}">
        <div class="alert alert-danger" role="alert">There is no faculty with id ${id}!</div>
    </c:when>
</c:choose>
<a class="btn btn-warning" role="button" href="/faculty/edit/${id}">Edit</a>
<form action="/faculty/delete/${id}" method=post style="display:inline;">
    <button class="btn btn-danger" type="submit" role="button">Delete</button>
</form>
<dl class="row">
    <dt class="col-sm-3">Name:</dt>
    <dd class="col-sm-9">${faculty.name}</dd>
    <dt class="col-sm-3">Created at:</dt>
    <dd class="col-sm-9">${faculty.creationDate}</dd>
    <dt class="col-sm-3">Department:</dt>
    <dd class="col-sm-9">
        <a href="/department/${department.id}">${department.name}</a>
    </dd>
    <dt class="col-sm-3">Current teachers:</dt>
    <dd class="col-sm-9">
        <ul>
            <c:forEach var="teacher" items="${faculty.splitTeachers}">
                <li>${teacher}</li>
            </c:forEach>
        </ul>
    </dd>
    <dt class="col-sm-3">Current subjects:</dt>
    <dd class="col-sm-9">
        <ul>
            <c:forEach var="subject" items="${faculty.splitSubjects}">
                <li>${subject}</li>
            </c:forEach>
        </ul>
    </dd>
</dl>
<jsp:include page="../includes/end.jsp"/>