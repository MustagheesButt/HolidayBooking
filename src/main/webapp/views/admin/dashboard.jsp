<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Dashboard - HolidaysManager</title>
  </jsp:attribute>

  <jsp:body>
  <script>
    const params = new URLSearchParams(window.location.search)
    if (["Head", "Deputy"].includes(params.get("error"))) {
      Swal.fire({
        title: 'This Department Already Has a ' + params.get('error'),
        icon: 'error'
      })
    } else if (params.get("error") === "Email") {
      Swal.fire({
        title: 'Email Already Exists. Please use another Email.',
        icon: 'error'
      })
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
                <th>ID</th>
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
                  <td>${employee.id}</td>
                  <td>${employee.fullName}</td>
                  <td>${employee.email}</td>
                  <td>${employee.department}</td>
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
                  <th>Reason</th>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Department</th>
                  <th>Period</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="hb" items="${holidayBookings}">
                  <tr>
                    <td>${hb.title}</td>
                    <td class="filter1-target">${hb.employee.fullName}</td>
                    <td class="filter1-target">${hb.employee.email}</td>
                    <td>${hb.employee.department}</td>
                    <td>
                      <span class="datetime">${hb.dateStart}</span> - <span class="datetime">${hb.dateEnd}</span> (${hb.duration} days)
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>

            <script>
              const filter1 = document.querySelector('#filter1')
  
              filter1.addEventListener('input', function () {
                document.querySelectorAll('.found').forEach((ele) => ele.classList.remove('found'))
  
                document.querySelectorAll('.filter1-target').forEach((ele, key, parent) => {
                  if (ele.parentElement.classList.contains("found")) return
  
                  if (ele.innerHTML.toLowerCase().includes(filter1.value.toLowerCase())) {
                    ele.parentElement.classList.add("found")
                    ele.parentElement.classList.remove('d-none')
                  } else {
                    ele.parentElement.classList.add('d-none')
                  }
                })
              })
            </script>
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
                      <td>${employee.department}</td>
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
                    <th>Employee Name</th>
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
                      <td class="p-2">${employee.department}</td>
                      <td class="p-2">${employee.role}</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>

          <script>
            const filter2 = document.querySelector('#filter2')

            magic()
            filter2.addEventListener('input', magic)

            function magic() {
              const selectedDate = new Date(filter2.value)

              document.querySelectorAll('.filter2-target').forEach((ele) => {
                ele.classList.remove('d-none')
              })

              document.querySelectorAll('.filter2-target.onleave').forEach((ele) => {
                const bookings = JSON.parse(ele.getAttribute("data-holiday-bookings"))
                if (bookings.length === 0) ele.classList.add('d-none')

                let toHide = true
                bookings.forEach((booking) => {
                  const startDate = (new Date(new Date(booking.start).toDateString()))
                  const endDate = (new Date(new Date(booking.end).toDateString()))

                  if ((selectedDate >= startDate && selectedDate <= endDate)) {
                    toHide = false
                  }
                })

                if (toHide) ele.classList.add('d-none')
              })

              document.querySelectorAll('.filter2-target.onduty').forEach((ele) => {
                const bookings = JSON.parse(ele.getAttribute("data-holiday-bookings"))

                bookings.forEach((booking) => {
                  const startDate = (new Date(new Date(booking.start).toDateString()))
                  const endDate = (new Date(new Date(booking.end).toDateString()))

                  if ((selectedDate >= startDate && selectedDate <= endDate)) {
                    ele.classList.add('d-none')
                  }
                })
              })
            }
          </script>
        </section>
      </div>
    </div>
  </jsp:body>
</t:layout>