package View.Alert;

public class AlertInvalidMove extends AlertGenerator {
    public  void invalidMoveAlert(String error){
        System.out.println(ANSI_RED+"ERROR -Invalid Move"+ANSI_RESET);
    }
}
