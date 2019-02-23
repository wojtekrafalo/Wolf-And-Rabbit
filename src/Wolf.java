/**
 * This class is thread of Wolf. Calls methods from Field class.
 * @author wojtekrafalo
 * @version 1.0
 * @since 1.0
 */
public class Wolf implements Runnable {

    private Field field;

    /**
     * Constructor sets Field class with all variables.
     * @param field object of a field class.
     */
    Wolf(Field field) {
        this.field = field;
    }

    /**
     * Method inherited from Runnable. Runs a Thread of Wolf and provides its action.
     */
    @Override
    public void run() {
        while (!Thread.interrupted() && field.getiRabbits()>0) {
            try {
                field.WolfIsWaiting();
                field.WolfIsMoving();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
