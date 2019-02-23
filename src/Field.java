import java.util.concurrent.TimeUnit;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.max;


/**
 * Class stores data like number of creatures, their positions.
 * Additionally provides all actions of a simulation.
 * Methods of this class are synchronized with Wolf and Rabbit classes.
 * @author wojtekrafalo
 * @version 1.0
 * @since 1.0
 */
public class Field {

    private boolean boolWolf = true;
    private boolean boolRabbit = false;
    int xWolf;
    int yWolf;
    private int iRabbits = MainWindow.iRabbits;

    private int roundsToWait = 0;
    private int dist;


    /**
     * This method stops Wolf thread, when it will go on a pile occupied by a Rabbit.
     * Wolf waits 5 rounds. It simulates a "sleeping" of a Wolf.
     */
    synchronized void WolfIsWaiting() {
        while (!boolWolf) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }


    /**
     * Method which stops Rabbit thread when Wolf moves.
     */
    synchronized void RabbitIsWaiting() {
        if (!boolRabbit) {
            try {
                wait();
            } catch (InterruptedException e ) {}
        }
    }


    /**
     * Method, which determines moving of Rabbits.
     * @throws InterruptedException exception required by a sleep method.
     */
    synchronized void RabbitIsMoving() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(MainWindow.canvasPanel.iTimeSleep);                                                 //delay of a Rabbit
        for (int i = 0; i < MainWindow.canvasPanel.iSizeX; i++) {
            for (int j = 0; j < MainWindow.canvasPanel.iSizeY; j++) {
                MainWindow.tabHelp[i][j][1] = 0;
            }
        }

        int changes = 1;
        while (changes > 0) {
            changes = 0;
            for (int i = 0; i < MainWindow.canvasPanel.iSizeX; i++) {
                for (int j = 0; j < MainWindow.canvasPanel.iSizeY; j++) {
                    if (MainWindow.tabHelp[i][j][0] == 1 && MainWindow.tabHelp[i][j][1] == 0) {
                        if (RabbitMove(i, j)) changes++;
                    }
                }
            }
        }

        if (roundsToWait == 0) {
            boolRabbit = false;
            boolWolf = true;
            notifyAll();
        } else if ( roundsToWait > 0) {
            boolWolf = false;
            notifyAll();
            roundsToWait--;
        }
    }


    /**
     * Method, which determines moving of Wolf.
     * @throws InterruptedException exception required by a sleep method.
     */
    public synchronized void WolfIsMoving () throws InterruptedException {
        int minDist = 100000;
        int howRabbits = 0;
        TimeUnit.MILLISECONDS.sleep(MainWindow.canvasPanel.iTimeSleep);                                                 //delay of a Wolf.
        for (int i = 0; i < MainWindow.canvasPanel.iSizeX; i++) {
            for (int j = 0; j < MainWindow.canvasPanel.iSizeY; j++) {
                dist = maxDist(i, j);
                if (( dist < minDist) && (MainWindow.tabHelp[i][j][0] == 1)) {
                    minDist = dist; howRabbits = 1;}
                else if ((dist == minDist) && (MainWindow.tabHelp[i][j][0] == 1)) howRabbits++;                         //draw of a Rabbit, if there is more than one closest.
            }
        }

        int which = 0;
        if (howRabbits >0) which = MainWindow.generator.nextInt(howRabbits);

        int temp=0;
        boolean WolfMoved = false;

        for (int i = 0; i < MainWindow.canvasPanel.iSizeX; i++) {
            for (int j = 0; j < MainWindow.canvasPanel.iSizeY; j++) {

                dist = maxDist(i, j);
                if ((dist == minDist) && (MainWindow.tabHelp[i][j][0] == 1)) {
                    if (temp==which) {WolfMove(i,j); WolfMoved = true; break;}
                    temp++;
                }
            }
            if (WolfMoved) break;
        }

        boolRabbit = true;
        boolWolf = false;
        notifyAll();
    }

    /**
     * This method introduces changes of Wolf in list with creatures.
     * @param i horizontal position of a Wolf
     * @param j vertical position of a Wolf
     */
    private synchronized void WolfMove(int i, int j) {
        MainWindow.tabHelp[xWolf][yWolf][0] = 0;
        if (xWolf < i) xWolf++;
        else if (xWolf > i) xWolf--;

        if (yWolf < j) yWolf++;
        else if (yWolf > j) yWolf--;

        if (MainWindow.tabHelp[xWolf][yWolf][0] == 1) {boolWolf = false; roundsToWait = 4; iRabbits--;}
        MainWindow.tabHelp[xWolf][yWolf][0] = 2;
    }

    /**
     * This method returns false, if Point (i, j) doesn't belong to list of creatures.
     * @param i horizontal position of a Point.
     * @param j vertical position of a Point.
     * @return false if Point (x,y) is out of a Field.
     */
    private boolean notContains(int i, int j) {
        return (i < 0 || i >= MainWindow.iSizeX || j < 0 || j >= MainWindow.iSizeY);
    }

    /**
     * This method returns distance between Wolf and Point (i, j), which Wolf can overcome in this number of rounds.
     * @param i horizontal position of a target.
     * @param j vertical position of a target.
     * @return distance between Wolf and Point (i, j)
     */
    private int maxDist(int i, int j) {
        return max(abs(xWolf - i), abs(yWolf - j));
    }

    /**
     * This method introduces changes of Rabbit (i ,j) in list with creatures.
     * @param i horizontal position of a Rabbit.
     * @param j vertical position of a Rabbit.
     * @return true if Rabbit (i, j) made any move.
     */
    private synchronized boolean RabbitMove(int i, int j) {
        int xMoved = i, yMoved = j;

        int howMany = 0;
        int maxDist = -1;
            boolean RabMoved = false;
            for (int k = i-1; k<= i+1; k++) {
                for (int l = j-1; l<=j+1; l++) {
                    if (notContains(k, l)) continue;
                    if (k==i && l==j) continue;
                    dist = maxDist(k, l);
                    if (dist > maxDist && MainWindow.tabHelp[k][l][0] == 0) {
                        maxDist = dist; howMany = 1;}
                    if (dist == maxDist && MainWindow.tabHelp[k][l][0] == 0) {
                        howMany = 1;}
                }
            }

            int which = 0;
            if (howMany > 0) which = MainWindow.generator.nextInt(howMany);

            int temp = 0;
            for (int k = i-1; k<= i+1; k++) {
                for (int l = j-1; l<=j+1; l++) {
                    if (notContains(k, l)) continue;
                    if (k==i && l==j) continue;
                    dist = maxDist(k, l);
                    if (dist == maxDist && MainWindow.tabHelp[k][l][0] == 0) {
                        if (temp == which) {
                            xMoved = k;
                            yMoved = l;
                            RabMoved = true;
                            break;
                        }
                        temp++;
                    }
                }
                if (RabMoved) break;
            }

            if (RabMoved) {
                MainWindow.tabHelp[i][j][0] = 0;
                MainWindow.tabHelp[i][j][0] = 0;
                MainWindow.tabHelp[i][j][1] = 0;

                MainWindow.tabHelp[xMoved][yMoved][0] = 1;
                MainWindow.tabHelp[xMoved][yMoved][0] = 1;
                MainWindow.tabHelp[xMoved][yMoved][1] = 1;
                return true;
            }
            return false;
    }

    /**
     * Default getter of number of Rabbits.
     * @return number of Rabbits.
     */
    int getiRabbits () {
        return iRabbits;
    }
}
