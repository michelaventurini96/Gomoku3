package test.java.Controller;
import Controller.AlertControllerInterface;
import Controller.AlertController;
import Model.Rules.Opening.OpeningType;
import com.sun.javafx.PlatformUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class AlertControllerTest {
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_PURPLE = "\u001B[35m";
    final String ANSI_PURPLE1 = "\u001B[95m";
    AlertControllerInterface alertinterface= new AlertController();

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void ConstructorTest(){
        assertEquals("class Controller.AlertController",this.alertinterface.getClass().toString());
    };

    @Test
    public void swapAlertTest(){
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("YES".getBytes());
        System.setIn(in);
        assertEquals("YES",this.alertinterface.swapAlert());
        System.setIn(sysInBackup);
    };

    @Test
    public void swapBlackTest(){
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("NO".getBytes());
        System.setIn(in);
        assertEquals("NO",this.alertinterface.swapBlack());
        System.setIn(sysInBackup);
    };

    @Test
    public void swap2AlertTest(){
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("2".getBytes());
        System.setIn(in);
        assertEquals("2",this.alertinterface.swap2Alert());
        System.setIn(sysInBackup);
    };

    @Test(expected = NoSuchElementException.class)
    public void swapAlertErrorTest() {
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("g".getBytes());
        System.setIn(in);
        this.alertinterface.swap2Alert();
        System.setIn(sysInBackup);
    };

    @Test
    public void callInvalidMoveErrorTest(){
        new AlertController().callInvalidMoveError();
        String space="";
        if(PlatformUtil.isWindows()){space+="\r";};
        assertEquals(ANSI_RED+"ERROR -Invalid Move "+ANSI_RESET+new String(Character.toChars(0x1F6AB))+space+"\n",outContent.toString());
    };

    @Test
    public void callGameOverAlertTest(){
        new AlertController().callGameOverAlert();
        String space="";
        if(PlatformUtil.isWindows()){space+="\r";};
        assertEquals(new String(Character.toChars(0x1F389))+ANSI_RED + " Game Over  "+ ANSI_RESET+new String(Character.toChars(0x1F389))+space+"\n"+ANSI_RED+"The board is full: game ended with no winner"+ ANSI_RESET+space+"\n",outContent.toString());
    };

    @Test
    public void callGameOverAlertTest1() {
        new AlertController().callGameOverAlert("giorgio");
        String space="";
        if(PlatformUtil.isWindows()){space+="\r";};
        assertEquals(new String(Character.toChars(0x1F389))+ANSI_RED + " Game Over  "+ ANSI_RESET+new String(Character.toChars(0x1F389))
                + space+"\n" + ANSI_RED + "The winner is giorgio" + ANSI_RESET + space+"\n", outContent.toString());
    };

    @Test
    public void callGetAlertOpeningTest(){
        String space="";
        if(PlatformUtil.isWindows()){space+="\r";};
        new AlertController().callGetAlertOpening(OpeningType.Swap);
        assertEquals(ANSI_PURPLE + "* SWAP opening - Rules *"+space+"\n" +
                ANSI_PURPLE1 + "BLACK player places 3 stones: 2 black and 1 white."+space+"\n" +
                "then WHITE player can decide to swap color or stay white" +
                ANSI_RESET+space+"\n",outContent.toString());
    };
}