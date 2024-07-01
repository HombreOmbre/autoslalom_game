package p02.pres;

import javax.swing.*;
import java.awt.*;

public class SevenSegmentContainer extends JPanel {

    private SevenSegmentDigit[] digits;

    public SevenSegmentContainer() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 5,0));
        this.setPreferredSize(new Dimension(100,60));
        setOpaque(false);

        this.digits = new SevenSegmentDigit[3];
        for (int i = 0; i < 3; i++) {
            digits[i] = new SevenSegmentDigit(0);
            add(digits[i]);
        }
    }

    public void setNumber(int number) {
        String[] intToStr = Integer.toString(number).split("");
        if (intToStr.length == 1) {
            digits[2].setNumber(Integer.parseInt(intToStr[0]));
        }

        if (intToStr.length == 2) {
            digits[2].setNumber(Integer.parseInt(intToStr[1]));
            digits[1].setNumber(Integer.parseInt(intToStr[0]));
        }

        if (intToStr.length == 3) {
            digits[2].setNumber(Integer.parseInt(intToStr[2]));
            digits[1].setNumber(Integer.parseInt(intToStr[1]));
            digits[0].setNumber(Integer.parseInt(intToStr[0]));
        }
    }

    public void setZero() {
        for (SevenSegmentDigit digit : digits)
            digit.setNumber(0);
    }
}
