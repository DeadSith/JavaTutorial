<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/start.jsp"/>
<c:if test="${error}">
    <div class="alert alert-danger" role="alert">
        Name, teacher names and subjects can contain only letters. Use picker to set date.
    </div>
</c:if>
<form action="/faculty/add/${id}" method="post">
    <div class="form-group row">
        <label for="name" class="col-sm-2 col-form-label">Name: </label>
        <div class="col-sm-10">
            <input id="name" type="text" class="form-control" name="name" pattern="[A-Za-z ]{5,}"
                   title="Name can contain only letters." required placeholder="Enter name">
        </div>
    </div>
    <div class="form-group row">
        <label for="creationDate" class="col-sm-2 col-form-label">Creation date: </label>
        <div class="col-sm-10">
            <input type="date" id="creationDate" max="<%=LocalDate.now()%>" required class="form-control"
                   name="creationDate">
        </div>
    </div>
    <div class="form-group row">
        <label for="teachers" class="col-sm-2 col-form-label">Teachers(separate with new line): </label>
        <div class="col-sm-10">
            <textarea id="teachers" class="form-control" pattern="[A-Za-z \n\r]*"
                      title="Teacher name can only contain letters" name="teachers" rows="4"></textarea>
        </div>
    </div>
    <div class="form-group row">
        <label for="subjects" class="col-sm-2 col-form-label">Subjects(separate with new line): </label>
        <div class="col-sm-10">
            <textarea id="subjects" class="form-control" pattern="[A-Za-z \n\r]*"
                      title="Subject name can only contain letters" name="subjects" rows="4"></textarea>
        </div>
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
<jsp:include page="../includes/end.jsp"/>
<script src="/static/js/validation.js"></script>