import java.awt.*;

/**
 * Main class to run the application.
 * Creates object of a class MainWindow at an EventQueue.
 * @author wojtekrafalo
 * @version 1.0
 * @since 1.0
 */
public class Main {

    /**
     * Main method used to run Project Window.
     * @param args arguments provided by default main method.
     */
    public static void main (String[] args) {
        EventQueue.invokeLater(MainWindow::new);
    }
}