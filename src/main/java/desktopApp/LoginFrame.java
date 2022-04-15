package desktopApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import holidayBooking.beans.LoginResponse;
import holidayBooking.models.Employee;

public class LoginFrame extends JFrame {
  public LoginFrame(int W, int H) {
    // JButton closeButton = new JButton("X");
    // closeButton.setBounds(W - 60, 0, 60, 40);
    // closeButton.addActionListener(new ActionListener() {
    // public void actionPerformed(ActionEvent e) {
    // jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
    // }
    // });
    this.setTitle("Holiday Booking - Straight Walls Ltd");

    JLabel heading = new JLabel("Employee Client");
    heading.setBounds(W / 2 - W / 6, 20, W / 3, 50);
    // heading.setForeground(Color.white);

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
        LoginResponse lr = Helpers.sendLoginReq(inputEmail.getText(), inputPassword.getText());
        if (lr != null && lr.getEmployee() != null) {
          System.out.println(lr.getEmployee().getFullName());
          goToDashboardFrame(W, H, lr.getEmployee());
        } else {
          JOptionPane.showMessageDialog(null, "Invalid email/password");
        }
      }
    });

    // jf.add(closeButton); // for undecorated frame
    this.add(heading);
    this.add(inputEmail);
    this.add(inputPassword);
    this.add(loginButton);

    // jf.setUndecorated(true);
    // jf.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.5f));

    this.setSize(W, H);
    this.setLayout(null);
  }

  private void goToDashboardFrame(int W, int H, Employee e) {
    this.dispose();
    DashboardFrame df = new DashboardFrame(W, H, e);
    df.setVisible(true);
  }
}
