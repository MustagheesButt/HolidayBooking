<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

  <t:layout>
  <jsp:attribute name="head">
    <title>Editing Employee | HolidaysManager</title>
  </jsp:attribute>
  
  <jsp:body>
    <div class="container-fluid text-bg-secondary min-vh-100">
      <jsp:include page="_sidebar.jsp" />
      
			<div class="row">
				<section class="m-4 p-4 col-6 text-bg-dark">
					<form action="/update-employee" method="post" class="flex flex-col mt-2 pt-2">
						<input type="hidden" name="id" value="${employee.id}" class="">
						<input type="text" name="fname" value="${employee.firstName}" placeholder="First name" class="form-control mb-4">
						<input type="text" name="lname" value="${employee.lastName}" placeholder="Last name" class="form-control mb-4">
						<input type="password" name="password" value="${employee.password}" placeholder="" class="form-control mb-4">
						<input type="email" name="email" value="${employee.email}" placeholder="email@gmail.com" class="form-control mb-4">
						<select name="role"  class="form-control mb-4">
							<c:forEach var="role" items="${roles}">
								<option value="${role.id}" ${employee.role.equals(role) ? "selected=true" : "" }>${role.title}</option>
							</c:forEach>
						</select>

						<select name="department"  class="form-control mb-4">
							<c:forEach var="department" items="${departments}">
								<option value="${department.id}" ${employee.department.equals(department) ? "selected=true" : "" }>${department.title}</option>
							</c:forEach>
						</select>

						<button type="submit" class="btn btn-primary">Update</button>
					</form>
				</section>
			</div>
    </div>
   
  </jsp:body>
</t:layout>