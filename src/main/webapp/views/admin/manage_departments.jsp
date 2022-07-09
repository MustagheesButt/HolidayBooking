<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Departments | HolidaysManager</title>
  </jsp:attribute>
  
  <jsp:body>
    <div class="container-fluid text-bg-secondary min-vh-100">
      <jsp:include page="_sidebar.jsp" />

      <div class="row">
        <section class="col-6 m-5 p-5 text-bg-dark">
          <h2>All departments</h2>
          <table class="table text-bg-dark">
            <thead>
              <tr>
                <th>ID</th>
                <th>Department</th>
                <th># of Employees</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="department" items="${departments}">
                <tr>
                  <td>${department.id}</td>
                  <td>${department.title}</td>
                  <td>${department.employees.size()}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </section>
      </div>
    </div>
  </jsp:body>
</t:layout>