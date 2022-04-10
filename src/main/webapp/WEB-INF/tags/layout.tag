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
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <t:svg-icons></t:svg-icons>
  </head>
  <body>
    <nav class="justify-center flex space-x-4 bg-purple-200 py-4">
      <a href="/" class="px-3 text-slate-700 font-medium">Home</a>
      <c:if test="${sessionScope.admin == null && sessionScope.employee == null}">
        <a href="/login" class="px-3 text-slate-700 font-medium">Login</a>
      </c:if>
      <c:if test="${sessionScope.admin != null || sessionScope.employee != null}">
        <a href='/${sessionScope.admin == null ? "dashboard" : "admin"}' class="px-3 text-slate-700 font-medium">Dashboard</a>
        <a href="/profile" class="px-3 text-slate-700 font-medium">Profile</a>
        <a href="/logout" class="px-3 text-slate-700 font-medium">Logout</a>
      </c:if>
      <a href="/about" class="px-3 text-slate-700 font-medium">About</a>
    </nav>
    <main>
      <c:if test="${param.error.length() > 0}">
        <div class="p-3 bg-red-300 text-red-800 text-center">
          ${App.getMappedError(param.error)}
        </div>
      </c:if>
      <c:if test="${success_message.length() > 0}">
        <div class="p-3 bg-green-200 text-green-800 text-center">
          ${success_message}
        </div>
      </c:if>

      <jsp:invoke fragment="header"/>

      <jsp:doBody/>
    </main>

    <footer>
      <jsp:invoke fragment="footer"/>
    </footer>
  </body>
</html>