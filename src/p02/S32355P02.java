package p02;

import p02.game.Board;

import javax.swing.*;

public class S32355P02 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Board::new);
    }
}
