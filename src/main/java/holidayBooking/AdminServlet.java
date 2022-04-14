package holidayBooking;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import holidayBooking.beans.AdminBean;
import holidayBooking.models.Admin;
import holidayBooking.services.DepartmentService;
import holidayBooking.services.EmployeeService;
import holidayBooking.services.HolidayRequestService;
import holidayBooking.services.RoleService;

@Transactional
@WebServlet({ "/admin", "/admin/manage-employees", "/delete-employee", "/add-employee", "/admin/create-employee",
		"/edit-employee", "/update-employee", "/admin/manage-roles", "/admin/manage-departments", "/admin/manage-requests",
		"/admin/edit-employee" })
public class AdminServlet extends HttpServlet {
	@Inject
	private EmployeeService employeeService;
	@Inject
	private DepartmentService departmentService;
	@Inject
	private RoleService roleService;
	@Inject
	private HolidayRequestService holidayRequestService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher view = req.getRequestDispatcher("/views/admin/dashboard.jsp");
		HttpSession session = req.getSession();
		String uri = req.getRequestURI();

		if (session.getAttribute("admin") == null) {
			resp.sendRedirect("/login");
			return;
		}

		Admin admin = (Admin) session.getAttribute("admin");
		req.setAttribute("admin", admin);

		if (uri.contains("manage-departments")) {
			view = AdminBean.getManageDepartments(req, departmentService);
		} else if (uri.contains("create-employee")) {
			req.setAttribute("roles", roleService.getAll());
			req.setAttribute("departments", departmentService.getAll());
			view = req.getRequestDispatcher("/views/admin/create_employee.jsp");
		} else if (uri.contains("manage-requests")) {
			req.setAttribute("holidayRequests", holidayRequestService.getPending());
			view = req.getRequestDispatcher("/views/admin/manage_requests.jsp");
		} else if (uri.contains("edit-employee")) {
			req.setAttribute("employee", employeeService.find(Long.parseLong(req.getParameter("id"))));
			req.setAttribute("roles", roleService.getAll());
			req.setAttribute("departments", departmentService.getAll());
			view = req.getRequestDispatcher("/views/admin/edit_employee.jsp");
		} else {
			req.setAttribute("employees", employeeService.getAll());
			req.setAttribute("holidayBookings", holidayRequestService.getApproved());
		}

		view.forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String uri = req.getRequestURI();

		if (session.getAttribute("admin") == null) {
			resp.sendRedirect("/login");
			return;
		}

		String redirectTo = "/admin";
		if (uri.contains("delete-employee")) {
			AdminBean.deleteEmployee(req, employeeService);
		} else if (uri.contains("update-employee")) {
			AdminBean.updateEmployee(req, employeeService, roleService, departmentService);
		} else if (uri.contains("add-employee")) {
			AdminBean.addEmployee(req, employeeService, roleService, departmentService);
		}

		resp.sendRedirect(redirectTo);
	}
}
