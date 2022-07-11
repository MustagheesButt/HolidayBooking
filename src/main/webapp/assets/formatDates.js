const dtEles = document.querySelectorAll('.datetime')
dtEles.forEach(e => {
  const date = new Date(e.innerHTML)
  e.innerHTML = date.toLocaleString()
})