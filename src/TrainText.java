import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TrainText extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textField1;
    private JLabel errorField;
    private JTextField textField2;
    private NeuralNetwork ann;

    public TrainText(NeuralNetwork ann) {
        this.ann = ann;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        textField2.setText("100");

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        String input = textField1.getText();
        try {
            int iterations = Integer.parseInt(textField2.getText());
            ArrayList<ArrayList<Double>> data = this.ann.formatData(input);
            this.ann.train(data, iterations);
            dispose();
        } catch(Exception e) {
            System.err.println("EXCEPTION");
            System.err.println(e);
            this.errorField.setVisible(true);
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
