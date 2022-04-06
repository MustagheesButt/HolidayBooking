package holidayBooking;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet({"", "/login"})
public class LoginServlet extends HttpServlet {
  @Inject
  private UserService userService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    RequestDispatcher view = req.getRequestDispatcher("login.jsp");
    view.forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String email = req.getParameter("email");
    String password = req.getParameter("password");
    HttpSession session = req.getSession();
    // session.invalidate(); // clear session

    List<User> users = userService.getAllUsers();
    User user = null;
    for (User u : users) {
      if (u.getEmail().equals(email)) {
        user = u;
      }
    }

    if (user == null || !user.getPassword().equals(password)) {
      resp.sendRedirect("/login?error=email");
      return;
    }

    // set session
    session.setAttribute("email", email);

    resp.sendRedirect("/admin");
  }
}
