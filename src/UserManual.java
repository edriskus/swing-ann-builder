import javax.swing.*;
import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

public class UserManual extends JDialog {
    private JPanel contentPane;
    private JPanel panel1;
    private JButton buttonOK;

    public UserManual() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        JFXPanel jfxPanel = new JFXPanel();
        panel1.add(jfxPanel);

        // Creation of scene and future interactions with JFXPanel
        // should take place on the JavaFX Application Thread
        Platform.runLater(() -> {
            WebView webView = new WebView();
            jfxPanel.setScene(new Scene(webView));
            webView.getEngine().load("http://cdn.tikm.lt/ANN/userguide.html");
        });
    }
}
