package sample;

import java.io.IOException;

public class Refresh implements Runnable {

    private long duration;
    private long startTime;

    private LobbyController lc = null;
    private BoardController bc = null;

    public Refresh(long duration, LobbyController lc){
        this.lc = lc;
        this.duration = duration;
    }

    public Refresh(long duration, BoardController bc){
        this.bc = bc;
        this.duration = duration;
    }

    @Override
    public void run() {
        startTime = System.nanoTime();

        while (System.nanoTime() - startTime < duration) {}
        // Timer has expired

        if (lc != null) {
            try {
                lc.challengeAccept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (bc != null) {
            try {
                bc.receiveMove();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        run();
    }
}
