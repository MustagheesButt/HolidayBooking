<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Manage Roles - Holiday Booking System</title>
  </jsp:attribute>
  
  <jsp:body>
    <div class="container-fluid min-vh-100 text-bg-secondary">
      <jsp:include page="_sidebar.jsp" />

      <section class="row">
        <section class="card col-4 m-4 p-4 text-bg-dark">
          <h2>All Roles</h2>
          <table class="table text-bg-dark">
            <thead>
              <tr>
                <th>ID</th>
                <th>Role</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="role" items="${roles}">
                <tr>
                  <td>${role.id}</td>
                  <td>${role.title}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </section>
      </section>
    </div>
  </jsp:body>
</t:layout>