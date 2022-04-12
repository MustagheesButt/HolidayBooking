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
        <!-- <section class="m-5 p-5 bg-gray-200">
      <h2 class="text-xl">${employee.email} - ${employee.department}</h2>
      <p>You have <strong>${employee.remainingHolidays}</strong> holidays remaining.</p>
    </section> -->

        <section class="m-5 p-5 bg-gray-200">
          <c:if test="${holidayRequests.size() == 0}">
            <p class="text-2xl text-gray-500">No holiday requests yet.</p>
          </c:if>
          <c:if test="${holidayRequests.size() > 0}">
            <h1>Your Holiday Requests</h1>
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Employee Name</th>
                  <th>Title</th>
                  <th>Date Start</th>
                  <th>Date End</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="hr" items="${holidayRequests}">
                  <tr>
                    <td>${hr.id}</td>
                    <td>${hr.employee.fullName}</td>
                    <td>${hr.title}</td>
                    <td>${hr.dateStart}</td>
                    <td>${hr.dateEnd}</td>
                    <td
                      class='capitalize ${hr.status == "pending" ? "bg-yellow-200" : hr.status == "approved" ? "bg-green-200": "bg-red-200"}'>
                      ${hr.status}</td>
                    <td class="flex justify-between">
                      <form action="/approve-request" method="post">
                        <input type="hidden" name="id" value="${hr.id}" />

                        <button type="submit">
                          <svg aria-hidden="true" focusable="false" class="h-5 w-5">
                            <use href="#check"></use>
                          </svg>
                        </button>
                      </form>
                      <form action="/reject-request" method="post">
                        <input type="hidden" name="id" value="${hr.id}" />

                        <button type="submit">
                          <svg aria-hidden="true" focusable="false" class="h-5 w-5">
                            <use href="#cross"></use>
                          </svg>
                        </button>
                      </form>
                    </td>
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