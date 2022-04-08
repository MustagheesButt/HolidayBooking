<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:layout>
  <jsp:attribute name="head">
    <title>My Profile - Holiday Booking System</title>
  </jsp:attribute>
  
  <jsp:body>
    <div class="flex flex-col md:flex-row">
      <aside class="flex flex-col">
        <a href="/request-holidays">Request Holidays</a>
        <a href="/manage-requests">Manage Roles</a>
        <a href="/profile">Profile</a>
      </aside>
  
      <section class="flex flex-col">
        <section class="m-5 p-5">
          <h2 class="text-xl">${employee.email} - ${employee.department}</h2>
          <p>You have <strong>${employee.holidays}</strong> holidays remaining.</p>
        </section>
    
        <section>
          <h1>Holiday Calendar</h1>
        </section>
      </section>
    </div>
  </jsp:body>
</t:layout>