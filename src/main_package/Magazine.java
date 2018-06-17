package main_package;

import java.util.ArrayList;

public class Magazine {

    ArrayList<Bullet> magazine;
    int maxSize;

    public Magazine(ArrayList<Bullet> magazine, int maxSize) {
        this.magazine = magazine;
        this.maxSize = maxSize;
    }

    public ArrayList<Bullet> getMagazine() {
        return magazine;
    }

    public void setMagazine(ArrayList<Bullet> magazine) {
        this.magazine = magazine;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
