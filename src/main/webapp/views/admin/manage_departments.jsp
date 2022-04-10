<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Manage Departments - Holiday Booking System</title>
  </jsp:attribute>
  
  <jsp:body>
    <div class="flex flex-col md:flex-row">
      <jsp:include page="_sidebar.jsp" />

      <section class="flex flex-col">
        <section>
          <h2>Welcome ${admin.email}!</h2>
        </section>

        <section>
          <h2>List of all departments</h2>
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Department</th>
                <th>Employee Count</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="department" items="${departments}">
                <tr>
                  <td>${department.id}</td>
                  <td>${department.title}</td>
                  <td>${department.employees.size()}</td>
                  <td class="flex justify-between">
                    <a href="/admin/edit-department/${department.id}">
                      <svg aria-hidden="true" focusable="false" class="h-6 w-6">
                        <use xlink:href="#pencil"></use>
                      </svg>
                    </a>
                    <a href="/admin/delete-department/${department.id}">
                      <svg aria-hidden="true" focusable="false" class="h-6 w-6">
                        <use xlink:href="#trash"></use>
                      </svg>
                    </a>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </section>
      </section>
    </div>
  </jsp:body>
</t:layout>