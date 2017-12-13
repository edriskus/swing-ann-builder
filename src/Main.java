import org.json.JSONObject;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends javax.swing.JFrame {

    NeuralNetwork ann = null;
    private JPanel panel1;
    Element current = null;
    int x_k, y_k;

    public Main() {
        setupDrawing();
        list1.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                    setFromTrainData(list1.getSelectedValue());
                }
            }
        });
    }

    public static void main(String[] args) {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) { }
        catch (ClassNotFoundException e) { }
        catch (InstantiationException e) { }
        catch (IllegalAccessException e) { }
        JFrame frame = new JFrame("Main");
        Main mainInstance = new Main();
        frame.setContentPane(mainInstance.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setting window size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height * 2 / 3;
        int width = screenSize.width * 2 / 3;

        frame.setSize(new Dimension(width, height));
        frame.setLocation(screenSize.width/2-width/2, screenSize.height/2-height/2);

        frame.setVisible(true);
        frame.setJMenuBar(mainInstance.generateMenus());
    }

    /* Add menus (it is not possible to do in IntelliJ IDEA's Main editor */
    public JMenuBar generateMenus() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        m_input = new javax.swing.JMenuItem();
        m_output = new javax.swing.JMenuItem();
        m_neuron = new javax.swing.JMenuItem();
        m_mapper = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        m_train_text = new javax.swing.JMenuItem();
        m_train_import = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        m_help = new javax.swing.JMenuItem();

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("New network");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem3.setText("Save as");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Open");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Quit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Add");

        m_neuron.setText("Hidden neuron");
        m_neuron.setEnabled(false);
        m_neuron.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_neuronActionPerformed(evt);
            }
        });
        jMenu2.add(m_neuron);

        m_output.setText("Output neuron");
        m_output.setEnabled(false);
        m_output.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_outputActionPerformed(evt);
            }
        });
        jMenu2.add(m_output);

        m_mapper.setText("Mapping node");
        m_mapper.setEnabled(false);
        m_mapper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_mapperActionPerformed(evt);
            }
        });
        jMenu2.add(m_mapper);

        m_input.setText("Feature");
        m_input.setEnabled(false);
        m_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_inputActionPerformed(evt);
            }
        });
        jMenu2.add(m_input);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Train");

        m_train_import.setText("Import file");
        m_train_import.setEnabled(false);
        m_train_import.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_train_importActionPerformed(evt);
            }
        });
        jMenu3.add(m_train_import);

        m_train_text.setText("Paste from clipboard");
        m_train_text.setEnabled(false);
        m_train_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_train_textActionPerformed(evt);
            }
        });
        jMenu3.add(m_train_text);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Help");

        m_help.setText("User manual");
        m_help.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_helpActionPerformed(evt);
            }
        });
        jMenu4.add(m_help);

        jMenuBar1.add(jMenu4);
        return jMenuBar1;
    }

    private void setupDrawing() {
        drawing1 = new Drawing();

        drawing1.setBackground(new java.awt.Color(255, 255, 255));
        drawing1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                drawing1MouseDragged(evt);
            }
        });
        drawing1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                drawing1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                drawing1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                drawing1MouseReleased(evt);
            }
        });

        panel1.add(drawing1);
    }

    // "Quit" menu action callback
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    // Menu action
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
        final Main selff = this;
        File file = new File("neural_network.json");
        try {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(selff) == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                // save to file
            }
            JSONObject res = ann.serialize();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(res.toString());
            fileWriter.flush();
        } catch(Exception ex) {
            System.err.println("Klaida skaitant faila");
        }
    }

    // Menu action
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {
        final Main selff = this;
        File file;
        try {
            final JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(selff);
            file = fc.getSelectedFile();
            String content = new Scanner(file).useDelimiter("\\Z").next();
            System.out.println(content);
            JSONObject obj = new JSONObject(content);
            this.ann.deserialize(obj);
            this.drawing1.repaint();
        } catch(Exception ex) {
            System.err.println("Klaida skaitant faila");
        }
    }

    // "New network" menu action callback
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        ann = new NeuralNetwork();

        m_input.setEnabled(true);
        m_output.setEnabled(true);
        m_neuron.setEnabled(true);
        m_mapper.setEnabled(true);
        m_train_import.setEnabled(true);
        m_train_text.setEnabled(true);

        drawing1.setNetwork(ann);
        drawing1.repaint();
    }

    // Mouse press event callback
    private void drawing1MousePressed(java.awt.event.MouseEvent evt) {
        this.drawing1.repaint();
        int x = evt.getX();
        int y = evt.getY();
        if(SwingUtilities.isLeftMouseButton(evt) || SwingUtilities.isRightMouseButton(evt)) {
            for (Element e : ann.getElements()) {
                if (x > e.getX() && x < (e.getX() + drawing1.zoom)
                        && y > e.getY() && y < (e.getY() + drawing1.zoom)) {
                    current = e;
                    x_k = evt.getX() - e.getX();
                    y_k = evt.getY() - e.getY();
                    break;
                }
                current = null;
            }
        }
    }

    // Mouse drag event callback
    private void drawing1MouseDragged(java.awt.event.MouseEvent evt) {
        if (current != null) {
            if (evt.getX() > 0 && evt.getX() < drawing1.getWidth() + drawing1.zoom) {
                if (SwingUtilities.isRightMouseButton(evt)) {
                    if(current instanceof AnnFeature) {
                        drawing1.removeDraggingConnection();
                        current = null;
                    }
                    drawing1.paintConnection(current.getX(), current.getY(), evt.getX(), evt.getY());
                    drawing1.repaint();
                } else if (SwingUtilities.isLeftMouseButton(evt)) {
                    this.drawing1.setMouseDown();
                    current.setX(evt.getX() - x_k);
                    current.setY(evt.getY() - y_k);
                    int xa = drawing1.getWidth() / 2 - 125;
                    int xb = drawing1.getWidth() / 2 + 125;
                    int ya = drawing1.getHeight() -100;
                    int yb = drawing1.getHeight();
                    if(evt.getX() > xa && evt.getX() < xb && evt.getY() > ya && evt.getY() < yb) {
                        current.hoverDelete = true;
                    } else current.hoverDelete = false;
                    drawing1.repaint();
                }
            }
        }
    }

    // Mouse release event callback
    private void drawing1MouseReleased(java.awt.event.MouseEvent evt) {
        this.drawing1.setMouseUp();
        drawing1.repaint();
        if (current != null) {
            int x = evt.getX();
            int y = evt.getY();
            if (SwingUtilities.isRightMouseButton(evt)) {
                for (Element e : ann.getElements()) {
                    if (x > e.getX() && x < (e.getX() + drawing1.zoom)
                            && y > e.getY() && y < (e.getY() + drawing1.zoom)) {
                        if(e instanceof AnnMapper) {

                        } else {
                            current.addInput(e);
                        }
                        break;
                    }
                }
                drawing1.removeDraggingConnection();
                current = null;
                drawing1.repaint();
            } else if (SwingUtilities.isLeftMouseButton(evt)) {
                int xa = drawing1.getWidth() / 2 - 125;
                int xb = drawing1.getWidth() / 2 + 125;
                int ya = drawing1.getHeight() -100;
                int yb = drawing1.getHeight();
                if(evt.getX() > xa && evt.getX() < xb && evt.getY() > ya && evt.getY() < yb) {
                    this.ann.remove(current);
                }
                current = null;
            }
        }
    }

    // Menu action add Input element
    private void m_inputActionPerformed(java.awt.event.ActionEvent evt) {
        ann.add(new AnnFeature());
        drawing1.repaint();
    }

    // Menu action
    private void m_outputActionPerformed(java.awt.event.ActionEvent evt) {
        ann.add(new AnnOutput());
        drawing1.repaint();
    }

    // Menu action
    private void m_neuronActionPerformed(java.awt.event.ActionEvent evt) {
        ann.add(new AnnNeuron());
        drawing1.repaint();
    }

    // Menu action
    private void m_mapperActionPerformed(java.awt.event.ActionEvent evt) {
        ann.add(new AnnMapper());
        drawing1.repaint();
    }

    // Menu action
    private void m_train_importActionPerformed(java.awt.event.ActionEvent evt) {

        TrainImport sp = new TrainImport(ann);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        sp.pack();
        int height = sp.getHeight();
        int width = sp.getWidth();
        sp.setLocation(screenSize.width/2-width/2, screenSize.height/2-height/2);
        sp.setVisible(true);
        sp.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                populateTrainList();
            }
        });

        drawing1.repaint();
    }

    // Menu action
    private void m_train_textActionPerformed(java.awt.event.ActionEvent evt) {
        TrainText sp = new TrainText(ann);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        sp.pack();
        int height = sp.getHeight();
        int width = sp.getWidth();
        sp.setLocation(screenSize.width/2-width/2, screenSize.height/2-height/2);
        sp.setVisible(true);
        drawing1.repaint();
        sp.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                populateTrainList();
            }
        });
    }

    // Menu action
    private void m_helpActionPerformed(java.awt.event.ActionEvent evt) {
        UserManual sp = new UserManual();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        sp.pack();
        int height = sp.getHeight();
        int width = sp.getWidth();
        sp.setLocation(screenSize.width/2-width/2, screenSize.height/2-height/2);
        sp.setVisible(true);
    }


    // Mouse click event callback
    private void drawing1MouseClicked(java.awt.event.MouseEvent evt) {
        int x = evt.getX();
        int y = evt.getY();
        for (Element e : ann.getElements()) {
            if (x > e.getX() && x < (e.getX() + drawing1.zoom)
                    && y > e.getY() && y < (e.getY() + drawing1.zoom)) {
                current = e;
                //cia dar reikia if, kad input nerodytu to dialogo
                //plius galit tikrinti gal tai not kuris jau turti 1 input ir t.t.
                if(e instanceof AnnFeature) {
                    // Su kiekvienu paspaudimu pakeicia INPUT reiksme
                    openValueModal(e);
                    drawing1.repaint();
                    break;
                } else if(e instanceof AnnMapper) {

                } else {
                    break;
                }
            }
            current = null;
        }
    }

    // Menu action
    private void openValueModal(Element e) {
        FeatureSettings sp = new FeatureSettings(e);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        sp.pack();
        int height = sp.getHeight();
        int width = sp.getWidth();
        sp.setLocation(screenSize.width/2-width/2, screenSize.height/2-height/2);
        sp.setVisible(true);
    }


    public void populateTrainList() {
        this.list1.setListData(this.ann.currentTrainData.toArray());
        System.out.println("Populated");
    }

    public void setFromTrainData(Object obj) {
        this.ann.setDataPoint((ArrayList<Double>)obj);
        this.drawing1.repaint();
    }

    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem m_neuron;
    private javax.swing.JMenuItem m_input;
    private javax.swing.JMenuItem m_output;
    private javax.swing.JMenuItem m_mapper;
    private javax.swing.JMenuItem m_train_text;
    private javax.swing.JMenuItem m_train_import;
    private javax.swing.JMenuItem m_help;
    private Drawing drawing1;
    private JList list1;

}
