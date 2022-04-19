<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<aside class="flex flex-col w-full md:w-1/6 bg-blue-200 p-5 min-h-screen">
  <a href="/request-holidays" class="py-1">Request Holidays</a>
  <a href="/manage-requests" class="py-1">Manage Requests</a>
  <c:if test="${employee.isHeadOfDept()}">
    <a href="/manage-department-requests" class="py-1">Manage Department Requests</a>
  </c:if>
</aside>