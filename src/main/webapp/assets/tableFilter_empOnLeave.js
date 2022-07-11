const f2 = document.querySelector('#f2')

magic()
f2.addEventListener('input', magic)

function magic() {
  const selectedDate = new Date(f2.value)

  document.querySelectorAll('.f2t').forEach((ele) => {
    ele.classList.remove('d-none')
  })

  document.querySelectorAll('.f2t.onleave').forEach((ele) => {
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

  document.querySelectorAll('.f2t.onduty').forEach((ele) => {
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