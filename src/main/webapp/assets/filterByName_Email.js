const filter1 = document.querySelector('#filter1')

filter1.addEventListener('input', function () {
  document.querySelectorAll('.found').forEach((ele) => ele.classList.remove('found'))

  document.querySelectorAll('.filter1-target').forEach((ele, key, parent) => {
    if (ele.parentElement.parentElement.classList.contains("found")) return

    if (ele.innerHTML.toLowerCase().includes(filter1.value.toLowerCase())) {
      ele.parentElement.parentElement.classList.add("found")
      ele.parentElement.parentElement.classList.remove('d-none')
    } else {
      ele.parentElement.parentElement.classList.add('d-none')
    }
  })
})