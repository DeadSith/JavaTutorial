<jsp:useBean id="department" scope="request" type="models.Department"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/start.jsp"/>
<c:if test="${error}">
    <div class="alert alert-danger" role="alert">
        Name can contain only letters. Phone number should be in form +xxxxxxxxxxx.
    </div>
</c:if>
<c:choose>
    <c:when test="${department!=null}">
        <form action="/department/edit/${id}" method="post">
            <div class="form-group row">
                <label for="name" class="col-sm-2 col-form-label">Name: </label>
                <div class="col-sm-10">
                    <input type="text" id="name" class="form-control" required pattern="[A-Za-z ]{5,}"
                           title="Name can contain only letters." name="name" value="${department.name}">
                </div>
            </div>
            <div class="form-group row">
                <label for="phoneNumber" class="col-sm-2 col-form-label">Phone number: </label>
                <div class="col-sm-10">
                    <input type="tel" id="phoneNumber" required pattern="\+\d{10,15}"
                           title="Input phone number in form +xxxxxxxxxxx" class="form-control" name="phoneNumber"
                           value="${department.phoneNumber}">
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </c:when>
    <c:otherwise>
        <div class="alert alert-danger" role="alert">There is no department with id ${id}!</div>
    </c:otherwise>
</c:choose>
<jsp:include page="../includes/end.jsp"/>