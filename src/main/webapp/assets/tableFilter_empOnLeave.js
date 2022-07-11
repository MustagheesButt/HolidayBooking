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