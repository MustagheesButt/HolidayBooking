package desktopApp;

import javax.swing.JFrame;
import java.awt.Color;

public class CustomFrame extends JFrame {
  protected static final int width = 640;
  private static final int height = 480;

  public CustomFrame() {
    this.setSize(width, height);
    this.setLayout(null);
    this.getContentPane().setBackground(Color.BLACK);
    this.getContentPane().setForeground(Color.WHITE);
  }
}
