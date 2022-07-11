const f1 = document.querySelector('#f1')

f1.addEventListener('input', function () {
  document.querySelectorAll('.found').forEach((ele) => ele.classList.remove('found'))

  document.querySelectorAll('.f1t').forEach((ele, key, parent) => {
    if (ele.parentElement.parentElement.classList.contains("found")) return

    if (ele.innerHTML.toLowerCase().includes(f1.value.toLowerCase())) {
      ele.parentElement.parentElement.classList.add("found")
      ele.parentElement.parentElement.classList.remove('d-none')
    } else {
      ele.parentElement.parentElement.classList.add('d-none')
    }
  })
})