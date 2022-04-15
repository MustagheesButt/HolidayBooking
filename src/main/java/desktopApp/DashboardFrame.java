package desktopApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import holidayBooking.models.Employee;

public class DashboardFrame extends JFrame {
  public DashboardFrame(int W, int H, Employee e) {
    this.setTitle("Dashboard");

    JLabel heading1 = new JLabel(String.format("Welcome %s", e.getFullName()));
    heading1.setBounds(W / 2 - W / 6, 20, W / 3, 50);

    JLabel heading2 = new JLabel("Create a Holiday Request");
    heading2.setBounds(W / 2 - W / 6, 90, W / 3, 50);

    UtilDateModel model = new UtilDateModel();
    JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
    JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new AbstractFormatter() {
      private String datePattern = "yyyy-MM-dd";
      private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

      @Override
      public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
      }

      @Override
      public String valueToString(Object value) throws ParseException {
        if (value != null) {
          Calendar cal = (Calendar) value;
          return dateFormatter.format(cal.getTime());
        }

        return "";
      }
    });
    datePicker.setBounds(W / 2 - W / 6, 170, W / 3, 50);

    JTextField inputTitle = new JTextField("Reason for holiday");
    inputTitle.setBounds(W / 2 - W / 6, 250, W / 3, 50);
    Helpers.setupInput(inputTitle, "Reason for holiday");

    JButton submitButton = new JButton("Submit");
    submitButton.setBounds(W / 2 - W / 6, 330, W / 3, 40);
    submitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      }
    });

    this.add(heading1);
    this.add(heading2);
    this.add(datePicker);
    this.add(inputTitle);
    this.add(submitButton);

    this.setSize(W, H);
    this.setLayout(null);
  }
}
