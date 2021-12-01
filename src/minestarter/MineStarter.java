package minestarter;

import mine.Mine;
import mine.Mine32_21;

import javax.swing.*;

public class MineStarter extends JFrame {
    public MineStarter(){
        super();
        Mine mine = new Mine32_21(9,9,60,60);
        mine.setVisible(true);
    }
}
