package desktopApp;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Main extends JFrame {
  private static final int W = 560;
  private static final int H = 480;

  public Main() {
  }

  public static void main(String args[]) {
    JFrame jf = new JFrame("Holiday Booking - Straight Walls Ltd");

    JLabel heading = new JLabel("Employee Client");
    heading.setBounds(W/2 - W/6, 20, W/3, 50);
    heading.setForeground(Color.white);

    JTextField inputEmail = new JTextField("you@example.com");
    inputEmail.setBounds(W / 2 - W / 6, 100, W / 3, 50);
    Helpers.setupInput(inputEmail, "you@example.com");

    JTextField inputPassword = new JTextField("Password");
    inputPassword.setBounds(W / 2 - W / 6, 180, W / 3, 50);
    Helpers.setupInput(inputPassword, "Password");

    JButton jb = new JButton("X");
    jb.setBounds(W - 60, 0, 60, 40);
    jb.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
      }
    });

    jf.add(heading);
    jf.add(jb);
    jf.add(inputEmail);
    jf.add(inputPassword);

    jf.setUndecorated(true);
    jf.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.5f));

    jf.setSize(560, 480);
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
}
