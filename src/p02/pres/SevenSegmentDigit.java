package p02.pres;

import javax.swing.*;
import java.awt.*;

public class SevenSegmentDigit extends JPanel {

    private boolean[] segments;

    public SevenSegmentDigit(int val) {
        segments = new boolean[7];
        setNumber(val);
        setPreferredSize(new Dimension(30, 80));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);

        if (segments[0]) drawSegment(g, 2,0, 26, 3);
        if (segments[1]) drawSegment(g, 27, 4, 3, 36);
        if (segments[2]) drawSegment(g, 27, 43, 3, 36 );
        if (segments[3]) drawSegment(g, 2, 77, 26, 3);
        if (segments[4]) drawSegment(g,0, 43, 3, 35);
        if (segments[5]) drawSegment(g, 0, 4, 3, 35);
        if (segments[6]) drawSegment(g, 4, 37, 22, 3);
    }

    private void drawSegment(Graphics g, int x1, int y1, int x2, int y2) {
        g.fillRect(x1, y1, x2, y2);
    }

    public void setNumber(int number) {
        switch (number) {
            case 0:
                segments = new boolean[]{true, true, true, true, true, true, false};
                break;
            case 1:
                segments = new boolean[]{false, true, true, false, false, false, false};
                break;
            case 2:
                segments = new boolean[]{true, true, false, true, true, false, true};
                break;
            case 3:
                segments = new boolean[]{true, true, true, true, false, false, true};
                break;
            case 4:
                segments = new boolean[]{false, true, true, false, false, true, true};
                break;
            case 5:
                segments = new boolean[]{true, false, true, true, false, true, true};
                break;
            case 6:
                segments = new boolean[]{true, false, true, true, true, true, true};
                break;
            case 7:
                segments = new boolean[]{true, true, true, false, false, false, false};
                break;
            case 8:
                segments = new boolean[]{true, true, true, true, true, true, true};
                break;
            case 9:
                segments = new boolean[]{true, true, true, true, false, true, true};
                break;
        }

        repaint();
    }
}
