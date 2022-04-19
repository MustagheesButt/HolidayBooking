package desktopApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import holidayBooking.models.Employee;
import holidayBooking.models.HolidayRequest;

public class DashboardFrame extends JFrame {
  public DashboardFrame(int W, int H, Employee employee) {
    this.setTitle("Dashboard");

    Integer remainHolidays = 30 - Helpers.getApprovedHolidayRequests(employee).size();
    JLabel heading1 = new JLabel(String.format("Welcome %s! Remaining holidays: %d", employee.getFullName(), remainHolidays));
    heading1.setBounds(W/5, 20, W, 50);

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

    JLabel label1 = new JLabel("Start date:");
    label1.setBounds(W / 2 - W / 4 - 50, 150, W / 3, 50);

    UtilDateModel model2 = new UtilDateModel();
    JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, new Properties());
    JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2, new AbstractFormatter() {
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
    datePicker2.setBounds(W / 2 - W / 6, 250, W / 3, 50);

    JLabel label2 = new JLabel("End date:");
    label2.setBounds(W / 2 - W / 4 - 50, 230, W / 3, 50);

    JTextField inputTitle = new JTextField("Reason for holiday");
    inputTitle.setBounds(W / 2 - W / 6, 330, W / 3, 50);
    Helpers.setupInput(inputTitle, "Reason for holiday");

    JButton submitButton = new JButton("Submit");
    submitButton.setBounds(W / 2 - W / 6, 400, W / 3, 40);
    submitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        HolidayRequest hr = new HolidayRequest();
        hr.setTitle(inputTitle.getText());
        Date startDate = (Date)datePicker.getModel().getValue();
        hr.setDateStart(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        Date endDate = (Date)datePicker2.getModel().getValue();
        hr.setDateEnd(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        if (Helpers.createHolidayRequest(hr, employee) != null) {
          JOptionPane.showMessageDialog(null, "Success!");
        } else {
          JOptionPane.showMessageDialog(null, "Could not create holiday request. Try again!");
        }
      }
    });

    this.add(heading1);
    this.add(heading2);
    this.add(datePicker);
    this.add(label1);
    this.add(datePicker2);
    this.add(label2);
    this.add(inputTitle);
    this.add(submitButton);

    this.setSize(W, H);
    this.setLayout(null);
  }
}
