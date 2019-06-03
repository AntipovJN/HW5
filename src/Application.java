
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

public class Application {

    public static void main(String[] args) {
        configureLogging();
        Logger log = Logger.getLogger("org.stepic.java.logging.ClassA");
        log.warning("warning, logging started");
        Exception e = new MessagingException("Some exception");
        log.log(Level.SEVERE, "Some information", e );
        log = Logger.getLogger("org.stepic.java.logging.ClassB");
        log.fine("fine log");
        log.warning("warning log");
    }

    private static void configureLogging() {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new XMLFormatter());

        Logger loggerA = Logger.getLogger("org.stepic.java.logging.ClassA");
        loggerA.setLevel(Level.ALL);

        Logger loggerB = Logger.getLogger("org.stepic.java.logging.ClassB");
        loggerB.setLevel(Level.WARNING);

        Logger loggerStepic = Logger.getLogger("org.stepic.java");
        loggerStepic.setUseParentHandlers(false);
        loggerStepic.addHandler(handler);

    }

    public static void moveRobot(RobotConnectionManager robotConnectionManager, int toX, int toY) {
        boolean getConnection = false;
        RobotConnection connection = null;
        for (int i = 0; i < 3 && !getConnection; i++) {
            try {
                connection = robotConnectionManager.getConnection();
                connection.moveRobotTo(toX, toY);
                getConnection = true;
            } catch (RobotConnectionException connectException) {
                if (i == 2 && !getConnection) {
                    throw new RobotConnectionException("can't connect to the robot");
                }
            } catch (Throwable ex) {
                return;
            } finally {
                try {
                    connection.close();
                } catch (Throwable t) {
                }
            }
        }
    }
}
