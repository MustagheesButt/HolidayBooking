<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<aside class="container py-3">
  <ul class="nav nav-pills">
    <li class="nav-item">
      <a class="nav-link 
                ${requestScope['javax.servlet.forward.request_uri'] == '/dashboard' || requestScope['javax.servlet.forward.request_uri'].contains('request-holidays') ? 'active' : ''}"
         aria-current="page" href="/request-holidays">Request Holidays</a>
    </li>
    <li class="nav-item">
      <a class="nav-link 
                ${requestScope['javax.servlet.forward.request_uri'].contains('manage-requests') ? 'active' : ''}"
         aria-current="page" href="/manage-requests">Manage Requests</a>
    </li>
    <c:if test="${employee.isHeadOfDept()}">
      <li class="nav-item">
        <a class="nav-link 
                  ${requestScope['javax.servlet.forward.request_uri'].contains('manage-department-requests') ? 'active' : ''}"
          aria-current="page" href="/manage-department-requests">Manage Department Requests</a>
      </li>
    </c:if>
  </ul>
</aside>