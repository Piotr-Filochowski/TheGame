package main_package;

import java.util.ArrayList;

public class Producer implements Runnable {

    Magazine magazine;

    public Producer( Magazine magazine) {
        this.magazine = magazine;
    }

    void produce() throws InterruptedException {
        Thread.sleep(30);
        synchronized (magazine) {
                while (magazine.getMagazine().size() >= magazine.getMaxSize()) {

                    magazine.wait();
                }
                magazine.getMagazine().add(new Bullet());
                if (magazine.getMagazine().size() == 1) magazine.notifyAll();
            }
    }

    @Override
    public void run() {
        while(true){
            try {
                produce();
            } catch (InterruptedException e) {

            }
        }
    }
}
