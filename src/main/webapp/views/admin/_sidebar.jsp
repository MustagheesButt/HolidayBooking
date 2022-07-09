<aside class="container py-3">
  <ul class="nav nav-pills">
    <li class="nav-item">
      <a class="nav-link 
                ${requestScope['javax.servlet.forward.request_uri'] == '/admin' || requestScope['javax.servlet.forward.request_uri'].contains('manage-employees') ? 'active' : ''}"
         aria-current="page" href="/admin/manage-employees">Employees</a>
    </li>
    <li class="nav-item">
      <a class="nav-link ${requestScope['javax.servlet.forward.request_uri'].contains('manage-requests') ? 'active' : ''}" href="/admin/manage-requests">Holiday Requests</a>
    </li>
    <li class="nav-item">
      <a class="nav-link ${requestScope['javax.servlet.forward.request_uri'].contains('manage-roles') ? 'active' : ''}" href="/admin/manage-roles">Roles</a>
    </li>
    <li class="nav-item">
      <a class="nav-link ${requestScope['javax.servlet.forward.request_uri'].contains('manage-departments') ? 'active' : ''}" href="/admin/manage-departments">Departments</a>
    </li>
  </ul>
</aside>