package Controller;

import Model.*;
import Model.GomokuGame.GomokuFactory;
import Model.GomokuGame.GomokuGame;
import Model.GomokuGame.GomokuType;
import View.Alert.*;
import Model.Rules.Opening.OpeningType;
import View.BoardView;
import View.ControlSkin;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;

public class BoardController extends Control {

    private BoardView boardView;
    private GamePlay gamePlay;
    private final StackPane mainLayout;
    private int cellX = 0;
    private int cellY = 0;

    BoardController(BlackPlayer blackPlayer, WhitePlayer whitePlayer, GomokuType gomokuType, OpeningType openingType) {
        GomokuGame gomokuGame = GomokuFactory.getGame(gomokuType);
        gomokuGame.setPlayers(blackPlayer, whitePlayer);
        this.boardView = new BoardView(gomokuGame.getGridSize());
        this.gamePlay = new GamePlay(gomokuGame, openingType);
        this.setSkin(new ControlSkin(this));
        this.getChildren().add(this.boardView);
        this.mainLayout = new StackPane();
        this.mainLayout.getChildren().add(this);
    }

    BoardView getBoardView(){
        return this.boardView;
    }

    void clickEventHandler() throws InvocationTargetException, IllegalAccessException {
        AlertOpening.getAlertOpening(gamePlay.getGame().getOpeningRules().getOpeningType());
        this.setOnMouseClicked((event) -> {
            if(placePiece(event.getX(), event.getY())) {
                //if (this.numMovesDone() == gamePlay.getNumMovesOpening() || this.numMovesDone() == 5) {
                startOpening();
               // }
                startGame(event.getX(), event.getY());
            }
        });
    }

    private int numMovesDone(){
        return this.gamePlay.getGame().getOpeningRules().getBlackPlayer().listSize() + this.gamePlay.getGame().getOpeningRules().getWhitePlayer().listSize();
    }

    private void coordinateSet(final double x, final double y ){
        this.cellX = (int)((x - this.boardView.getGrid().getStartX() + (this.boardView.getGrid().getCellWidth() / 2.0)) / this.boardView.getGrid().getCellWidth());
        this.cellY = (int)((y - this.boardView.getGrid().getStartY() + (this.boardView.getGrid().getCellHeight() / 2.0)) / this.boardView.getGrid().getCellHeight());
    }

    private boolean placePiece(final double x, final double y) {
        this.coordinateSet(x,y);
        if(this.gamePlay.isValidMove(this.cellX,this.cellY) && !this.gamePlay.isOutOfBound(this.cellX, this.cellY) ){
            this.boardView.setPiece(this.cellX, this.cellY, this.gamePlay.getCurrentPlayer().getColor());
            this.gamePlay.placePiece(this.cellX, this.cellY);

            if(this.gamePlay.checkFullBoard())
                this.gameOver();
            if(!this.gamePlay.checkWinningMove().isEmpty() ){
                this.gameOver(this.gamePlay.checkWinningMove());
            }
            this.gamePlay.changeTurn();
            return true;
        }
        return false;
    }

    private void displacePiece(final double x, final double y) {
        this.coordinateSet(x,y);
        this.gamePlay.displacePiece(this.cellX, this.cellY);
        this.boardView.removePiece(this.cellX, this.cellY);
        this.boardView.setPiece(this.cellX, this.cellY, PieceColor.EMPTY);
    }

    private void startOpening(){
        if (this.numMovesDone() == gamePlay.getNumMovesOpening() || this.numMovesDone() == 5) {
            this.gamePlay.getGame().getOpeningRules().callOpening();
        }
    }

    private void startGame(final double x, final double y){
        try {
            //this.placePiece(x,y);
            this.gamePlay.getGame().checkInvalidMoves();
        }
        catch (Error | Exception e){
            AlertInvalidMove.invalidMoveAlert(e.toString().substring(17));
            this.displacePiece(x,y);
        }
    }

    private void gameOver(String ... winner){
        Stage stage = (Stage) boardView.getScene().getWindow();
        String result = AlertGameOver.gameOverAlert(winner);
        if("OK".equals(result))
            stage.close();
    }

    void start(Stage primaryStage) {
        primaryStage.setTitle("GOMOKU GAME");
        primaryStage.setScene(new Scene(this.mainLayout, 600, 600));
        primaryStage.show();
    }
}

