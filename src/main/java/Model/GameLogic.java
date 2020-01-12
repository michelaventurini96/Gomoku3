package Model;

import javafx.scene.control.Alert;
import java.util.List;

public class GameLogic {
    private Board myBoard;

    //players of the board
    private int current_player;
    private int opposing_player;

    private GomokuGame game;
    public String gameName;
    String OpeningName;
   //list of all the pieces
    private List<Piece> pieceChunk;

    public static int N;

    public GameLogic(Board board,GomokuGame g) {
        this.myBoard = board;
        this.game=g;
        this.game.initGame();
        this.N=this.game.getN();
        this.gameName=this.game.GetName();
        this.OpeningName=this.game.getOp();
    }



    // public method that will try to place a piece in the given x (col ),y (row )coordinate
    public void placePiece(final int x, final int y) {
        if (this.getPiece(x, y) != 0)
            return;

        if (!this.isValidMove(x, y))
            return;

        this.myBoard.pieces[x][y].setPiece(this.current_player);
        this.InsertMove(x,y);
        this.swapPlayers(); //cambia il colore
        this.Print();
    }


    // public method that will try to unplace a piece in the given x (col ),y (row )coordinate
    public void UnplacePiece(final int x, final int y) {
        this.swapPlayers();
        this.myBoard.pieces[x][y].removePiece();
        this.RemoveMove(x,y);
    }

    //utility function to call specific game's opening rule.
    public void Opening(int c){
        this.game.OpeningRules(c);
    }

    public void Rules(){
        this.game.setRules();
    }

    public void OpeningRules(){
            Alert alertColors = new Alert(Alert.AlertType.INFORMATION);
//            alertColors.setWidth(150);
//            alertColors.setHeight(250);
            alertColors.setHeaderText(null);
            switch (this.game.getOp()) {
                case "Standard":
                    alertColors.setTitle("Standard Opening - Rules");
                    alertColors.setContentText("Black player starts and insert 1 stones followed by white player. Stones can be placed anywhere.");
                    break;
                case "Pro":
                    alertColors.setHeight(200);
                    alertColors.setTitle("Pro Opening - Rules");
                    alertColors.setContentText("Black player starts in the centre, followed by white player. Black player can place the second " +
                            "stone out of a 5x5 square from the centre.");
                    break;
                case "LongPro":
                    alertColors.setHeight(200);
                    alertColors.setTitle("PLongro Opening - Rules");
                    alertColors.setContentText("Black player starts in the centre, followed by white player. Black player can place the second " +
                            "stone out of a 7x7 square from the centre.");
                    break;
                case "Swap":
                    alertColors.setTitle("Swap Opening - Rules");
                    alertColors.setContentText("Black player places 3 stones: 2 black and 1 white. White player can decide to swap color " +
                            "or stay white.");
                    break;
                case "Swap2":
                    alertColors.setHeight(200);
                    alertColors.setTitle("Swap2 Opening - Rules");
                    alertColors.setContentText("Black player places 3 stones: 2 black and 1 white. White player can decide to swap color " +
                            "stay white, or place other 2 stones (1 black and 1 white) and let the black player decide the wanted color.");
                    break;
            }
            alertColors.showAndWait();
        };

    private void checkPiece(final int x, final int y, final int player) {
        if (!this.validCoords(x, y))
            return;
    }

    private boolean checkPosition(final int x, final int y, final int player) {
        if (!this.validCoords(x, y))
            return (false);
        int pieceType = this.getPiece(x, y);
        Piece piece = this.myBoard.pieces[x][y];
        if (pieceType == Board.EMPTY_SPACE)
            return (true);
        if (pieceType == player && !this.pieceChunk.contains(piece))
            this.pieceChunk.add(piece);
        return (false);
    }

    //Insert moves in private player's set.

    private void InsertMove( int x,  int y){
        Piece m=new Piece(this.current_player);
        m.setX(x);
        m.setY(y);
        if(this.current_player==this.game.getP1().getColor().get()){
            this.game.getP1().addPosition(m);
        }
        else{
            this.game.getP2().addPosition(m);
        }
    }

    private void RemoveMove(int x,  int y){
        Piece m=new Piece(this.current_player);
        m.setX(x);
        m.setY(y);
        if(this.current_player==this.game.getP1().getColor().get()){
            if(this.game.getP1().CheckinMoves(m)) this.game.getP1().removePosition(this.game.getP1().getPositions().size()-1);
        }
        else{
            if(this.game.getP2().CheckinMoves(m)) this.game.getP2().removePosition(this.game.getP2().getPositions().size()-1);
        }
        this.myBoard.pieces[x][y].setPiece(Board.EMPTY_SPACE);
    };

    // private method for swapping the players
    private void swapPlayers() {
       if (this.current_player== Board.WHITE_PLAYER) {
           this.current_player= Board.BLACK_PLAYER;
           this.opposing_player= Board.WHITE_PLAYER;
        }
        else {
           this.current_player=Board.WHITE_PLAYER;
           this.opposing_player= Board.BLACK_PLAYER;
        }
    }

    private void Print(){
        this.game.getP1().PrintPositions();
        this.game.getP2().PrintPositions();
    }
    // private method for getting a piece on the board. this will return the board
    // value unless we access an index that doesnt exist. this is to make the code
    // for determining reverse chains much easier
    private int getPiece(final int x, final int y) {
        if (this.validCoords(x, y))
            return (this.myBoard.pieces[x][y].getPiece());
        return (-1);
    }

    private boolean validCoords(final int x, final int y) {
        if ((x >= 0 && x < myBoard.board_size) && (y >= 0 && y < myBoard.board_size)) {
            return (true);
        }
        return (false);
    }

    // private method that will reset the renders
    private void resetRenders() {
        for (int i = 0; i < myBoard.board_size; ++i) {
            for (int j = 0; j < myBoard.board_size; ++j) {
                this.myBoard.pieces[i][j].setPiece(Board.EMPTY_SPACE);
            }
        }
    }

    public boolean isValidMove(int x, int y) {
        if (this.myBoard.pieces[x][y].getPiece() != Board.EMPTY_SPACE)
            return (false);
        return (true);
    }



}
