<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Requests | HolidaysManager</title>
  </jsp:attribute>
  
  <jsp:body>
    <div class="container-fluid text-bg-secondary min-vh-100">
      <jsp:include page="_sidebar.jsp" />
  
      <div class="row">
        <section class="col-11 m-5 p-5 text-bg-dark">
          <h2 class="">${employee.fullName} - ${employee.department}</h2>
          <p>You have <strong>${employee.remainingHolidays}</strong> holidays remaining.</p>
        </section>
    
        <section class="col-6 m-5 p-5 text-bg-dark card rounded">
          <c:if test="${holidayRequests.isEmpty()}">
            <h3 class="text-center">You don't have any requests right now.</h3>
          </c:if>
          <c:if test="${holidayRequests.size() > 0}">
            <h1 class="mb-5">Your Requests</h1>
            <table class="table text-bg-dark">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Title</th>
                  <th>Period</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="hr" items="${holidayRequests}">
                  <tr>
                    <td>${hr.id}</td>
                    <td>${hr.title}</td>
                    <td>
                      <span class="datetime">${hr.dateStart}</span> - <span class="datetime">${hr.dateEnd}</span> (${hr.duration} days)
                    </td>
                    <td class='text-capitalize ${hr.status == "pending" ? "text-warning" : hr.status == "approved" ? "text-success": "text-danger"}'>${hr.status}</td>
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