<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="faculty" scope="request" type="models.Faculty"/>
<jsp:include page="../includes/start.jsp"/>
<c:if test="${error}">
    <div class="alert alert-danger" role="alert">
        Name, teacher names and subjects can contain only letters.
    </div>
</c:if>
<c:choose>
    <c:when test="${faculty==null}">
        <div class="alert alert-danger" role="alert">There is no faculty with id ${id}!</div>
    </c:when>
    <c:otherwise>
        <form action="/faculty/edit/${id}" method="post">
            <div class="form-group row">
                <label for="name" class="col-sm-2 col-form-label">Name: </label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="name" pattern="[A-Za-z ]{5,}"
                           title="Name can contain only letters." name="name" value="${faculty.name}">
                </div>
            </div>
            <div class="form-group row">
                <label for="teachers" class="col-sm-2 col-form-label">Teachers(separate with new line): </label>
                <div class="col-sm-10">
            <textarea id="teachers" class="form-control" name="teachers" pattern="[A-Za-z \n\r]*"
                      title="Teacher name can only contain letters" rows="4"><c:forEach var="teacher"
                                                                                        items="${faculty.teachers}">${teacher}&#13;&#10;</c:forEach>
            </textarea>
                </div>
            </div>
            <div class="form-group row">
                <label for="subjects" class="col-sm-2 col-form-label">Subjects(separate with new line): </label>
                <div class="col-sm-10">
            <textarea id="subjects" class="form-control" name="subjects" pattern="[A-Za-z \n\r]*"
                      title="Subject name can only contain letters"
                      rows="4"><c:forEach var="subject"
                                          items="${faculty.subjects}">${subject}&#13;&#10;</c:forEach>
            </textarea>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </c:otherwise>
</c:choose>
<jsp:include page="../includes/end.jsp"/>
<script src="/static/js/validation.js"></script>