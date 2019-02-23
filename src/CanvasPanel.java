import javax.swing.*;
import java.awt.*;

/**
 * Class of a Panel, which draws web and creatures: Rabbits and Wolf.
 * Additionally drawing methods of this class are called every time
 * simulation changes.
 * @author wojtekrafalo
 * @version 1.0
 * @since 1.0
 */
public class CanvasPanel extends JPanel{

    int iRabbits= 5;
    int iSizeX = 10;
    int iSizeY = 10;
    int iTimeSleep;

    int X = MainWindow.WIDTH/iSizeX;
    int Y = MainWindow.HEIGHT2/iSizeY;

    /**
     * Default constructor. Sets size of a panel and its background.
     */
    CanvasPanel() {
        setPreferredSize(new Dimension(MainWindow.WIDTH, MainWindow.HEIGHT1));
        setBackground(Color.RED);
    }

    /**
     * Method inherited from JComponent. Provides drawing of all shapes at canvas.
     * @param g component on which elements are drawn.
     */
    @Override
    protected void paintComponent (Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        drawWeb(g2d);
        g2d.setColor(Color.BLUE);
        drawFigures(g2d);
        repaint();
    }

    /**
     * This method draws a web of a simulation. It is only called once.
     * @param g2d component on which web is drawn.
     */
    private void drawWeb (Graphics2D g2d) {
        for (int i = 0; i < iSizeX; i++) {
            g2d.drawLine(X * (i + 1),0 , X * (i + 1),1000);
        }

        for (int i = 0; i < iSizeY; i++) {
            g2d.drawLine(0,Y * (i + 1), 1000, Y * (i + 1));
        }
    }

    /**
     * This method draws creatures: Rabbits and Wolf.
     * It is called every time a simulation is changed.
     * @param g2d component on which creatures is drawn.
     */
    private void drawFigures (Graphics2D g2d) {
        for (int i = 0; i < iSizeX; i++) {
            for (int j = 0; j < iSizeY; j++) {
                if (MainWindow.tabHelp[i][j][0] == 1) g2d.fillOval(X * i, Y * j, X, Y);
                else if (MainWindow.tabHelp[i][j][0] == 2) {g2d.setColor(Color.CYAN); g2d.fillRect(X * i, Y * j, X, Y); g2d.setColor(Color.BLUE);}
            }
        }
    }
}
