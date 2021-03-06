<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/start.jsp"/>
<c:if test="${error}">
    <div class="alert alert-danger" role="alert">
        Name can contain only letters. Phone number should be in form +xxxxxxxxxxx. Use picker to set date.
    </div>
</c:if>
<form action="/department/add" method="post">
    <div class="form-group row">
        <label for="name" class="col-sm-2 col-form-label">Name: </label>
        <div class="col-sm-10">
            <input type="text" id="name" required pattern="[A-Za-z ]{5,}"
                   title="Name can contain only letters." class="form-control" name="name" placeholder="Enter name">
        </div>
    </div>
    <div class="form-group row">
        <label for="phoneNumber" class="col-sm-2 col-form-label">Phone number: </label>
        <div class="col-sm-10">
            <input type="tel" id="phoneNumber" required pattern="\+\d{10,15}"
                   title="Input phone number in form +xxxxxxxxxxx" class="form-control" name="phoneNumber"
                   placeholder="+38xxxxxxxxxx">
        </div>
    </div>
    <div class="form-group row">
        <label for="creationDate" class="col-sm-2 col-form-label">Creation date: </label>
        <div class="col-sm-10">
            <input type="date" id="creationDate" max="<%=LocalDate.now()%>" required class="form-control"
                   name="creationDate">
        </div>
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
<jsp:include page="../includes/end.jsp"/>
