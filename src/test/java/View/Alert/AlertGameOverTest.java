package View.Alert;

import com.sun.javafx.PlatformUtil;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AlertGameOverTest {

    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_RESET = "\u001B[0m";
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private AlertGameOver alertGameOver = new AlertGameOver();

    /*needed to run the test according to the OS*/
    private boolean isWindows = PlatformUtil.isWindows();
    private String specialCharacter = this.isWindows ? "\r" : "";

    @Test
    public void gameOverAlertTest() {
        System.setOut(new PrintStream(outContent));

        alertGameOver.gameOverAlert();

        String expected = new String(Character.toChars(0x1F389)) + ANSI_RED + " Game Over  " + ANSI_RESET +
                new String(Character.toChars(0x1F389)) + specialCharacter + "\n" + ANSI_RED +
                "The board is full: game ending with no winner." +  ANSI_RESET + specialCharacter + "\n";
        assertThat(outContent.toString(), is(expected));
    }

    @Test
    public void gameOverAlertWinnerTest() {
        System.setOut(new PrintStream(outContent));
        String winner  =  "Player1";
        alertGameOver.gameOverAlert(winner);

        String expected = new String(Character.toChars(0x1F389)) + ANSI_RED + " Game Over  " + ANSI_RESET +
                new String(Character.toChars(0x1F389)) + specialCharacter + "\n" + ANSI_RED +
                "The winner is "+ winner +  ANSI_RESET + specialCharacter + "\n";

        assertThat(outContent.toString(), is(expected));
    }
}
