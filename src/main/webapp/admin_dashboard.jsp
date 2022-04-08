<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Dashboard - Holiday Booking System</title>
  </jsp:attribute>
  
  <jsp:body>
    <div class="flex flex-col md:flex-row">
      <aside class="flex flex-col">
        <a href="/admin/manage-employees">Manage Employees</a>
        <a href="/admin/manage-roles">Manage Roles</a>
        <a href="/admin/manage-departments">Manage Departments</a>
      </aside>
  
      <section class="flex flex-col">
        <section>
          <h2>Welcome ${admin.email}!</h2>
        </section>
    
        <section>
          <h2>List of all employees</h2>
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Department</th>
                <th>Role</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="employee" items="${employees}">
                <tr>
                  <td>${employee.id}</td>
                  <td>${employee.fullName}</td>
                  <td>${employee.email}</td>
                  <td>${employee.department}</td>
                  <td>${employee.role}</td>
                  <td class="flex justify-between">
                    <a href="/admin/edit-employee/${employee.id}">
                      <svg aria-hidden="true" focusable="false" class="h-6 w-6">
                        <use xlink:href="#pencil"></use>
                      </svg>
                    </a>
                    <a href="/admin/delete-employee/${employee.id}">
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