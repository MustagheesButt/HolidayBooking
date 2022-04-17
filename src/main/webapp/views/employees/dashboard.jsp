<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Dashboard - Holiday Booking System</title>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.2/main.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/fullcalendar@5.10.2/main.css">
  </jsp:attribute>
  
  <jsp:body>
    <div class="flex flex-col md:flex-row bg-gray-400">
      <jsp:include page="_sidebar.jsp" />
  
      <section class="flex flex-col w-full">
        <section class="m-5 p-5 bg-gray-200">
          <h2 class="text-md">${employee.email}</h2>
          <h3 class="text-xl">${employee.fullName} - ${employee.department}</h3>
          <p>You have <strong>${employee.remainingHolidays}</strong> holidays remaining.</p>
        </section>
    
        <section class="m-5 p-5 bg-gray-200">
          <h1>Holiday Calendar</h1>
          <div id="calendar" class="w-1/2"></div>
        </section>
      </section>
    </div>

    <script>
      document.addEventListener('DOMContentLoaded', function() {
        const calendarEl = document.getElementById('calendar');
        const calendar = new FullCalendar.Calendar(calendarEl, {
          initialView: 'dayGridMonth',
          selectable: true,
          // dateClick: function(info) {
          //   console.log(info)
          // },
          select: function(info) {
            const diff = (info.end - info.start) / (24000 * 3600)
            console.log(diff)
            const endStr = (diff === 1) ? '' : (" - " + info.endStr)
            // if (diff > 1)
            //   info.end = new Date(info.end.getTime() + 86399999)
            Swal.fire({
              title: 'Submit Holiday Request?',
              input: 'text',
              inputAttributes: {
                placeholder: 'Title/Reason for holiday'
              },
              html: info.startStr + endStr + ' : ' + diff + ' Days',
              icon: 'question',
              showCancelButton: true
            }).then(function(result) {
              const formData = new FormData()
              formData.append('title', result.value)
              formData.append('dateStart', info.start)
              formData.append('dateEnd', info.end)
              formData.append('duration',diff)
              if (result.isConfirmed) {
            	  if(diff <= ${employee.remainingHolidays}){
                fetch("/create-holiday-request", {
                  method: 'POST',
                  body: formData
                }).then(async function(res) {
                  // console.log(await res.json())
                  Swal.fire({
                    title: 'Holiday request created!',
                    icon: 'success'
                  })
                })
            	  }
            	  else if (diff > ${employee.remainingHolidays}){
            		  Swal.fire({
            			  title: 'Request Exceeds Holidays Limit',
            			  html: 'Remaining Holidays = ' + ${employee.remainingHolidays},
            			  icon: 'error'
            		  })
            	  }
              }
            })
          }
        });
        calendar.render();
      });
    </script>
  </jsp:body>
</t:layout>