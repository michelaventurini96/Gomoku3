package View.Alert;

import Model.GomokuGame.GomokuType;
import Model.Rules.Opening.OpeningType;
import com.sun.javafx.PlatformUtil;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AlertLoginTest {

    private AlertLogin alertLogin = new AlertLogin();
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final String ANSI_RESET = "\u001B[0m";

    /*needed to run the test according to the OS*/
    private boolean isWindows = PlatformUtil.isWindows();
    private String specialCharacter = this.isWindows ? "\r" : "";

    @Test
    public void alertLoginTest() {
        AlertLogin alertLoginTest = new AlertLogin();
        assertThat(alertLoginTest.getGomokuTypes().get(0), is(GomokuType.Standard));
        assertThat(alertLoginTest.getGomokuTypes().get(1), is(GomokuType.Omok));
        assertThat(alertLoginTest.getGomokuTypes().get(2), is(GomokuType.Freestyle));

        assertThat(alertLoginTest.getOpeningTypes().get(0), is(OpeningType.Standard));
        assertThat(alertLoginTest.getOpeningTypes().get(1), is(OpeningType.Swap));
        assertThat(alertLoginTest.getOpeningTypes().get(2), is(OpeningType.Swap2));
    }

    @Test
    public void loginAlertTest() {
        System.setOut(new PrintStream(outContent));

        this.alertLogin.loginAlert();

        String ANSI_RED = "\u001B[31m";
        String expected = ANSI_RED + "invalid selected type! " + ANSI_RESET +
                new String(Character.toChars(0x1F6AB)) + specialCharacter + "\n";

        assertThat(outContent.toString(), is(expected));
    }

    @Test
    public void welcomePrintTest() {
        System.setOut(new PrintStream(outContent));

        this.alertLogin.welcomePrint();

        String ANSI_PURPLE = "\u001B[35m";
        String STAR = "*".repeat(17);
        String expected = ANSI_PURPLE + STAR +" WELCOME IN GOMOKU "+ STAR + ANSI_RESET + specialCharacter +
                "\n" + ANSI_PURPLE + STAR +"   Game Setting  "+ STAR + ANSI_RESET + specialCharacter + "\n";

        assertThat(outContent.toString(), is(expected));
    }

    @Test
    public void setBlackPlayerTest() {
        System.setOut(new PrintStream(outContent));

        this.alertLogin.setBlackPlayer();

        String expected = "Black Player Name: " + specialCharacter + "\n";

        assertThat(outContent.toString(), is(expected));
    }

    @Test
    public void setWhitePlayerTest() {
        System.setOut(new PrintStream(outContent));

        this.alertLogin.setWhitePlayer();

        String expected = "White Player Name: " + specialCharacter + "\n" ;

        assertThat(outContent.toString(), is(expected));
    }

    @Test
    public void setOpeningTest() {
        System.setOut(new PrintStream(outContent));

        this.alertLogin.setOpening();
        String expected = "Choose your favorite Opening Rule between: " + specialCharacter + "\nStandard"  + specialCharacter +
                "\nSwap"  + specialCharacter + "\nSwap2" + specialCharacter + "\n";

        assertThat(outContent.toString(), is(expected));
    }

    @Test
    public void setGame() {
        System.setOut(new PrintStream(outContent));
        this.alertLogin.setGame();

        String expected = "Choose your favorite version of Gomoku between: " + specialCharacter +"\nStandard" +
        specialCharacter +"\nOmok" + specialCharacter + "\nFreestyle"+specialCharacter + "\n";

        assertThat(outContent.toString() , is(expected));
    }


}
