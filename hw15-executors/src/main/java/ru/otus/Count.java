package ru.otus;

public class Count {

    private int i;
    private String last;
    private Direction dir;


    public Count(String last, Direction dir, int i) {
        this.last = last;
        this.dir = dir;
        this.i = i;
    }

    public void changeCount(){
        if (this.getDir().equals(Direction.UP)) {
            if (this.getI() == 10) {
                this.setDir(Direction.DOWN);
                this.setI(9);
            } else {
                this.Inc_I();
            }
        } else {
            if (this.getDir().equals(Direction.DOWN)) {
                if (this.getI() == 1) {
                    this.setDir(Direction.UP);
                    this.setI(2);
                } else {
                    this.Dec_i();
                }
            }
        }

    }
    
    public void Inc_I(){
        i++;
    }

    public void Dec_i(){
        i--;
    }

    public int getI() {
        return i;
    }

    public String getLast() {
        return last;
    }

    public Direction getDir() {
        return dir;
    }

    public void setLast(String last){
        this.last = last;
    }

    public void setDir(Direction dir){
        this.dir=dir;
    }

    public void setI(int i){
        this.i=i;
    }

}
