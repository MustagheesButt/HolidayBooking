package holidayBooking;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

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
import holidayBooking.models.HolidayRequest;
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

		// check requested URL and load appropriate view
		if (uri.contains("manage-departments")) {
			view = AdminBean.getManageDepartments(req, departmentService);
		} else if (uri.contains("manage-roles")) {
			view = AdminBean.getManageRoles(req, roleService);
		} else if (uri.contains("create-employee")) {
			req.setAttribute("roles", roleService.getAll());
			req.setAttribute("departments", departmentService.getAll());

			view = req.getRequestDispatcher("/views/admin/create_employee.jsp");
		} else if (uri.contains("manage-requests")) {
			List<HolidayRequest> pendingRequests = holidayRequestService.getPending();

			// Functionality G - Prioritize by # of holidays already approved, and days
			// requested during peak time
			pendingRequests.sort(new Comparator<HolidayRequest>() {
				@Override
				public int compare(HolidayRequest hr1, HolidayRequest hr2) {
					Long total1 = hr1.getDaysDuringPeakTime() + hr1.getEmployee().getHolidayBookingsDayCount();
					Long total2 = hr2.getDaysDuringPeakTime() + hr2.getEmployee().getHolidayBookingsDayCount();
					return total1.compareTo(total2);
				}
			});

			req.setAttribute("holidayRequests", pendingRequests);
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
			String msg = AdminBean.updateEmployee(req, employeeService, roleService, departmentService);
			if (msg == "Head") {
				redirectTo = "/admin?error=Head";
			} else if (msg == "Deputy") {
				redirectTo = "/admin?error=Deputy";
			} else if (msg == "Email") {
				redirectTo = "/admin?error=Email";
			}
		}

		else if (uri.contains("add-employee")) {
			String msg = AdminBean.addEmployee(req, employeeService, roleService, departmentService);
			if (msg == "Head") {
				redirectTo = "/admin?error=Head";
			} else if (msg == "Deputy") {
				redirectTo = "/admin?error=Deputy";
			} else if (msg == "Email") {
				redirectTo = "/admin?error=Email";
			}
		}

		resp.sendRedirect(redirectTo);
	}
}
