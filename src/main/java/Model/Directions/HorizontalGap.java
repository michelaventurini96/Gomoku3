package Model.Directions;

import Model.BoardLogic;
import Model.Piece;
import javafx.util.Pair;

import java.util.Set;

public class HorizontalGap extends Directions {
    @Override
    public boolean outOfGridCheck(int x, int y, int sign) {
        return !(isOutOfGrid(updateCoord(x,range4,sign),y) && isOutOfGrid(updateCoord(x,-range1,sign),y)) &&
                !(isOutOfGrid(updateCoord(x,range5,sign),y) && isOutOfGrid(updateCoord(x,-range1,sign),y)) &&
                !(isOutOfGrid(updateCoord(x,range4,sign),y) && super.isPieceIn(updateCoord(x,-range1,sign),y,"white")) &&
                !(super.isPieceIn(updateCoord(x,range4,sign),y,"white") && isOutOfGrid(updateCoord(x,-range1,sign),y));
    }


    @Override
    public boolean updateIn(int x, int y, int sign,String col) {
        return super.isPieceIn(updateCoord(x,range2,sign),y,col)  &&
                super.isPieceIn(updateCoord(x,range3,sign) , y,col);
    }

    @Override
    public boolean updateOut(int x, int y, int sign) {
        return isEmpty(updateCoord(x,range1,sign),y)&&
                isEmpty(updateCoord(x,-range1,sign),y) &&
                isEmpty(updateCoord(x,range4,sign),y);
    }

    @Override
    public void check(int x, int y, int sign, Set<Piece> pieceSet){
        int x1=x + sign * 2 ;
        int x2=x + sign*3;
        super.auxiliaryCheck(sign,x,y,x1, y,x2, y,pieceSet);
    }

    @Override
    public boolean consecutiveFivePiece(int x, int y, int sign,String col) {
        return false;
    }

    @Override
    public boolean FiveBoundaries(int x, int y, int sign, String col) {
        return false;
    }
}
