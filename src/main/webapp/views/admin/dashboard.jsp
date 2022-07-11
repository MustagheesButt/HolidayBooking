<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Dashboard | HolidaysManager</title>
  </jsp:attribute>

  <jsp:body>
  <script>
    const params = new URLSearchParams(window.location.search)
    if (["Head", "Deputy"].includes(params.get("error"))) {
      alert('Department already has a ' + params.get('error'))
    } else if (params.get("error") === "Email") {
      alert('Email already in use')
    }
  </script>

    <div class="container-fluid text-bg-secondary">
      <jsp:include page="_sidebar.jsp" />
  
      <div class="row">
        <section class="card text-bg-dark col-11 m-4 rounded">
          <div class="card-body">
            <a href="/admin/create-employee" class="btn btn-primary">New Employee</a>
          </div>
        </section>
    
        <section class="col-6 m-4 p-5 card text-bg-dark rounded">
          <h2 class="mb-5">All Employees</h2>

          <table class="table text-bg-dark">
            <thead>
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Department</th>
                <th>Role</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="employee" items="${employees}">
                <tr>
                  <td>${employee.fullName}</td>
                  <td>${employee.email}</td>
                  <td>${employee.dept}</td>
                  <td>${employee.role}</td>
                  <td>
                    <div class="d-flex">
                      <a href="/admin/edit-employee?id=${employee.id}" class="btn btn-primary me-3">
                        <i class="bi bi-pencil"></i>
                      </a> 
                      <form action="/delete-employee" method="post">
                          <input type="hidden" name="id" value="${employee.id}" />
                          <button type="submit" class="btn btn-danger">
                            <i class="bi bi-trash3"></i>
                          </button>
                      </form>
                    </div>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </section>

        <section class="col-5 m-4 p-5 card text-bg-dark rounded">
          <h2 class="mb-4">Approved Holidays</h2>

          <c:if test="${holidayBookings.isEmpty()}">
            <p class="text-center">No approved holiday requests right now</p>
          </c:if>
          <c:if test="${holidayBookings.size() > 0}">
            <input id="filter1" type="text" placeholder="Filter by name/email" class="form-control mb-4" />
            <table class="table text-bg-dark">
              <thead>
                <tr>
                  <th>Employee</th>
                  <th>Reason</th>
                  <th>Department</th>
                  <th>Role</th>
                  <th>Period</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="hb" items="${holidayBookings}">
                  <tr>
                    <td><span class="filter1-target">${hb.emp.fullName}</span> (<span class="filter1-target">${hb.emp.email}</span>)</td>
                    <td>${hb.reason}</td>
                    <td>${hb.emp.dept}</td>
                    <td>${hb.emp.role}</td>
                    <td>
                      <span class="datetime">${hb.dateStart}</span> - <span class="datetime">${hb.dateEnd}</span> (${hb.duration} days)
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>

            <script src="/assets/filterByName_Email.js"></script>
          </c:if>
        </section>

        <section class="col-11 m-4 p-5 card text-bg-dark rounded">
          <h2 class="mb-5">Search Employees on Leave/Work by Date</h2>

          <input type="date" id="filter2" class="form-control mb-5" />
          <div class="flex">
            <div class="mr-5">
              <h3 class="">On Duty</h3>

              <table class="table text-bg-dark">
                <thead>
                  <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Department</th>
                    <th>Role</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="employee" items="${employees}">
                    <tr class="filter2-target onduty" data-holiday-bookings='${employee.holidayBookingsSerialized}'>
                      <td>${employee.fullName}</td>
                      <td>${employee.email}</td>
                      <td>${employee.dept}</td>
                      <td>${employee.role}</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>

            <div class="">
              <h3 class="">On Leave</h3>

              <table class="table text-bg-dark">
                <thead>
                  <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Department</th>
                    <th>Role</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="employee" items="${employees}">
                    <tr class="filter2-target onleave"
                      data-holiday-bookings='${employee.holidayBookingsSerialized}'>
                      <td class="p-2">${employee.fullName}</td>
                      <td class="p-2">${employee.email}</td>
                      <td class="p-2">${employee.dept}</td>
                      <td class="p-2">${employee.role}</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>

          <script src="/assets/tableFilter_empOnLeave.js"></script>
        </section>
      </div>
    </div>
  </jsp:body>
</t:layout>