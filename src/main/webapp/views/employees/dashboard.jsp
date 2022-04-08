<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Dashboard - Holiday Booking System</title>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.2/main.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.2/main.css">
  </jsp:attribute>
  
  <jsp:body>
    <div class="flex flex-col md:flex-row bg-gray-400">
      <aside class="flex flex-col">
        <a href="/request-holidays">Request Holidays</a>
        <a href="/manage-requests">Manage Roles</a>
        <a href="/profile">Profile</a>
      </aside>
  
      <section class="flex flex-col w-full">
        <section class="m-5 p-5 bg-gray-200">
          <h2 class="text-md">${employee.email}</h2>
          <h3 class="text-xl">${employee.fullName} - ${employee.department}</h3>
          <p>You have <strong>${employee.remainingHolidays}</strong> holidays remaining.</p>
        </section>
    
        <section class="m-5 p-5 bg-gray-200">
          <h1>Holiday Calendar</h1>
          <div id="calendar" class="w-1/2"></div>
        </section>
      </section>
    </div>

    <script>
      document.addEventListener('DOMContentLoaded', function() {
        const calendarEl = document.getElementById('calendar');
        const calendar = new FullCalendar.Calendar(calendarEl, {
          initialView: 'dayGridMonth',
          selectable: true,
          dateClick: function(info) {
            console.log(info)
          },
          select: function(info) {
            console.log(info)
          }
        });
        calendar.render();
      });
    </script>
  </jsp:body>
</t:layout>