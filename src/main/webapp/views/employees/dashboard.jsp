<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Dashboard | HolidaysManager</title>
  </jsp:attribute>
  
  <jsp:body>
    <div class="container-fluid text-bg-secondary">
      <jsp:include page="_sidebar.jsp" />
  
      <div class="row">
        <section class="col-11 m-5 p-5 card text-bg-dark rounded">
          <h3 class="">${employee.fullName} - ${employee.department}</h3>
          <p>You have <strong>${employee.remainingHolidays}</strong> holidays remaining.</p>
        </section>
    
        <section class="col-5 m-5 p-5 card text-bg-dark rounded">
          <h2 class="">Request a New Holiday</h2>
          <div>
            <form method="post" action="/create-holiday-request" id="hrForm">
              <div class="mb-3">
                <label>Start Date</label>
                <input name="dateStart" class="form-control" type="datetime-local" required />
              </div>

              <div class="mb-3">
                <label>End Date</label>
                <input name="dateEnd" class="form-control" type="datetime-local" required />
              </div>

              <div class="mb-3">
                <label>Reason</label>
                <input name="title" class="form-control" type="text" required />
              </div>

              <button class="btn btn-primary" type="submit">Request</button>
            </form>
          </div>
        </section>
      </section>
    </div>

    <script>
      const remainingHolidays = ${employee.remainingHolidays}
      const startEle = document.querySelector('input[name=dateStart]')
      const endEle = document.querySelector('input[name=dateEnd]')
      const form = document.querySelector('#hrForm')
      const ONE_DAY = 24000 * 3600

      startEle.value = (new Date()).toISOString().slice(0,16)
      endEle.value = new Date(Date.now() + ONE_DAY).toISOString().slice(0,16)

      form.onsubmit = (e) => {
        const diff = Math.round(
          (new Date(endEle.value) - new Date(startEle.value)) / ONE_DAY
        )

        if (diff > remainingHolidays) {
          e.preventDefault()
          Swal.fire({
            title: 'Too Many Holidays',
            html: `You requested ${diff} holidays, when you only have ${remainingHolidays} remaining holidays`,
            icon: 'error'
          })
        }
      }
    </script>
  </jsp:body>
</t:layout>