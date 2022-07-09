<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

  <t:layout>
  <jsp:attribute name="head">
    <title>Create New Employee | HolidayBooking</title>
  </jsp:attribute>
  
  <jsp:body>
    <div class="container-fluid text-bg-secondary min-vh-100">
      <jsp:include page="_sidebar.jsp" />

			<div class="row">
				<section class="m-4 p-4 col-6 card text-bg-dark">  
					<form action="/add-employee" method="post" class=">
						<input type="text" name="fname"  Placeholder="First Name" required  class="form-control mb-4">
						<input type="text" name="lname"  Placeholder="last Name" required  class="form-control mb-4">
						<input type="Password" name="password"  Placeholder="Password" required  class="form-control mb-4">
						<input type="email" name="email" required  placeholder="email@google.com" class="form-control mb-4">
	
						<select name="role" required class="form-control mb-4">
							<c:forEach var="role" items="${roles}">
								<option value="${role.id}">${role.title}</option>
							</c:forEach>
						</select>
	
						<select name="department" required   class="form-control mb-4">
							<c:forEach var="department" items="${departments}">
								<option value="${department.id}">${department.title}</option>
							</c:forEach>
						</select>

						<button type="submit" class="btn btn-primary">Create</button>  
					</form>
					
				</section>
			</div>
    </div>
  
   </jsp:body>
</t:layout>