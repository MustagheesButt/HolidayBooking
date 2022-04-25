<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<aside class="flex flex-col w-full md:w-1/6 bg-blue-200 min-h-screen">
  <a href="/request-holidays" class="p-3 hover:bg-blue-400">Request Holidays</a>
  <a href="/manage-requests" class="p-3 hover:bg-blue-400">Manage Requests</a>
  <c:if test="${employee.isHeadOfDept()}">
    <a href="/manage-department-requests" class="p-3 hover:bg-blue-400">Manage Department Requests</a>
  </c:if>
</aside>