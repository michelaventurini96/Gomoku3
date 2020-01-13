package Model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


public class Opening {
    private Player player1;
    private Player player2;
    private String method;
    private int numMoves=0;
    private Boolean over;

    public Opening(Player p1, Player p2,String m){
        this.player1=p1;
        this.player2=p2;
        this.method=m;
        System.out.println(this.method);
        if(this.method.equals("Standard")) this.numMoves=2;
        else this.numMoves=3;
    }

    int getNummoves(){return this.numMoves;}

// factory!

    void calling(int c){
        switch (this.method){
            case "Standard":
                if(c==2) this.OpenStd();
                break;
            case "Swap":
                if(c==3) this.Swap();
                break;
            case "Swap2":
                if(c!=5) this.over=this.Swap2();
                else if(c==5 && !this.over) this.Swap2_1();
                break;
        }
    }

    private void CheckError(){

        if(!player1.CheckAllMoves(player2)){
            throw new Error("place stones in different places");
        }else{
            System.out.println("end of opening moves");
        }
    }

    public void CheckinError(Player p, Piece m){
        if(!p.CheckinMoves(m)){
            throw new Error(p.getName()+" place stones in different places");
        }
    }
/*
    Black can place anywhere, white secondly can place anywhere but on black spot.
 */
    private void OpenStd(){
        CheckError();
    }

    private Player GetBlack(){
        if(player1.getColor().get()==1) return player1;
        else return player2;
    }

    public Player GetWhite(){
        if(player1.getColor().get()==2) return player1;
        else return player2;
    }


    private void utilitySwap(){
        if(player1.equals(this.GetBlack())){
        player1.addPosition(player2.getPositions().get(0));
        player2.addPosition(player1.getPositions().get(0));
        player2.addPosition(player1.getPositions().get(1));
        player1.removePosition(1);
        player1.removePosition(0);
        player2.removePosition(0);
        player1.SetColor(2);
        player2.SetColor(1);
        }
        else {
            player2.addPosition(player1.getPositions().get(0));
            player1.addPosition(player2.getPositions().get(0));
            player1.addPosition(player2.getPositions().get(1));
            player2.removePosition(1);
            player2.removePosition(0);
            player1.removePosition(0);
            player2.SetColor(2);
            player1.SetColor(1);
        }
    }

    private void Swap(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Swap ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if ("Sì".equals(alert.getResult().getText())) {
            this.utilitySwap();
        }
        CheckError();
    }

    private Boolean Swap2(){
        //Boolean over;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Swap ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        switch (alert.getResult().getText()){
            case "Sì":
                this.utilitySwap();
                CheckError();
                break;
            case "No":
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to stay white?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                switch (alert.getResult().getText()){
                    case "Sì":
                        CheckError();
                        break;
                    case "No":
                        Alert alertColors = new Alert(Alert.AlertType.INFORMATION);
                        alertColors.setTitle("Swap2 - Opening");
                        alertColors.setHeaderText(null);
                        alertColors.setContentText("white player insert 2 more stones (1 black and 1 white)");
                        alertColors.showAndWait();
                        //over=Boolean.FALSE;
                        //return over;
                        return false;
                }
        }
        //over=Boolean.TRUE;
        //return over;
        return true;
    }

    public void utilitySwap2(){
        if(player1.equals(this.GetBlack())){
        player1.addPosition(player2.getPositions().get(0));
        player1.addPosition(player2.getPositions().get(1));
        player2.addPosition(player1.getPositions().get(0));
        player2.addPosition(player1.getPositions().get(1));
        player2.addPosition(player1.getPositions().get(2));
        player1.removePosition(2);
        player1.removePosition(1);
        player1.removePosition(0);
        player2.removePosition(1);
        player2.removePosition(0);
        player1.SetColor(2);
        player2.SetColor(1);
        }
        else {
            player2.addPosition(player1.getPositions().get(0));
            player2.addPosition(player1.getPositions().get(1));
            player1.addPosition(player2.getPositions().get(0));
            player1.addPosition(player2.getPositions().get(1));
            player1.addPosition(player2.getPositions().get(2));
            player2.removePosition(2);
            player2.removePosition(1);
            player2.removePosition(0);
            player1.removePosition(1);
            player1.removePosition(0);
            player2.SetColor(2);
            player1.SetColor(1);
        }
    }

     private void Swap2_1(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Black player you want to Swap ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
         if ("Sì".equals(alert.getResult().getText())) {
             this.utilitySwap2();
         }
        CheckError();
    }
}
