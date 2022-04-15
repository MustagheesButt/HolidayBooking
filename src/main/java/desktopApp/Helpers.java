package desktopApp;

import java.awt.Color;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import holidayBooking.beans.LoginResponse;

public class Helpers {
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
      JOptionPane.showMessageDialog(null, "Could not establish a connection");
      System.out.println(e.getMessage());
      return null;
    }
  }
}
