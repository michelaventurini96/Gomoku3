package View.Alert;

import Model.Rules.Opening.OpeningType;
import java.util.HashMap;
import java.util.Map;

public class AlertOpening extends AlertGenerator{

    public AlertOpening() {this.add();}

    String stdOpeningRulesAlert(){
        return ANSI_PURPLE + "* STANDARD opening - Rules * \r\n" +
                ANSI_PURPLE1 + "Black player starts and insert 1 stones followed by white player. \r\n" +
                "Stones can be placed anywhere." +
                ANSI_RESET;
    }

    String swapOpeningRulesAlert(){
        return ANSI_PURPLE + "* SWAP opening - Rules *\r\n" +
                ANSI_PURPLE1 + "BLACK player places 3 stones: 2 black and 1 white.\r\n" +
                "then WHITE player can decide to swap color or stay white" +
                ANSI_RESET;
    }

    String swap2OpeningRulesAlert(){
        return ANSI_PURPLE+"* SWAP2 opening - Rules *"+"\r\n"+ ANSI_PURPLE1+"BLACK player places 3 stones: 2 black and 1 white\r\n" +
                        "then WHITE player can decide to: " +
                        "- stay white,\r\n" +
                        "- swap color,\r\n" +
                        "- place 2 more stones (1 black and 1 white) and let the black player decide the wanted color";
    }


    private  Map<OpeningType, String> alertOpeningMap = new HashMap<>();

    private void add(){
        this.alertOpeningMap.put(OpeningType.Standard,this.stdOpeningRulesAlert());
        this.alertOpeningMap.put(OpeningType.Swap,this.swapOpeningRulesAlert());
        this.alertOpeningMap.put(OpeningType.Swap2,this.swap2OpeningRulesAlert());
    }

    public void getAlertOpening(OpeningType opening) {
        try {
            System.out.println(this.alertOpeningMap.get(opening)+"\r");
        }catch (NullPointerException e){
            System.out.println(e.getCause());
        }
    }

     Map<OpeningType, String> getAlertOpeningMap() {
        return alertOpeningMap;
    }


}
