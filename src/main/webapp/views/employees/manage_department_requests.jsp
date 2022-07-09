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
            <p class="text-2xl text-gray-500">No pending holiday requests :)</p>
          </c:if>
          <c:if test="${holidayRequests.size() > 0}">
            <h2 class="text-xl font-bold mb-5">Pending Holiday Requests</h2>
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Employee Name</th>
                  <th>Title</th>
                  <th>Date Start</th>
                  <th>Date End</th>
                  <th>Duration</th>
                  <th>Constraint Violations</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="hr" items="${holidayRequests}">
                  <tr class='capitalize ${hr.constraintViolations.isEmpty() ? "" : "bg-red-200"}'>
                    <td class="p-2">${hr.id}</td>
                    <td class="p-2">${hr.employee.fullName}</td>
                    <td class="p-2">${hr.title}</td>
                    <td class="p-2 datetime">${hr.dateStart}</td>
                    <td class="p-2 datetime">${hr.dateEnd}</td>
                    <td class="p-2">${hr.duration}</td>
                    <td class="p-2">${hr.constraintViolations.isEmpty() ? "None" : String.join(", ", hr.constraintViolations)}</td>
                    <td class="flex justify-between p-2">
                      <c:if test="${hr.constraintViolations.size() == 0 }">
                        <form action="/approve-request" method="post">
                          <input type="hidden" name="id" value="${hr.id}" />

                          <button type="submit">
                            <i class="bi bi-check-circle-fill"></i>
                          </button>
                        </form>
                      </c:if>
                      <form action="/reject-request" method="post">
                        <input type="hidden" name="id" value="${hr.id}" />

                        <button type="submit">
                          <i class="bi bi-x-circle-fill link-danger"></i>
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