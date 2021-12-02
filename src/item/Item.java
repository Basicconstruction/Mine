package item;

import javax.swing.*;
import java.awt.*;

public class Item extends JLabel {
    private String message="";
    private volatile boolean isBoom = false;
    public final int x;
    public volatile boolean isPacked = true;

    public final int y;

    public Item(int x, int y) {
        super();
        setOpaque(true);
        setBackground(Color.LIGHT_GRAY);
        this.x = x;
        this.y = y;
    }

    public void setIsBoom(boolean isBoom) {
        this.isBoom = isBoom;
    }

    public boolean getIsBoom() {
        return isBoom;
    }

    public void justMessage(String text) {
        this.message = text;
    }

    public void setMessage(String text) {
        super.setText("");
        this.message = text;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        int width = Item.this.getWidth();
        int height = Item.this.getHeight();
        g2.setStroke(new BasicStroke(2,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
        g2.drawRect(0,0,width,height);

        if (!isPacked) {
            if(!isBoom){
                g2.setColor(Color.BLUE);
                g2.setFont(new Font("微软雅黑", Font.PLAIN, width/4));
                int size = g2.getFont().getSize();
                g2.drawString(
                        Item.this.getMessage(),
                        calculateX(calLength(Item.this.getMessage()), size, width),
                        calculateY(size, height));
            }
        }
    }

    public int calculateX(int length, int size, int width) {
        int totalX = length * size / 2;
        return (width / 2 - totalX / 2);
    }

    public int calculateY(int size, int height) {
        return height / 2 + size / 2;
    }

    public int calLength(String str) {
        int length = 0;
        for (char c : str.toCharArray()) {
            if (isAscii(c)) {
                length++;
            } else {
                length += 2;
            }
        }
        return length;
    }

    public boolean isAscii(char c) {
        return c < 256;
    }
}
