<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
  <jsp:attribute name="head">
    <title>Login - Holiday Booking System</title>
  </jsp:attribute>

  <jsp:body>
    <div class="h-screen bg-blue-200">
      <section class="h-full flex flex-col justify-center items-center">
        <h2 class="text-3xl">Straight Walls Ltd</h2>
        <form class="mt-10" action="/login" method="POST">
          <div class="mb-4">
            <input class="shadow appearance-none border w-full py-2 px-3" name="email" type="email"
              placeholder="example@gmail.com" />
          </div>
  
          <div class="mb-4">
            <input class="shadow appearance-none border w-full py-2 px-3" name="password" type="password"
              placeholder="Password" />
          </div>
  
          <div class="mb-4 text-center">
            <button type="submit" class="px-4 py-1 bg-white hover:bg-blue-400">Login</button>
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