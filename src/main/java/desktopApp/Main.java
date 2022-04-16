package desktopApp;

import javax.swing.JFrame;

public class Main extends JFrame {
  private static final int W = 560;
  private static final int H = 480;

  public Main() {
    // JFrame jf = new JFrame("Holiday Booking - Straight Walls Ltd");
  }

  public static void main(String args[]) {
    JFrame lf = new LoginFrame(W, H);
    lf.setVisible(true);
  }
}