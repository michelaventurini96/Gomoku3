package Model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Player {
    private String name;
    private AtomicInteger color;
    private List<Pair<Integer, Integer>> positionsList = new ArrayList<>();


    public Player(String name,String color){
        this.name = name;
        if(color.equals("Black")) { this.color = new AtomicInteger(1);}
        else {this.color = new AtomicInteger(2);}
    }


    public void addPosition(Pair<Integer, Integer> position){
        positionsList.add(position);
    }

    public void removePosition(int i){
        positionsList.remove(positionsList.get(i));
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return this.color.get();
    }

    public String getColorName(){
        if (this.color.get() == 1 ) return "Black";
        else return "White";
    }

//    public void setName(String name){
//        this.name = name;
//    }

    public void SetColor(int i) {
        this.color.set(i);
    }

    void PrintPositions(){
        System.out.println("movements for player "+this.name+":");
        for(Pair pair : positionsList) {
            System.out.println(pair.getKey() +" "+ pair.getValue());
        }
    }

    public List<Pair<Integer, Integer>> getPositions(){
        return positionsList;
    }

    public boolean checkMove(Pair pair){

        boolean b=false;
        for(Pair p : this.getPositions()) {
            if (p.getKey()== pair.getKey() && p.getValue()==pair.getValue()) b=true;
        }
        return b;
    }

    public boolean CheckAllMoves(Player p){
        List<Pair<Integer, Integer>> intersection = new ArrayList<Pair<Integer, Integer>>(this.positionsList);
        intersection.retainAll(p.getPositions());
        return intersection.isEmpty();
    }

}
