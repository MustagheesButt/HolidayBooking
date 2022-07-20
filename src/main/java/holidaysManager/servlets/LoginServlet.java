package holidaysManager.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import holidaysManager.helpers.LoginHelper;
import holidaysManager.helpers.LogResp;
import holidaysManager.services.AdminService;
import holidaysManager.services.EmpService;

@WebServlet({"", "/login", "/logout"})
public class LoginServlet extends HttpServlet {
  @Inject
  private EmpService employeeService;
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
    else if (session.getAttribute("admin") != null) {
      resp.sendRedirect("/admin");
      return;
    } else if (session.getAttribute("employee") != null) {
      resp.sendRedirect("/dashboard");
      return;
    }

    view.forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String email = req.getParameter("email");
    String password = req.getParameter("password");
    HttpSession session = req.getSession();

    LogResp loginStatus = LoginHelper.login(email, password, employeeService, adminService, session);

    if (loginStatus.getEmployee() == null && loginStatus.getAdmin() == null) {
      resp.sendRedirect("/login?error=email");
    }
    else if (loginStatus.getEmployee() != null) {
      resp.sendRedirect("/dashboard");
    }
    else {
      resp.sendRedirect("/admin");
    }
  }
}
