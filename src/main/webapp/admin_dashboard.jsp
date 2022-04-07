<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Dashboard - Holiday Booking System</title>
  </jsp:attribute>
  
  <jsp:body>
    <aside>
      <a href="/admin/manage-employees">Manage Employees</a>
      <a href="/admin/manage-roles">Manage Roles</a>
      <a href="/admin/manage-departments">Manage Departments</a>
    </aside>

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
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>1</td>
            <td>Steve Jobs</td>
            <td>steve.j@apple.com</td>
          </tr>
        </tbody>
      </table>
    </section>
  </jsp:body>
</t:layout>