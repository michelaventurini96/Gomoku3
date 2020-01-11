package Model;

import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

public class Board extends Pane {

    private GameLogic gameLogic;
    private Rectangle background; //rectangle for the background of the board
    private Line[] horizontal; //array for horizontal lines
    private Line[] vertical; //array for vertical lines
    private Translate[] horizontal_t; //array holding translate obj for the horizontal grid lines
    private Translate[] vertical_t; //array holding translate obj for the vertical grid lines
    Piece[][] pieces; // matrix for the internal representation of the board and the pieces that are in place

    /* needed when resizing the board */
    private double cell_width;
    private double cell_height;

    /* offset to center the board in the window */
    private double start_x;
    private double start_y;

    /* size of the grid */
    private int line_number;
    int board_size;

    private static int APPLICATION_BORDER = 50; //border between the grid and the window
    private static Color BACKGROUND_COLOR = Color.PINK;

    static RadialGradient WHITE_COLOR = new RadialGradient(0.5, 0.5, 0, 0, 1.5, true, CycleMethod.REFLECT, new Stop(0, Color.WHITE), new Stop(1, Color.GREY));
    static RadialGradient BLACK_COLOR = new RadialGradient(0.5, 0.5, 0, 0, 1.5, true, CycleMethod.REFLECT, new Stop(0, Color.DARKSLATEGREY), new Stop(1, Color.BLACK));

    private static double PIECE_SIZE = 0.70; //piece dimension

    static int EMPTY_SPACE = 0;
    static int BLACK_PLAYER = 1;
    static int WHITE_PLAYER = 2;

    static int APPLICATION_WIDTH = 600;
    static int APPLICATION_HEIGHT = 600;

    public static int N; //to store the N of the gamelogic

    public Board(int inputSize,GomokuGame game) {
        this.line_number = inputSize;
        this.board_size = this.line_number + 1;
        this.pieces = new Piece[this.board_size][this.board_size];
        this.horizontal = new Line[this.board_size + 1];
        this.vertical = new Line[this.board_size + 1];
        this.horizontal_t = new Translate[this.board_size + 1];
        this.vertical_t = new Translate[this.board_size + 1];

        this.initialiseLinesBackground();
        this.initialiseRender();

        this.gameLogic = new GameLogic(this,game);
        N = GameLogic.N;
    }

    public int InitialMove(){
        this.gameLogic.OpeningRules();
        if(this.gameLogic.OpeningName.equals("Pro") || this.gameLogic.OpeningName.equals("LongPro")){

            if(this.board_size==16) this.gameLogic.placePiece(7,7);
            else this.gameLogic.placePiece(9,9);
            return 1;
        }
        else return 0;
    }

    // function that allows the opening functions to work on the board.
    public int getOpgame(final double x, final double y,int c){
        this.placePiece(x,y);
        int i=0;

        try {
            this.gameLogic.Opening(c);
        }
        catch (Error e){
            Alert alertColors = new Alert(Alert.AlertType.ERROR);
            alertColors.setTitle("ERROR - Opening");
            alertColors.setHeaderText(null);
            alertColors.setContentText(e.toString());
            alertColors.showAndWait();
            i=1;
            this.UnplacePiece(x,y);
        }

        return c-i;
    }

    public void getIngame(final double x, final double y){
        this.placePiece(x,y);
        try {
            this.gameLogic.Rules();
        }
        catch (Error e){
            Alert alertColors = new Alert(Alert.AlertType.ERROR);
            alertColors.setTitle("ERROR -Invalid Move");
            alertColors.setHeaderText(null);
            alertColors.setContentText(e.toString());
            alertColors.showAndWait();
            this.UnplacePiece(x,y);   //tolgo  l'ultima pedina inserita
        }
    };

    public void reset() { this.gameLogic.resetGame(); }

    // overridden version of the resize method to give the board the correct size
    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        double newWidth = width - APPLICATION_BORDER;
        double newHeight = height - APPLICATION_BORDER;

        if (width > height) newWidth = newHeight;
        else newHeight = newWidth;

        this.cell_width = newWidth / this.line_number;
        this.cell_height = newHeight / this.line_number;

        this.start_x = (width / 2) - (newWidth / 2);
        this.start_y = (height / 2) - (newHeight / 2);

        this.getChildren().remove(this.background);
        this.background = new Rectangle(width, height);
        this.background.setFill(BACKGROUND_COLOR);
        this.getChildren().add(0, this.background);

        this.horizontalResizeRelocate(newWidth);
        this.verticalResizeRelocate(newHeight);

        this.pieceResizeRelocate();
    }

    public void placePiece(final double x, final double y) {
        int cellX = (int)((x - this.start_x + (this.cell_width / 2.0)) / this.cell_width);
        int cellY = (int)((y - this.start_y + (this.cell_height / 2.0)) / this.cell_height);
        System.out.println(cellX);
        System.out.println(cellY);
        this.gameLogic.placePiece(cellX, cellY);
    }

    private void UnplacePiece(final double x, final double y) {
        int cellX = (int)((x - this.start_x + (this.cell_width / 2.0)) / this.cell_width);
        int cellY = (int)((y - this.start_y + (this.cell_height / 2.0)) / this.cell_height);
        //System.out.println(cellX);
        //System.out.println(cellY);
        this.gameLogic.UnplacePiece(cellX, cellY);
    }

    // private method that will initialise the background and the lines
    private void initialiseLinesBackground() {
        this.background = new Rectangle(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        this.background.setFill(BACKGROUND_COLOR);
        this.getChildren().add(this.background);

        for (int i = 0; i < this.line_number + 1; ++i) {
            this.horizontal[i] = new Line();

            this.horizontal[i].setStartX(0);
            this.horizontal[i].setStartY(0);
            this.horizontal[i].setEndY(0);

            this.horizontal_t[i] = new Translate(0, 0);
            this.horizontal[i].getTransforms().add(this.horizontal_t[i]);

            this.getChildren().add(this.horizontal[i]);
        }

        for (int i = 0; i < this.line_number + 1; ++i) {
            this.vertical[i] = new Line();

            this.vertical[i].setStartX(0);
            this.vertical[i].setEndX(0);
            this.vertical[i].setStartY(0);

            this.vertical_t[i] = new Translate(0, 0);
            this.vertical[i].getTransforms().add(this.vertical_t[i]);

            this.getChildren().add(this.vertical[i]);
        }
    }

    // private method for resizing and relocating the horizontal lines
    private void horizontalResizeRelocate(final double width) {
        for (int i = 0; i < this.line_number + 1; ++i) {
            this.horizontal[i].setStartX(this.start_x);
            this.horizontal[i].setEndX(this.start_x + width);
            this.horizontal_t[i].setY(this.start_y + this.cell_height * i);
        }
    }

    // private method for resizing and relocating the vertical lines
    private void verticalResizeRelocate(final double height) {
        for (int i = 0; i < this.line_number + 1; ++i) {
            this.vertical[i].setStartY(this.start_y);
            this.vertical[i].setEndY(this.start_y + height);
            this.vertical_t[i].setX(this.start_x + this.cell_width * i);
        }
    }

    // private method for resizing and relocating all the pieces
    private void pieceResizeRelocate() {
        double cellX = this.cell_width * PIECE_SIZE;
        double cellY = this.cell_height * PIECE_SIZE;
        double offsetX = this.cell_width * ((1 - PIECE_SIZE) / 2);
        double offsetY = this.cell_height * ((1 - PIECE_SIZE) / 2);
        for (int i = 0; i < this.board_size; ++i) {
            for (int j = 0; j < this.board_size; ++j) {
                this.pieces[i][j].resize(cellX, cellY);
                this.pieces[i][j].relocate(this.start_x + i * this.cell_width + offsetX - this.cell_width / 2.0, this.start_y + j * this.cell_height + offsetY - this.cell_height / 2.0);
            }
        }
    }

    // private method that will initialise everything in the render array
    private void initialiseRender() {

        for (int i = 0; i < this.board_size; ++i) {
            for (int j = 0; j < this.board_size; ++j) {
                this.pieces[i][j] = new Piece(EMPTY_SPACE);
                this.pieces[i][j].setX(i);
                this.pieces[i][j].setY(j);
            }
            this.getChildren().addAll(this.pieces[i]);
        }
    }
}
