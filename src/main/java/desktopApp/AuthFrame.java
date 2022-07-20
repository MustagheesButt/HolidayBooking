package desktopApp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import holidaysManager.entities.Emp;
import holidaysManager.helpers.LogResp;

public class AuthFrame extends CustomFrame {
  public AuthFrame() {
    this.setTitle("HolidaysManager");

    JLabel h1 = new JLabel("Login to HolidaysManager");
    h1.setBounds(width / 2 - 100, 20, width / 3, 40);
    h1.setForeground(Color.WHITE);

    JTextField emailField = new JTextField("Email");
    emailField.setBounds(width / 2 - 105, 100, width / 3, 30);
    Helpers.setupInput(emailField, "Email");

    JTextField passwdField = new JTextField("Password");
    passwdField.setBounds(width / 2 - 105, 180, width / 3, 30);
    Helpers.setupInput(passwdField, "Password");

    JButton lBtn = new JButton("Login");
    lBtn.setBounds(width / 2 - 105, 250, width / 3, 40);
    lBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        LogResp lr = Helpers.sendLoginReq(emailField.getText(), passwdField.getText());
        if (lr == null) {
          JOptionPane.showMessageDialog(null, "Could not establish a connection");
          return;
        }

        if (lr.getEmployee() != null) {
          loadMain(lr.getEmployee());
        } else {
          JOptionPane.showMessageDialog(null, "Invalid email/password");
        }
      }
    });

    this.add(h1);
    this.add(emailField);
    this.add(passwdField);
    this.add(lBtn);
  }

  private void loadMain(Emp e) {
    this.dispose();
    MainFrame df = new MainFrame(e);
    df.setVisible(true);
  }
}
