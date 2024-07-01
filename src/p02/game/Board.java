package p02.game;

import p02.pres.TableModelConstructor;
import p02.pres.TrackView;
import p02.pres.SevenSegmentContainer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class Board
    extends KeyAdapter
    implements TickEventListener {

    private int[] gameboard;
    private boolean isRunning;
    private int lastObstacles;
    private int pointCounter;
    private int tickCounter;
    private int tickEventLimit;
    private TrackView trackView;
    private JTable carTable;
    private TableModelConstructor carTableModel;
    private List<TableModelConstructor> obstacleTableModels;
    private SevenSegmentContainer counter;

    public Board() {
        this.gameboard = new int[7];
        addStartingValuesToGameboard();
        this.isRunning = false;
        this.gameboard[0] = 2;
        this.lastObstacles = 0;
        this.pointCounter = 0;
        this.tickCounter = 0;
        this.tickEventLimit = 4;
        this.trackView = new TrackView();
        this.carTable = trackView.getCarTable();
        this.carTableModel = (TableModelConstructor) carTable.getModel();
        this.obstacleTableModels = trackView.getObstacleTableModels();
        this.obstacleTableModels = obstacleTableModels.reversed();
        this.counter = trackView.getCounter();

        trackView.addKeyListener(this);
        trackView.setFocusable(true);
        trackView.requestFocusInWindow();
    }

    private void addStartingValuesToGameboard() {
        for (int i = 0; i < gameboard.length; i++)
            gameboard[i] = 0;
    }

    private void startEvent() {
        isRunning = true;
        TickGenerator.getInstance().addEventListener(this);
        TickGenerator.getInstance().startTicking();
    }

    private void resetEvent() {
        isRunning = false;
        pointCounter = 0;
        for (int i = 0; i < this.gameboard.length; i++)
            gameboard[i] = 0;

        gameboard[0] = 2;
        TickGenerator.getInstance().stopEvent();
        updateCarPosition();
        counter.setZero();
        removeObstacles();
        showEndGameModal();
    }

    private void showEndGameModal() {
        JOptionPane.showMessageDialog(null, pointCounter == 999 ? "You win!" : "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateCarPosition() {
        String carPosInBin = Integer.toBinaryString(gameboard[0]);
        while (carPosInBin.length() < 3)
            carPosInBin = "0" + carPosInBin;

        int[] arrWithBinPos = new int[3];
        String[] stringToArr = carPosInBin.split("");
        for (int i = 0; i < 3; i++)
            arrWithBinPos[i] = Integer.parseInt(stringToArr[i]);

        carTableModel.updateData(arrWithBinPos);
    }

    private void removeObstacles() {
        for (TableModelConstructor table : obstacleTableModels)
            table.updateData(new int[]{0,0,0});
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> {
                if (gameboard[0] >= 1 && gameboard[0] < 4) {
                    gameboard[0] *= 2;
                    updateCarPosition();
                }
            }
            case KeyEvent.VK_D -> {
                if (gameboard[0] > 1 && gameboard[0] <= 4) {
                    gameboard[0] /= 2;
                    updateCarPosition();
                }
            }
            case  KeyEvent.VK_S -> {
                if (!isRunning)
                    startEvent();
            }
        }
    }

    private int drawObstacles() {
        Random ran = new Random();

        List<Integer> arrWithComplexRoadObstacles = new ArrayList<>();
        arrWithComplexRoadObstacles.add(6);
        arrWithComplexRoadObstacles.add(5);
        arrWithComplexRoadObstacles.add(3);

        List<Integer> arrWithSimpleRoadObstacles = new ArrayList<>();
        arrWithSimpleRoadObstacles.add(1);
        arrWithSimpleRoadObstacles.add(2);
        arrWithSimpleRoadObstacles.add(4);

        List<Integer> allPossiblyObstacles = new ArrayList<>();
        allPossiblyObstacles.add(1);
        allPossiblyObstacles.add(2);
        allPossiblyObstacles.add(3);
        allPossiblyObstacles.add(4);
        allPossiblyObstacles.add(5);
        allPossiblyObstacles.add(6);

        if ( lastObstacles == 0 ) {
            int randomNum = ran.nextInt(6);
            lastObstacles = allPossiblyObstacles.get(randomNum);

            return lastObstacles;
        }

        if ( lastObstacles == 6 || lastObstacles == 5 || lastObstacles == 3 ) {
            switch (lastObstacles) {
                case 6 -> {
                    arrWithSimpleRoadObstacles.remove(1);
                    arrWithSimpleRoadObstacles.remove(1);
                }
                case 5 -> {
                    arrWithSimpleRoadObstacles.remove(0);
                    arrWithSimpleRoadObstacles.remove(1);
                }
                case 3 -> {
                    arrWithSimpleRoadObstacles.remove(0);
                    arrWithSimpleRoadObstacles.remove(0);
                }
            }

            lastObstacles = arrWithSimpleRoadObstacles.get(0);

            return lastObstacles;
        }

        switch (lastObstacles) {
            case 1 -> {
                arrWithComplexRoadObstacles.remove(1);
                arrWithComplexRoadObstacles.remove(1);
            }
            case 2 -> {
                arrWithComplexRoadObstacles.remove(0);
                arrWithComplexRoadObstacles.remove(1);
            }
            case 4 -> {
                arrWithComplexRoadObstacles.remove(0);
                arrWithComplexRoadObstacles.remove(0);
            }
        }

        lastObstacles = arrWithComplexRoadObstacles.get(0);
        return lastObstacles;
    }

    public void incrementPointCounter() {
        pointCounter += 1;
        counter.setNumber(pointCounter);
        checkPointsLimit();
    }

    private void checkCollision() {
        if ((gameboard[0] & gameboard[1]) > 0) {
            resetEvent();
        } else {
            incrementPointCounter();
        }
    }

    public void checkPointsLimit() {
        if (pointCounter == 999) {
            resetEvent();
        }
    }

    private void setTickEvent() {
        if (pointCounter == 0)
            this.tickEventLimit = 4;

        if (pointCounter > 0 && pointCounter < 10)
            this.tickEventLimit = 3;

        if (pointCounter >= 10)
            this.tickEventLimit = 2;
    }

    private void changeObstaclePositionOnArray(int newObstacle) {
        for (int i = 1; i < gameboard.length - 1; i++) {
            gameboard[i] = gameboard[i + 1];
        }
        gameboard[gameboard.length - 1] = newObstacle;
        updateObstaclePositionOnTrack();
    }

    private void updateObstaclePositionOnTrack() {
        for (int i = 1; i < gameboard.length; i++ ) {
            String posInBin = Integer.toBinaryString(gameboard[i]);
            while (posInBin.length() < 3)
                posInBin = "0" + posInBin;

            String[] arrWithStrBin = posInBin.split("");
            int[] arrWithBinNums = new int[3];
            for (int j = 0; j < arrWithStrBin.length; j++)
                arrWithBinNums[j] = Integer.parseInt(arrWithStrBin[j]);

            obstacleTableModels.get(i - 1).updateData(arrWithBinNums);
        }
    }

    @Override
    public void handleTickEvent(TickEvent e) {
        if (tickCounter == tickEventLimit) {
            changeObstaclePositionOnArray(drawObstacles());
            tickCounter = 0;
        } else {
            changeObstaclePositionOnArray(0);
            lastObstacles = 0;
        }
        tickCounter += 1;
        setTickEvent();
        checkCollision();
    }
}
