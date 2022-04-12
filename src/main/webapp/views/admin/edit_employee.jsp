<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

  <t:layout>
  <jsp:attribute name="head">
    <title>Edit Employee Data - Holiday Booking System</title>
  </jsp:attribute>
  
  <jsp:body>
    <div class="flex flex-col md:flex-row">
      <jsp:include page="_sidebar.jsp" />
      
      <section class="m-2 p-2 place-self-center">  
	 		<form action="/update-employee" method="post" class="flex flex-col mt-2 pt-2">
		  		<input type="hidden" name="id" value="${employee.id}" Placeholder="Employee ID" class="shadow appearance-none border w-full py-2 px-3">
		  		<input type="text" name="fname" value="${employee.firstName}" Placeholder="First Name" class="shadow appearance-none border w-full py-2 px-3">
		  		<input type="text" name="lname" value="${employee.lastName}" Placeholder="last Name" class="shadow appearance-none border w-full py-2 px-3">
		  		<input type="Password" name="password" value="${employee.password}" Placeholder="" class="shadow appearance-none border w-full py-2 px-3">
		  		<input type="email" name="email" value="${employee.email}" Placeholder="xyz@google.com" class="shadow appearance-none border w-full py-2 px-3">
		  		<select name="role"  class="block appearance-none w-full bg-gray-100 border border-gray-200 text-gray-700 py-3 px-4 pr-8 rounded leading-tight focus:outline-none focus:bg-white focus:border-gray-500">
		                <c:forEach var="role" items="${roles}">
		                  <option value="${role.id}" ${employee.role.equals(role) ? "selected=true" : "" }>${role.title}</option>
		                </c:forEach>
		        </select>
		        <select name="department"  class="block appearance-none w-full bg-gray-100 border border-gray-200 text-gray-700 py-3 px-4 pr-8 rounded leading-tight focus:outline-none focus:bg-white focus:border-gray-500">
		                <c:forEach var="department" items="${departments}">
		                  <option value="${department.id}" ${employee.department.equals(department) ? "selected=true" : "" }>${department.title}</option>
		                </c:forEach>
		        </select>
		  		<input type="submit" value="Update Employee" class="shadow appearance-none border w-full py-2 px-3">  
		  	</form>
  		</section>
    </div>
  </jsp:body>
</t:layout>