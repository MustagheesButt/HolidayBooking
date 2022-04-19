<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Dashboard - Holiday Booking System</title>
  </jsp:attribute>

  <jsp:body>
  <script>
		    const params = new URLSearchParams(window.location.search)
		    if(params.get("error")==="Head"){
		    	Swal.fire({
					  title: 'This Department Already Has a Head',
					  icon: 'error'
				  })
		    }
		    else if(params.get("error")==="Deputy"){
		    	Swal.fire({
					  title: 'This Department Already Has a Deputy Head',
					  icon: 'error'
				  })
		    }
		    else if(params.get("error")==="Email"){
		    	Swal.fire({
					  title: 'Email Already Exists. Please use another Email.',
					  icon: 'error'
				  })
		    }
</script>
    <div class="flex flex-col md:flex-row">
      <jsp:include page="_sidebar.jsp" />
  
      <section class="flex flex-col p-5 w-full">
        <section class="m-2 p-2 place-self-center">
          <h2>Welcome ${admin.email}!</h2>
        </section>
    
        <section class="m-2 p-2 place-self-center">
          <h2>List of all employees</h2>
          <table>
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
                    <a href="/admin/edit-employee?id=${employee.id}">
                     <svg aria-hidden="true" focusable="false" class="h-6 w-6">
                       <use xlink:href="#pencil"></use>
                     </svg>
                  </a> 
                    <form action="/delete-employee" method="post">
                        <input type="hidden" name="id" value="${employee.id}" />
                        <button type="submit">
                   			
                          <svg aria-hidden="true" focusable="false" class="h-6 w-6">
                            <use xlink:href="#trash"></use>            
                      </svg>
                      </button>
                    </form>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </section>
        
        <section class="m-2 p-2 place-self-center">	
        		<a href="/admin/create-employee" class="button p-2 bg-indigo-400 rounded-none hover:rounded-full">Create New Employee</a>       	
        </section>

        <section class="m-5 p-5 bg-gray-200">
          <h2 class="text-xl">Holiday Bookings</h2>
          <p class="mb-5 text-gray-500">These are approved holiday requests.</p>
          <c:if test="${holidayBookings.size() == 0}">
            <p>No approved holiday requests right now</p>
          </c:if>
          <c:if test="${holidayBookings.size() > 0}">
            <input id="filter1" type="text" placeholder="Filter by name/email" />
            <table>
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
                    <td>${hb.title}</td>
                    <td class="filter1-target">${hb.employee.fullName}</td>
                    <td class="filter1-target">${hb.employee.email}</td>
                    <td>${hb.employee.department}</td>
                    <td class="datetime">${hb.dateStart}</td>
                    <td class="datetime">${hb.dateEnd}</td>
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

        <section class="m-5 p-5 bg-gray-200">
          <h2 class="text-xl font-bold mb-5">Search Employees on Leave/Work by Date</h2>
          <input type="date" id="filter2" class="mb-5" />
          <div class="flex">
            <div class="p-10 bg-gray-100 mr-5">
              <h3 class="font-bold">On Duty</h3>
              <table>
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
                      <td>${employee.fullName}</td>
                      <td>${employee.email}</td>
                      <td>${employee.department}</td>
                      <td>${employee.role}</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>

            <div class="p-10 bg-gray-100">
              <h3 class="font-bold">On Leave</h3>
              <table>
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
                      <td>${employee.fullName}</td>
                      <td>${employee.email}</td>
                      <td>${employee.department}</td>
                      <td>${employee.role}</td>
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

                bookings.forEach((booking) => {
                  const startDate = (new Date(new Date(booking.start).toDateString()))
                  const endDate = (new Date(new Date(booking.end).toDateString()))
                  // console.log(startDate)
                  // console.log(endDate)
                  // console.log(selectedDate)
                  if (!(selectedDate >= startDate && selectedDate <= endDate)) {
                    ele.classList.add('hidden')
                  }
                })
              })

              document.querySelectorAll('.filter2-target.onduty').forEach((ele) => {
                const bookings = JSON.parse(ele.getAttribute("data-holiday-bookings"))
                // if (bookings.length === 0) ele.classList.add('hidden')

                bookings.forEach((booking) => {
                  const startDate = (new Date(new Date(booking.start).toDateString()))
                  const endDate = (new Date(new Date(booking.end).toDateString()))
                  // console.log(startDate)
                  // console.log(endDate)
                  // console.log(selectedDate)
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