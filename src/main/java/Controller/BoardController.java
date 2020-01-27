package Controller;

import Model.GamePlay;
import Model.GomokuGame.GomokuFactory;
import Model.GomokuGame.GomokuGame;
import Model.GomokuGame.GomokuType;
import Model.Piece;
import Model.Player;
import Model.Rules.Opening.OpeningType;
import View.Alert;
import View.BoardView;
import View.ControlSkin;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BoardController extends Control {

    private BoardView myView;
    private GamePlay myGame;
    private int clicksCount = 0;
    private final StackPane mainLayout;

     BoardController(Player p1, Player p2, GomokuType gomokuType, OpeningType openingType) {
         GomokuGame gomokuGame = GomokuFactory.getGame(gomokuType).orElseThrow(() -> new IllegalArgumentException("Invalid operator"));
         gomokuGame.setPlayers(p1, p2);
         this.myView = new BoardView(gomokuGame.getGridDim());
         this.myGame = new GamePlay(gomokuGame, openingType);
         this.setSkin(new ControlSkin(this));
         this.getChildren().add(this.myView);
         this.mainLayout = new StackPane();
         this.mainLayout.getChildren().add(this);
    }

    BoardView getMyView(){
        return myView;
    }

    void clickOpeningCounter(){
         Alert.openingRulesAlert(myGame.getGame().getOpeningRules().getOpeningType().name());
         this.setOnMouseClicked((event) -> {
             this.clicksCount++;
             this.setClickCount(event.getX(), event.getY());
             if(this.clicksCount == myGame.getNumMovesOpening() || this.clicksCount == 5)
                 startOpening(event.getX(), event.getY());
             startGame(event.getX(), event.getY());
         });
     }

    private void setClickCount(final double x, final double y ){
         int cellX = (int)((x - this.myView.startX + (this.myView.cellWidth / 2.0)) / this.myView.cellWidth);
         int cellY = (int)((y - this.myView.startY + (this.myView.cellHeight / 2.0)) / this.myView.cellHeight);
         if(!this.myGame.isValidMove(cellX, cellY)) this.clicksCount-=1;}

    private void placePiece(final double x, final double y) {
        int cellX = (int)((x - this.myView.startX + (this.myView.cellWidth / 2.0)) / this.myView.cellWidth);
        int cellY = (int)((y - this.myView.startY + (this.myView.cellHeight / 2.0)) / this.myView.cellHeight);

        if(this.myGame.isValidMove(cellX, cellY) && !this.myGame.isOutOfBound(cellX, cellY) ){
            this.myView.setPiece(cellX, cellY, this.myGame.getCurrentPlayer().getColor());
            this.myGame.placePiece(cellX, cellY);

            if(this.myGame.checkFullBoard())
                this.gameOver();
            if(!this.myGame.checkWinningMove().isEmpty() ){
                this.gameOver(this.myGame.checkWinningMove());
            }
            this.myGame.swapPlayers();
        }
    }

    private void displacePiece(final double x, final double y) {
        int cellX = (int)((x - this.myView.startX + (this.myView.cellWidth / 2.0)) / this.myView.cellWidth);
        int cellY = (int)((y - this.myView.startY + (this.myView.cellHeight / 2.0)) / this.myView.cellHeight);
        this.myGame.displacePiece(cellX, cellY);
        this.myView.removePiece(cellX, cellY);
        this.myView.setPiece(cellX, cellY, Piece.PieceType.EMPTY);
    }

    private void startOpening(final double x, final double y){
        this.placePiece(x,y);
        this.myGame.getGame().getOpeningRules().callOpening(this.clicksCount);
    }

    private void startGame(final double x, final double y){
        try {
            this.placePiece(x,y);
            this.myGame.getGame().checkInvalidMoves();
        }
        catch (Error | Exception e){
            Alert.invalidMoveAlert(e.toString());
            this.displacePiece(x,y);
        }
    }

    private void gameOver(String winner){
        Stage stage = (Stage) myView.getScene().getWindow();
        String result = View.Alert.gameOverAlert(winner);
        if("OK".equals(result))
            stage.close();
    }

    private void gameOver(){
        Stage stage = (Stage) myView.getScene().getWindow();
        String result = View.Alert.gameOverAlert();
        if("OK".equals(result))
            stage.close();
    }

    void start(Stage primaryStage) {
        primaryStage.setTitle("GOMOKU GAME");
        primaryStage.setScene(new Scene(this.mainLayout, 600, 600));
        primaryStage.show();
    }
}

