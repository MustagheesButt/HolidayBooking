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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import holidaysManager.entities.Emp;
import holidaysManager.entities.HRequest;

public class MainFrame extends CustomFrame {
  public MainFrame(Emp e) {
    this.setTitle(String.format("Welcome %s", e.getFullName()));

    Integer remainHolidays = 30 - Helpers.getApprovedHolidayRequests(e).size();
    JLabel h1 = new JLabel(String.format("Remaining holidays: %d", remainHolidays));
    h1.setBounds(width/5, 20, width, 50);

    JLabel h2 = new JLabel("New Holiday Request");
    h2.setBounds(width / 2 - width / 6, 90, width / 3, 50);

    UtilDateModel model = new UtilDateModel();
    JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
    JDatePickerImpl dp1 = new JDatePickerImpl(datePanel, new AbstractFormatter() {
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
    dp1.setBounds(width / 2 - width / 6, 170, width / 3, 25);

    JLabel label1 = new JLabel("Starts At:");
    label1.setBounds(width / 2 - width / 4 - 50, 150, width / 3, 50);

    UtilDateModel model2 = new UtilDateModel();
    JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, new Properties());
    JDatePickerImpl dp2 = new JDatePickerImpl(datePanel2, new AbstractFormatter() {
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
    dp2.setBounds(width / 2 - width / 6, 250, width / 3, 25);

    JLabel label2 = new JLabel("Ends At:");
    label2.setBounds(width / 2 - width / 4 - 50, 230, width / 3, 50);

    JTextField inputTitle = new JTextField("Reason for holiday");
    inputTitle.setBounds(width / 2 - width / 6, 330, width / 3, 50);
    Helpers.setupInput(inputTitle, "Reason for holiday");

    JButton btnSubmit = new JButton("Submit");
    btnSubmit.setBounds(width / 2 - width / 6, 400, width / 3, 40);
    btnSubmit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        HRequest hr = new HRequest();
        hr.setTitle(inputTitle.getText());
        Date startDate = (Date)dp1.getModel().getValue();
        hr.setDateStart(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        Date endDate = (Date)dp2.getModel().getValue();
        hr.setDateEnd(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        if (Helpers.createHolidayRequest(hr, e) != null) {
          JOptionPane.showMessageDialog(null, "Success!");
        } else {
          JOptionPane.showMessageDialog(null, "Failed to register request!");
        }
      }
    });

    this.add(h1);
    this.add(h2);
    this.add(dp1);
    this.add(label1);
    this.add(dp2);
    this.add(label2);
    this.add(inputTitle);
    this.add(btnSubmit);
  }
}
