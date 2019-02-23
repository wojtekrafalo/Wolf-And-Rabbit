/**
 * This class is thread of Rabbit. Calls methods from Field class.
 * @author wojtekrafalo
 * @version 1.0
 * @since 1.0
 */
public class Rabbit implements Runnable {

    private Field field;

    /**
     * Constructor sets Field class with all variables.
     * @param field object of a field class.
     */
    Rabbit(Field field) {
        this.field = field;
    }

    /**
     * Method inherited from Runnable. Runs a Thread of Rabbit and provides its action.
     */
    @Override
    public void run() {
        while (!Thread.interrupted() && field.getiRabbits()>0) {
            try {
                field.RabbitIsWaiting();
                field.RabbitIsMoving();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
