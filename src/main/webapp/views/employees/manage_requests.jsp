<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Dashboard - Holiday Booking System</title>
  </jsp:attribute>
  
  <jsp:body>
    <div class="flex flex-col md:flex-row">
      <jsp:include page="_sidebar.jsp" />
  
      <section class="flex flex-col">
        <section class="m-5 p-5 bg-gray-200">
          <h2 class="text-xl">${employee.email} - ${employee.department}</h2>
          <p>You have <strong>${employee.remainingHolidays}</strong> holidays remaining.</p>
        </section>
    
        <section class="m-5 p-5 bg-gray-200">
          <c:if test="${holidayRequests.size() == 0}">
            <p class="text-2xl text-gray-500">No holiday requests yet.</p>
          </c:if>
          <c:if test="${holidayRequests.size() > 0}">
            <h1 class="text-xl font-bold mb-5">Your Holiday Requests</h1>
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Title</th>
                  <th>Date Start</th>
                  <th>Date End</th>
                  <th>Status</th>
                  <!-- <th>Actions</th> -->
                </tr>
              </thead>
              <tbody>
                <c:forEach var="hr" items="${holidayRequests}">
                  <tr>
                    <td class="p-2">${hr.id}</td>
                    <td class="p-2">${hr.title}</td>
                    <td class="p-2 datetime">${hr.dateStart}</td>
                    <td class="p-2 datetime">${hr.dateEnd}</td>
                    <td class='capitalize p-2 ${hr.status == "pending" ? "bg-yellow-200" : hr.status == "approved" ? "bg-green-200": "bg-red-200"}'>${hr.status}</td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </c:if>
        </section>
      </section>
    </div>
  </jsp:body>
</t:layout>