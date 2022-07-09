<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Login | HolidayBooking</title>
  </jsp:attribute>

  <jsp:body>
    <div class="vh-100 text-bg-secondary row"
         style="background-image: url(https://unsplash.com/photos/e6n7uoEnYbA/download?ixid=MnwxMjA3fDB8MXxhbGx8fHx8fHx8fHwxNjU3MzU5MjQ3&force=true&w=1920); background-size: cover;">
      <section class="text-bg-dark bg-opacity-75 p-5 col-3 m-auto text-center rounded">
        <h2 class="mb-4">Login</h2>
        <form class="" action="/login" method="POST">
          <div class="mb-4">
            <input class="form-control" name="email" type="email"
              placeholder="example@gmail.com" />
          </div>
  
          <div class="mb-4">
            <input class="form-control" name="password" type="password"
              placeholder="Password" />
          </div>
  
          <div class="mb-4 text-center">
            <button type="submit" class="px-4 py-1 btn btn-primary">Login</button>
          </div>
        </form>
      </section>
    </div>

    <script>
      const params = new URLSearchParams(window.location.search)
      if (params.get("error")) {
        alert("Wrong email or password!")
      }
    </script>
  </jsp:body>
</t:layout>