<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Department Requests - HolidaysManager</title>
  </jsp:attribute>

  <jsp:body>
    <div class="container-fluid text-bg-secondary min-vh-100">
      <jsp:include page="_sidebar.jsp" />

      <div class="row">
        <section class="col-11 m-5 p-5 card text-bg-dark rounded">
          <c:if test="${holidayRequests.isEmpty()}">
            <h3 class="text-center">No pending holiday requests</h3>
          </c:if>
          <c:if test="${holidayRequests.size() > 0}">
            <h2 class="text-xl font-bold mb-5">Pending Holiday Requests</h2>
            <table class="table text-bg-dark">
              <thead>
                <tr>
                  <th>Employee Name</th>
                  <th>Title</th>
                  <th>Period</th>
                  <th>Violations</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="hr" items="${holidayRequests}">
                  <tr class='text-capitalize ${hr.violations.isEmpty() ? "" : "bg-danger"}'>
                    <td>${hr.emp.fullName}</td>
                    <td>${hr.title}</td>
                    <td>
                      <span class="datetime">${hr.dateStart}</span> - <span class="datetime">${hr.dateEnd}</span> (${hr.duration} days)
                    </td>
                    <td>${hr.violations.isEmpty() ? "None" : String.join(", ", hr.violations)}</td>
                    <td>
                      <div class="d-flex">
                        <c:if test="${hr.violations.isEmpty()}">
                          <form action="/approve-request" method="post">
                            <input type="hidden" name="id" value="${hr.id}" />
  
                            <button type="submit" class="btn btn-success me-3">
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
                      </div>
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