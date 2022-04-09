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
        <section class="m-5 p-5">
          <h2 class="text-xl">${employee.email} - ${employee.department}</h2>
          <p>You have <strong>${employee.remainingHolidays}</strong> holidays remaining.</p>
        </section>
    
        <section>
          <h1>Your Holiday Requests</h1>
          <c:if test="${holidayRequest.size() == 0}">
            <p class="text-gray-300">No holiday requests yet.</p>
          </c:if>
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
                  <td>${hr.id}</td>
                  <td>${hr.title}</td>
                  <td>${hr.dateStart}</td>
                  <td>${hr.dateEnd}</td>
                  <td>${hr.status}</td>
                  <!-- <td class="flex justify-between">
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