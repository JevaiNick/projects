import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;


public class MainForm {
    private JPanel mainPanel;
    private JButton button;
    private JTextField nameField;
    private JTextField surnField;
    private JTextField patronField;
    private JLabel nameLable;
    private JLabel surnameLable;
    private JLabel patronymicLable;

    public MainForm() {
        button.addActionListener(new Action() {
            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (patronField.isVisible()) {
                    if (!nameField.getText().trim().equals("") && !surnField.getText().trim().equals("")) {
                        String str = nameField.getText() + " "
                                + surnField.getText() + " "
                                + patronField.getText();
                        patronymicLable.setVisible(false);
                        patronField.setVisible(false);
                        surnField.setVisible(false);
                        surnameLable.setVisible(false);
                        nameLable.setText("N.S.P.");
                        nameField.setText(str);
                        button.setText("Expand");
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Required fields are empty!", "Oops", JOptionPane.PLAIN_MESSAGE);
                    }
                } else {
                    String[] strings = nameField.getText().split(" ");
                    if (strings.length == 2 || strings.length == 3) {
                        patronymicLable.setVisible(true);
                        patronField.setVisible(true);
                        patronField.setText(strings.length == 3 ? strings[2] : "");
                        surnField.setVisible(true);
                        surnField.setText(strings[1]);
                        surnameLable.setVisible(true);
                        nameLable.setText("Name");
                        nameField.setText(strings[0]);
                    }
                    else {
                        JOptionPane.showMessageDialog(mainPanel, "Wrong input!", "Oops", JOptionPane.PLAIN_MESSAGE);

                    }
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }


}
