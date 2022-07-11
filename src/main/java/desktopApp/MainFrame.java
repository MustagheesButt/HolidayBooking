package desktopApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
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

    JLabel h2 = new JLabel("New Holiday Request");
    h2.setBounds(width / 2 - width / 6, 90, width / 3, 50);
    h2.setForeground(Color.WHITE);

    JLabel l1 = new JLabel("Starts At:");
    l1.setBounds(20, 150, width / 3, 50);
    l1.setForeground(Color.WHITE);

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
    dp1.setBounds(100, 170, width / 3, 25);

    JLabel l2 = new JLabel("Ends At:");
    l2.setBounds(20, 230, width / 3, 50);
    l2.setForeground(Color.WHITE);

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
    dp2.setBounds(100, 250, width / 3, 25);

    JTextField reasonField = new JTextField("Reason for holiday");
    reasonField.setBounds(width / 2, 170, width / 2 - 10, 105);
    Helpers.setupInput(reasonField, "Reason for holiday");

    JButton btnSubmit = new JButton("Submit");
    btnSubmit.setBounds(width / 2 - width / 6, 330, width / 3, 40);
    btnSubmit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        HRequest hr = new HRequest();
        hr.setReason(reasonField.getText());
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

    this.add(h2);
    this.add(dp1);
    this.add(l1);
    this.add(dp2);
    this.add(l2);
    this.add(reasonField);
    this.add(btnSubmit);
  }
}
