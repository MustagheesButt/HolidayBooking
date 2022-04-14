package desktopApp;

import java.awt.Color;
import java.awt.event.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import holidayBooking.beans.LoginResponse;

public class Main extends JFrame {
  private static final int W = 560;
  private static final int H = 480;

  public Main() {
  }

  public static void main(String args[]) {
    JFrame jf = new JFrame("Holiday Booking - Straight Walls Ltd");

    JButton closeButton = new JButton("X");
    closeButton.setBounds(W - 60, 0, 60, 40);
    closeButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
      }
    });

    JLabel heading = new JLabel("Employee Client");
    heading.setBounds(W / 2 - W / 6, 20, W / 3, 50);
    heading.setForeground(Color.white);

    JTextField inputEmail = new JTextField("you@example.com");
    inputEmail.setBounds(W / 2 - W / 6, 100, W / 3, 50);
    Helpers.setupInput(inputEmail, "you@example.com");

    JTextField inputPassword = new JTextField("Password");
    inputPassword.setBounds(W / 2 - W / 6, 180, W / 3, 50);
    Helpers.setupInput(inputPassword, "Password");

    JButton loginButton = new JButton("Login");
    loginButton.setBounds(W / 2 - W / 6, 260, W / 3, 40);
    loginButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
        LoginResponse lr = Helpers.sendLoginReq(inputEmail.getText(), inputPassword.getText());
        if (lr.getEmployee() != null) {
          System.out.println(lr.getEmployee().getFullName());
        } else {
          System.out.println("Login failed");
        }
      }
    });

    jf.add(closeButton);
    jf.add(heading);
    jf.add(inputEmail);
    jf.add(inputPassword);
    jf.add(loginButton);

    jf.setUndecorated(true);
    jf.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.5f));

    jf.setSize(W, H);
    jf.setLayout(null);
    jf.setVisible(true);
  }
}

class Helpers {
  public static void setupInput(JTextField input, String placeholder) {
    input.setForeground(Color.GRAY);
    input.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        if (input.getText().equals(placeholder)) {
          input.setText("");
          input.setForeground(Color.BLACK);
        }
      }

      @Override
      public void focusLost(FocusEvent e) {
        if (input.getText().isEmpty()) {
          input.setForeground(Color.GRAY);
          input.setText(placeholder);
        }
      }
    });
  }

  public static LoginResponse sendLoginReq(String email, String password) {
    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8080/api/auth/login"))
          .header("Content-Type", "application/json")
          .POST(BodyPublishers.ofString(String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password)))
          .build();
      LoginResponse lr = client.sendAsync(request, BodyHandlers.ofString())
          .thenApply(HttpResponse::body)
          .thenApply(LoginResponse::parseLoginResponse)
          .join();
      return lr;
    } catch (Exception e) {
      System.out.println("Something went wrong!");
      System.out.println(e.getMessage());
      return null;
    }
  }
}