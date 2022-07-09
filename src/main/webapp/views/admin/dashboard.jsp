<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Dashboard - Holiday Booking System</title>
  </jsp:attribute>

  <jsp:body>
  <script>
		    const params = new URLSearchParams(window.location.search)
		    if(["Head", "Deputy"].includes(params.get("error"))) {
		    	Swal.fire({
					  title: 'This Department Already Has a ' + params.get('error'),
					  icon: 'error'
				  })
		    }
		    else if(params.get("error") === "Email"){
		    	Swal.fire({
					  title: 'Email Already Exists. Please use another Email.',
					  icon: 'error'
				  })
		    }
  </script>

    <div class="container-fluid text-bg-secondary">
      <jsp:include page="_sidebar.jsp" />
  
      <section class="row">
        <section class="card text-bg-dark col-8 m-4">
          <div class="card-body">
            <a href="/admin/create-employee" class="btn btn-primary">New Employee</a>
          </div>
        </section>
    
        <section class="col-11 m-4 p-5 card text-bg-dark">
          <h2>All Employees</h2>
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
                  <td class="flex justify-between">
                    <a href="/admin/edit-employee?id=${employee.id}" class="btn btn-primary">
                      <i class="bi bi-pencil"></i>
                    </a> 
                    <form action="/delete-employee" method="post">
                        <input type="hidden" name="id" value="${employee.id}" />
                        <button type="submit" class="btn btn-danger">
                          <i class="bi bi-trash3"></i>
                        </button>
                    </form>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </section>

        <section class="col-6 m-4 p-5 card text-bg-dark">
          <h2 class="text-xl">Holiday Bookings</h2>
          <p class="mb-5 text-gray-500">These are approved holiday requests.</p>
          <c:if test="${holidayBookings.size() == 0}">
            <p>No approved holiday requests right now</p>
          </c:if>
          <c:if test="${holidayBookings.size() > 0}">
            <input id="filter1" type="text" placeholder="Filter by name/email" />
            <table class="table text-bg-dark">
              <thead>
                <tr>
                  <th>Reason for Holiday</th>
                  <th>Employee Name</th>
                  <th>Employee Email</th>
                  <th>Department</th>
                  <th>Date Start</th>
                  <th>Date End</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="hb" items="${holidayBookings}">
                  <tr>
                    <td class="p-2">${hb.title}</td>
                    <td class="p-2 filter1-target">${hb.employee.fullName}</td>
                    <td class="p-2 filter1-target">${hb.employee.email}</td>
                    <td class="p-2">${hb.employee.department}</td>
                    <td class="p-2 datetime">${hb.dateStart}</td>
                    <td class="p-2 datetime">${hb.dateEnd}</td>
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
                    ele.parentElement.classList.remove('hidden')
                  } else {
                    ele.parentElement.classList.add('hidden')
                  }
                })
              })
            </script>
          </c:if>
        </section>

        <section class="col-5 m-4 p-5 card text-bg-dark">
          <h2 class="text-xl font-bold mb-5">Search Employees on Leave/Work by Date</h2>
          <input type="date" id="filter2" class="mb-5" />
          <div class="flex">
            <div class="p-10 bg-gray-100 mr-5">
              <h3 class="font-bold">On Duty</h3>
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
                    <tr class="filter2-target onduty" data-holiday-bookings='${employee.holidayBookingsSerialized}'>
                      <td class="p-2">${employee.fullName}</td>
                      <td class="p-2">${employee.email}</td>
                      <td class="p-2">${employee.department}</td>
                      <td class="p-2">${employee.role}</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>

            <div class="p-10 bg-gray-100">
              <h3 class="font-bold">On Leave</h3>
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

            filter2.addEventListener('input', function () {
              const selectedDate = new Date(filter2.value)

              document.querySelectorAll('.filter2-target').forEach((ele) => {
                ele.classList.remove('hidden')
              })

              document.querySelectorAll('.filter2-target.onleave').forEach((ele) => {
                const bookings = JSON.parse(ele.getAttribute("data-holiday-bookings"))
                if (bookings.length === 0) ele.classList.add('hidden')

                let toHide = true
                bookings.forEach((booking) => {
                  const startDate = (new Date(new Date(booking.start).toDateString()))
                  const endDate = (new Date(new Date(booking.end).toDateString()))

                  if ((selectedDate >= startDate && selectedDate <= endDate)) {
                    toHide = false
                  }
                })

                if (toHide) ele.classList.add('hidden')
              })

              document.querySelectorAll('.filter2-target.onduty').forEach((ele) => {
                const bookings = JSON.parse(ele.getAttribute("data-holiday-bookings"))
                // if (bookings.length === 0) ele.classList.add('hidden')

                bookings.forEach((booking) => {
                  const startDate = (new Date(new Date(booking.start).toDateString()))
                  const endDate = (new Date(new Date(booking.end).toDateString()))

                  if ((selectedDate >= startDate && selectedDate <= endDate)) {
                    ele.classList.add('hidden')
                  }
                })
              })
            })
          </script>
        </section>
      </section>
    </div>
  </jsp:body>
</t:layout>