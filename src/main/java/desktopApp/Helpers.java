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
import java.util.List;

import javax.swing.JTextField;

import holidaysManager.entities.Emp;
import holidaysManager.entities.HRequest;
import holidaysManager.helpers.HReqHelper;
import holidaysManager.helpers.LogResp;

public class Helpers {
  private static String apiBaseUrl = "http://localhost:8080";

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

  public static LogResp sendLoginReq(String email, String password) {
    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder(new URI(apiBaseUrl + "/api/auth/login"))
          .header("Content-Type", "application/json")
          .POST(BodyPublishers.ofString(String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password)))
          .build();
      LogResp lr = client.sendAsync(request, BodyHandlers.ofString())
          .thenApply(HttpResponse::body)
          .thenApply(LogResp::pLogResp)
          .join();
      return lr;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  public static List<HRequest> getApprovedHolidayRequests(Emp employee) {
    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder(new URI(apiBaseUrl + "/api/holiday-requests/approved/" + employee.getId()))
          .header("Content-Type", "application/json")
          .build();
      List<HRequest> lr = client.sendAsync(request, BodyHandlers.ofString())
          .thenApply(HttpResponse::body)
          .thenApply(HReqHelper::hReqsFromJson)
          .join();
      return lr;
    } catch (Exception e) {
      return null;
    }
  }

  public static HRequest createHolidayRequest(HRequest req, Emp employee) {
    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder(new URI(apiBaseUrl + "/api/holiday-requests/create/" + employee.getId()))
          .header("Content-Type", "application/json")
          .POST(BodyPublishers.ofString(String.format("{\"reason\": \"%s\", \"dateStart\": \"%s\", \"dateEnd\": \"%s\"}", req.getReason(), req.getDateStart(), req.getDateEnd())))
          .build();
      HRequest lr = client.sendAsync(request, BodyHandlers.ofString())
          .thenApply(HttpResponse::body)
          .thenApply(HReqHelper::hReqFromJson)
          .join();
      return lr;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }
}
