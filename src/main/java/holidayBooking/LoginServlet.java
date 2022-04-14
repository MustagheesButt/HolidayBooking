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

import holidayBooking.beans.LoginBean;
import holidayBooking.beans.LoginResponse;
import holidayBooking.services.AdminService;
import holidayBooking.services.EmployeeService;

@WebServlet({"", "/login", "/logout"})
public class LoginServlet extends HttpServlet {
  @Inject
  private EmployeeService employeeService;
  @Inject
  private AdminService adminService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    RequestDispatcher view = req.getRequestDispatcher("login.jsp");
    String uri = req.getRequestURI();
    HttpSession session = req.getSession();

    if (uri.contains("logout")) {
      session.invalidate();
    }

    view.forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String email = req.getParameter("email");
    String password = req.getParameter("password");
    HttpSession session = req.getSession();
    // session.invalidate(); // clear session

    LoginResponse loginStatus = LoginBean.login(email, password, employeeService, adminService, session);

    if (loginStatus.getEmployee() == null && loginStatus.getAdmin() == null) {
      resp.sendRedirect("/login?error=email");
    } else if (loginStatus.getEmployee() != null) {
      resp.sendRedirect("/dashboard");
    }
    
    resp.sendRedirect("/admin");
  }
}
