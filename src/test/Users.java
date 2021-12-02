package test;

import item.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class Users extends JFrame{

    public Users(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,300);
        setLayout(null);
        Jl jl = new Jl(new ImageIcon(Objects.requireNonNull(Users.class.getResource("img.png"))));
        jl.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {
                jl.isPacked = false;
                jl.setText("hello world");
                jl.setIcon(new ImageIcon(Objects.requireNonNull(Users.class.getResource("img_1.png"))));
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        jl.setBounds(0,0,100,100);
        this.getContentPane().add(jl);
    }
    public static void main(String[] args){
        Users u = new Users();
        u.setVisible(true);
    }


}
class Jl extends JLabel{
    public volatile boolean isPacked = true;

    public Jl(ImageIcon imageIcon) {
        super(imageIcon);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (!isPacked) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.BLUE);
            int width = Jl.this.getWidth();
            int height = Jl.this.getHeight();
            g2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
            int size = g2.getFont().getSize();
            g2.drawString(
                    Jl.this.getText(),
                    calculateX(calLength(Jl.this.getText()), size, width),
                            calculateY(size, height));
        } else {
            super.paint(g);
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
