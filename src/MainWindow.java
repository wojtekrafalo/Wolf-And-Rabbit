import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


/**
 * Class represents a Window of application.
 * It contains objects of class like CanvasPanel and MenuPanel.
 * Provides answers of user's actions like closing window,
 * pressing buttons and menu items. It Also runs the whole simulation.
 * @author wojtekrafalo
 * @version 1.0
 * @since 1.0
 */
public class MainWindow extends JFrame implements ActionListener {

    static Random generator = new Random();

    static int iSizeX = 10;
    static int iSizeY = 10;
    static int iRabbits;
    static int iTimeSleep;

    static final int WIDTH = 600;
    public static final int HEIGHT = 700;
    static final int HEIGHT1 = 100;
    static final int HEIGHT2 = 600;

    static int[][][] tabHelp = new int[10][10][2];

    private MenuItem menuReset, menuInfo, menuExit;

    static Field field = new Field();
    static Runnable run1 = new Rabbit(field);
    static Runnable run2 = new Wolf(field);
    static Thread rabbit = new Thread(run1);
    static Thread wolf = new Thread(run2);

    private static MenuPanel menuPanel = new MenuPanel();
    static CanvasPanel canvasPanel = new CanvasPanel();

    @Override
    public void actionPerformed (ActionEvent event){
        String information = "Hello!" +
                "This program shows a simulation of game \"Wolf & Rabbits \".\n" +
                "One Wolf is running for all Rabbits and when \n" +
                "The circle means Wolf and square means Rabbit.\n" +
                "Please type number of Rabbits, size of bound and time of sleeping between round.";

        Object source = event.getSource();
        if (source.equals(menuReset)) {
//            iSizeX = 10;
//            iSizeY = 10;
//            iRabbits = 0;
//            iTimeSleep = 0;
//            menuPanel.x = 0;
//            menuPanel.y = 0;
//            menuPanel.iSizeX = 10;
//            menuPanel.iSizeY = 10;
//            menuPanel.iRabbits = 0;
//            menuPanel.iTimeSleep = 0;
//            canvasPanel.X = WIDTH/iSizeX;
//            canvasPanel.Y = HEIGHT2/iSizeY;
//            tabHelp = new int[10][10][2];
//            canvasPanel.iTimeSleep = 0;
//            canvasPanel.iRabbits=5;
//            canvasPanel.iSizeX=10;
//            canvasPanel.iSizeY=10;
//            canvasPanel.repaint();
        }

        if (source.equals(menuInfo)) JOptionPane.showMessageDialog(null, information);
        if (source.equals(menuExit)) System.exit(0);
    }

    /**
     * Default class Constructor
     */
    MainWindow() {
        super("Wolf and Rabbit");
        setBounds(0,0,620, 765);
        setLayout(null);

        menuPanel.setBounds(0,0,WIDTH,HEIGHT1);
        canvasPanel.setBounds(0,HEIGHT1,WIDTH,HEIGHT2);
        add(menuPanel);
        add(canvasPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        MenuBar myMenu = new MenuBar();
        Menu menu1 = new Menu("Menu");
        myMenu.add(menu1);

        menuReset = new MenuItem("RESET");
        menuReset.addActionListener(this);
        menuInfo = new MenuItem("INFO");
        menuInfo.addActionListener(this);
        menuExit = new MenuItem("EXIT");
        menuExit.addActionListener(this);

        menu1.add(menuReset);
        menu1.add(menuInfo);
        menu1.addSeparator();
        menu1.add(menuExit);

        setMenuBar(myMenu);
    }
}