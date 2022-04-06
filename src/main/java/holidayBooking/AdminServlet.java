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

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
  @Inject
  private UserService userService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    RequestDispatcher view = req.getRequestDispatcher("admin_dashboard.jsp");
    HttpSession session = req.getSession();

    if (session.getAttribute("email") == null) {
      resp.sendRedirect("/login");
      return;
    }
  
    User u = userService.getAllUsers().get(0);
    req.setAttribute("user", u);

    view.forward(req, resp);
  }
}
