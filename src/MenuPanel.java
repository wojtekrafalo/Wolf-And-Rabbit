import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class of a Panel with Menu. Uses objects of classes like CanvasPanel,
 *
 * @author wojtekrafalo
 * @version 1.0
 * @since 1.0
 */
public class MenuPanel extends JPanel implements ActionListener{

    private JButton buttonOK = new JButton("OK");
    private JTextField textSize1 = new JTextField(), textSize2 = new JTextField();
    private JTextField textNumberOfRabbits = new JTextField();
    private JTextField textSleeping = new JTextField();

    /**
     * Default constructor. Creates buttons, text fields, sets background, layout and size.
     */
    MenuPanel() {
        super();
        setBackground(Color.YELLOW);
        setLayout(null);
        setPreferredSize(new Dimension(MainWindow.WIDTH, MainWindow.HEIGHT1));

        Label labelSize = new Label("Size of board");
        labelSize.setBounds(10,10,200,20);
        textSize1.setBounds(210,10,100,20);
        textSize2.setBounds(320,10,100,20);

        Label labelNumber = new Label("Number of Rabbits");
        labelNumber.setBounds(10,40,200,20);
        textNumberOfRabbits.setBounds(210,40,100,20);

        Label labelSleep = new Label("Time of sleeping (in milliseconds)");
        labelSleep.setBounds(10,70,200,20);
        textSleeping.setBounds(210,70,100,20);

        buttonOK.addActionListener(this);
        buttonOK.setBounds(320,70,80,20);

        add(labelSize);
        add(textSize1);
        add(textSize2);
        add(labelNumber);
        add(textNumberOfRabbits);
        add(labelSleep);
        add(textSleeping);
        add(buttonOK);

    }

    /**
     * Method implemented from a ActionListener.
     * Reacts on actions like clicking buttons.
     * @param event object of an event of pressed button.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source.equals(buttonOK)) {
            try {
                int iSizeX = Integer.parseInt(textSize1.getText());
                int iSizeY = Integer.parseInt(textSize2.getText());
                int iRabbits = Integer.parseInt(textNumberOfRabbits.getText());
                int iTimeSleep = Integer.parseInt(textSleeping.getText());

                if (iSizeX <= 0     || iSizeX >= 1000 ||
                        iSizeY <= 0 || iSizeY >= 1000 ||
                        iRabbits >= iSizeY * iSizeX - 1 ||
                        iRabbits <0 || iTimeSleep <= 0 ||
                        iTimeSleep >= 6000) {

                    JOptionPane.showMessageDialog(null, "Please, type correct data.");
                }

                else {
                    MainWindow.canvasPanel.iSizeX = iSizeX;
                    MainWindow.iSizeX = iSizeX;
                    MainWindow.canvasPanel.iSizeY = iSizeY;
                    MainWindow.iSizeY = iSizeY;
                    MainWindow.canvasPanel.iRabbits = iRabbits;
                    MainWindow.iRabbits = iRabbits;
                    MainWindow.canvasPanel.iTimeSleep = iTimeSleep;
                    MainWindow.iTimeSleep = iTimeSleep;
                    MainWindow.canvasPanel.X = MainWindow.WIDTH / iSizeX;
                    MainWindow.canvasPanel.Y = MainWindow.HEIGHT2 / iSizeY;


                    MainWindow.tabHelp = new int[iSizeX][iSizeY][2];                                                    //initialization of an array
                    int x = 0;
                    int y = 0;
                    int xWolf = 0, yWolf = 0;

                    while (MainWindow.tabHelp[x][y][0] == 0) {                                                          //random typing of a wolf's position
                        x = MainWindow.generator.nextInt(iSizeX);
                        y = MainWindow.generator.nextInt(iSizeY);
                        if (MainWindow.tabHelp[x][y][0] == 0) {
                            MainWindow.tabHelp[x][y][0] = 2;
                            xWolf = x;
                            yWolf = y;
                        }
                    }

                    for (int i = 0; i < iRabbits; i++) {                                                                //random typing of rabbits
                        x = MainWindow.generator.nextInt(iSizeX);
                        y = MainWindow.generator.nextInt(iSizeY);
                        if (MainWindow.tabHelp[x][y][0] == 0) MainWindow.tabHelp[x][y][0] = 1;
                        else i--;
                    }

                    MainWindow.canvasPanel.repaint();

                    MainWindow.field = new Field();                                                                     //initialization of thread and a field object
                    MainWindow.run1 = new Rabbit(MainWindow.field);
                    MainWindow.run2 = new Wolf(MainWindow.field);
                    MainWindow.rabbit = new Thread(MainWindow.run1);
                    MainWindow.wolf = new Thread(MainWindow.run2);

                    MainWindow.field.xWolf = xWolf;
                    MainWindow.field.yWolf = yWolf;

                    MainWindow.wolf.start();                                                                            //start of threads
                    MainWindow.rabbit.start();
                }
            } catch (NumberFormatException e) {JOptionPane.showMessageDialog(null, "Please, type correct data.");}
        }
    }
}
