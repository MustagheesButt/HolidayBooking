<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Manage Roles - Holiday Booking System</title>
  </jsp:attribute>
  
  <jsp:body>
    <div class="flex flex-col md:flex-row">
      <jsp:include page="_sidebar.jsp" />

      <section class="flex flex-col">
        <section class="m-5 p-5 bg-gray-200">
          <h2>Welcome ${admin.email}!</h2>
        </section>

        <section class="m-5 p-5 bg-gray-200">
          <h2>List of all Roles</h2>
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Role</th>
                <!-- <th>Actions</th> -->
              </tr>
            </thead>
            <tbody>
              <c:forEach var="role" items="${roles}">
                <tr>
                  <td>${role.id}</td>
                  <td>${role.title}</td>
                  <!-- <td class="flex justify-between">
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
                  </td> -->
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </section>
      </section>
    </div>
  </jsp:body>
</t:layout>