<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@tag description="Page template" pageEncoding="UTF-8"%>
<%@attribute name="head" fragment="true" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<!DOCTYPE html>
<html>
  <head>
    <jsp:invoke fragment="head" />
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="/assets/bootstrap5.css" rel="stylesheet" />
    <script src="/assets/bootstrap5.js"></script>
    <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css">
  </head>
  <body>
    <nav class="navbar navbar-expand-lg bg-light">
      <div class="container-fluid">
        <a class="navbar-brand" href="/">HolidayBooking</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <c:if test="${sessionScope.admin == null && sessionScope.employee == null}">
            <li class="nav-item">
              <a class="nav-link active" aria-current="page" href="/login">Login</a>
            </li>
            </c:if>
            <c:if test="${sessionScope.admin != null || sessionScope.employee != null}">
            <li class="nav-item">
              <a class="nav-link" href="${sessionScope.admin == null ? "/dashboard" : "/admin"}">Dashboard</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/logout">Logout, ${admin.email}</a>
            </li>
            </c:if>
          </ul>
        </div>
      </div>
    </nav>
    <main>
      <jsp:invoke fragment="header"/>

      <jsp:doBody/>
    </main>

    <footer>
      <script>
        // format dates
        const datetimeElements = document.querySelectorAll('.datetime')
        datetimeElements.forEach(ele => {
          const d = new Date(ele.innerHTML)
          ele.innerHTML = d.toLocaleString()
        })
      </script>
      <jsp:invoke fragment="footer"/>
    </footer>
  </body>
</html>