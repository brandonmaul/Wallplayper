package view;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.sun.javafx.application.PlatformImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.ProcessData;

import javax.imageio.ImageIO;

public class Main extends Application{

    private Stage _stage;
    private static final File iconImageLoc = new File("mac-os-tray-icon.png");

    @Override
    public void start(Stage stage) throws Exception {
        this._stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Wallplayper");
        stage.setResizable(false);


        //for not exiting on close
        Platform.setImplicitExit(false);
        PlatformImpl.setTaskbarApplication(true);
        if(System.getProperty("os.name").startsWith("Mac")){
            javax.swing.SwingUtilities.invokeLater(this::addAppToMacTray);
        }

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(System.getProperty("os.name").contains("windows")){
            ProcessData pd = new ProcessData();
            try {
                pd.enableAutoStart();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        launch(args);
    }

    private void addAppToMacTray() {
        try {
            // ensure awt toolkit is initialized.
            java.awt.Toolkit.getDefaultToolkit();

            // app requires system tray support, just exit if there is no support.
            if (!java.awt.SystemTray.isSupported()) {
                System.out.println("No system tray support, application exiting.");
                Platform.exit();
            }

            // set up a system tray icon.
            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            //java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(ImageIO.read(new URL("http://icons.iconarchive.com/icons/scafer31000/bubble-circle-3/16/GameCenter-icon.png")));
            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(ImageIO.read(new File("src/view/AppImages/picture.png")));
            // if the user double-clicks on the tray icon, show the main app stage.
            trayIcon.addActionListener(event -> Platform.runLater(this::showStage));

            // if the user selects the default menu item (which includes the app name),
            // show the main app stage.
            java.awt.MenuItem openItem = new java.awt.MenuItem("Open");
            openItem.addActionListener(event -> Platform.runLater(this::showStage));

            // the convention for tray icons seems to be to set the default icon for opening
            // the application stage in a bold font.
            java.awt.Font defaultFont = java.awt.Font.decode(null);
            java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
            openItem.setFont(boldFont);

            // to really exit the application, the user must go to the system tray icon
            // and select the exit option, this will shutdown JavaFX and remove the
            // tray icon (removing the tray icon will also shut down AWT).
            java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
            exitItem.addActionListener(event -> {
                Platform.exit();
                tray.remove(trayIcon);
            });

            // setup the popup menu for the application.
            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
            popup.add(openItem);
            popup.addSeparator();
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);

            // add the application tray icon to the system tray.
            tray.add(trayIcon);
        } catch (java.awt.AWTException | IOException e) {
            System.out.println("Unable to init system tray");
            e.printStackTrace();
        }
    }

    private void showStage() {
        if (_stage != null) {
            _stage.show();
            _stage.toFront();
        }
    }
}
