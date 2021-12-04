package mine;

import javax.swing.*;
import java.awt.*;

public class Picker extends JLabel {
    public volatile boolean isSelected;
    public Picker(String message,boolean isSelected){
        super(message,JLabel.CENTER);
        this.isSelected = isSelected;
        setOpaque(true);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        int width = Picker.this.getWidth();
        int height = Picker.this.getHeight();
        g2.setStroke(new BasicStroke(2,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
        if(isSelected){
            g2.drawRect(0,0,width,height);
            setBackground(Color.LIGHT_GRAY);
        }else{
            setBackground(Color.white);
        }
    }

}
