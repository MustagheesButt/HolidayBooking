<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Dashboard - Holiday Booking System</title>
  </jsp:attribute>

  <jsp:body>
    <div class="container-fluid text-bg-secondary min-vh-100">
      <jsp:include page="_sidebar.jsp" />

      <div class="row">
        <section class="col-6 m-5 p-5 text-bg-dark">
          <c:if test="${holidayRequests.size() == 0}">
            <p class="text-2xl text-gray-500">No pending holiday requests :)</p>
          </c:if>
          <c:if test="${holidayRequests.size() > 0}">
            <h2 class="text-xl font-bold mb-5">Pending Holiday Requests</h2>
            <table class="table text-bg-dark">
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

                          <button type="submit" class="btn btn-success">
                            <i class="bi bi-check-circle-fill"></i>
                          </button>
                        </form>
                      </c:if>
                      <form action="/reject-request" method="post">
                        <input type="hidden" name="id" value="${hr.id}" />

                        <button type="submit" class="btn btn-danger">
                          <i class="bi bi-x-circle-fill"></i>
                        </button>
                      </form>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </c:if>
        </section>
      </div>
    </div>
  </jsp:body>
</t:layout>