import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Drawing extends javax.swing.JPanel {
    NeuralNetwork ann = null;
    int zoom = 50;
    BufferedImage imageNeuron, imageMapper, imageFeature, imageDelete, imageOutput;
    DraggingConnection draggingConnection;
    boolean mouseIsDown = false;
    private JPanel panel1;
    private Color paintColor;

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public void setNetwork(NeuralNetwork n) {
        this.ann = n;
    }

    public void setColor(String hex) {
        this.paintColor = hexToColor(hex);
    }

    private Color hexToColor(String hex) {
        return new Color(
                Integer.valueOf( hex.substring( 1, 3 ), 16 ),
                Integer.valueOf( hex.substring( 3, 5 ), 16 ),
                Integer.valueOf( hex.substring( 5, 7 ), 16 ) );
    }

    /* Constructor */
    public Drawing() {
        this.paintColor = new Color(0, 0, 0);
        URL resourceNeuron = getClass().getResource("images/Neuron.png");
        URL resourceMapper = getClass().getResource("images/Mapper.png");
        URL resourceFeature = getClass().getResource("images/Feature.png");
        URL resourceOutput= getClass().getResource("images/Output.png");
        URL resourceDelete = getClass().getResource("images/Delete.png");
        try {
            imageNeuron = ImageIO.read(resourceNeuron);
            imageMapper = ImageIO.read(resourceMapper);
            imageFeature = ImageIO.read(resourceFeature);
            imageDelete = ImageIO.read(resourceDelete);
            imageOutput= ImageIO.read(resourceOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Overriding for custom component painting */
    @Override
    public void paintComponent(Graphics g){
        Color redColor = hexToColor("#17E9DD");
        Color greenColor = hexToColor("#CE56BB");
        Color yellowColor = hexToColor("#1377FF");
        super.paintComponent(g);
        g.setFont(new Font("default", Font.BOLD, 16));
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(paintColor);
        if(ann==null){
            g.drawString("Project empty. Please create a new Artificial Neural Network", this.getWidth() / 2 - 275, this.getHeight() / 2);
        }else{
            for(Element e:ann.getElements()){
                if(e.hoverDelete == true) g.setXORMode(yellowColor);
                g.drawImage(resize(getElementImage(e), zoom, zoom), e.getX(), e.getY(), this);
                //}
                g.drawString((Math.round(e.output()*100.0)/100.0) + "", e.getX() + zoom / 2 - 5, e.getY() - 6);
                g.setFont(new Font("default", Font.PLAIN, 13));
                if(e instanceof AnnNeuron || e instanceof AnnOutput) {
                    g.drawString("b: " + (Math.round(e.bias*100.0)/100.0), e.getX() + zoom / 2 - 5, e.getY() + zoom + 15);
                }
                for(int i = 0; i < e.inputs.size(); i++) {
                    Element input = e.inputs.get(i);
                    g2.drawLine(e.getX(), e.getY() + zoom / 2, input.getX() + zoom, input.getY() + zoom / 2);
                    if(e instanceof AnnNeuron || e instanceof AnnOutput) {
                        int x = (int) ((e.getX() + input.getX()) / 2.0);
                        int y = (int) ((e.getY() + input.getY()) / 2.0);
                        g.drawString("w: " + (Math.round(e.weights.get(i) * 100.0) / 100.0), x, y);
                    }
                }
                g.setFont(new Font("default", Font.BOLD, 16));
            }
            if(draggingConnection != null) {
                g2.drawLine(draggingConnection.ax, draggingConnection.ay, draggingConnection.bx, draggingConnection.by);
            }
            if(mouseIsDown == true) {
                // Istrinti mygtukas
                g.drawImage(imageDelete, this.getWidth() / 2 - 25, this.getHeight() -70, this);
                g.drawString("Tempkite čia, kad ištrintumėte", this.getWidth() / 2 - 140, this.getHeight() -80);
            }
        }
    }

    /* Paint a temporary dragging connection */
    public void paintConnection(int ax, int ay, int bx, int by) {
        this.draggingConnection = new DraggingConnection(ax, ay + zoom/2, bx, by);
    }
    public void removeDraggingConnection() {
        this.draggingConnection = null;
    }

    public void setMouseDown() {
        this.mouseIsDown = true;
    }
    public void setMouseUp() {
        this.mouseIsDown = false;
    }

    /* Get an image by parent class type */
    private BufferedImage getElementImage(Element e) {
        if(e instanceof AnnFeature) return imageFeature;
        if(e instanceof AnnMapper) return imageMapper;
        if(e instanceof AnnOutput) return imageOutput;
        if(e instanceof AnnNeuron) return imageNeuron;
        return imageNeuron;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

}
