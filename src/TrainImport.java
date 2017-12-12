import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.Scanner;

public class TrainImport extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton pasirinktiFailaButton;
    private JTextField textField1;

    private NeuralNetwork ann;
    private File file;

    public TrainImport(NeuralNetwork ann) {
        this.ann = ann;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        textField1.setText("100");
        final TrainImport selff = this;
        pasirinktiFailaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    final JFileChooser fc = new JFileChooser();
                    int returnVal = fc.showOpenDialog(selff);
                    file = fc.getSelectedFile();
                } catch(Exception ex) {
                    System.err.println("Klaida skaitant faila");
                }
            }
        });

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
        try {
            int iterations = Integer.parseInt(textField1.getText());
            String content = new Scanner(file).useDelimiter("\\Z").next();
            this.ann.train(this.ann.formatData(content), iterations);
        } catch(Exception e) {
            System.err.println("Klaida skaitant faila");
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
