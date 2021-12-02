package minestarter;

import mine.Mine;
import mine.Mine32_21;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MineStarter extends JFrame {
    private final int y_expend = 39;
    private final int x_expend = 16;
    private final JLabel rows = new JLabel("rows");
    private final JLabel columns = new JLabel("columns");
    private final JLabel widthOfMine = new JLabel("mine_width");
    private final JLabel heightOfMine = new JLabel("mine_height");
    private final JTextField rowsField = new JTextField("12");
    private final JTextField columnsField = new JTextField("21");
    private final JTextField widthOfMineField = new JTextField("60");
    private final JTextField heightOfMineField = new JTextField("60");
    private final int designw = 500;
    private final int designh = 400;
    private final int normalH = 40;
    private final int normalW = 100;
    private final JButton start = new JButton("start");
    public MineStarter(){
        super("选择游戏参数");
        setUi();
    }

    private void setUi() {
        Dimension d = getToolkit().getScreenSize();
        int ww = (int) d.getWidth();
        int hh = (int) d.getHeight();
        setBounds((ww/2-(designw+x_expend/2)),(hh/2-(designh+y_expend/2)),designw+x_expend,designh+y_expend);
        setLayout(null);
        int left = 30;
        int left2 = 130;
        int left3 = 230;
        int left4 = 330;
        int top = 30;
        int top2 = top+normalH;
        columns.setBounds(left,top,normalW,normalH);
        columnsField.setBounds(left2,top,normalW,normalH);
        rows.setBounds(left3,top,normalW,normalH);
        rowsField.setBounds(left4,top,normalW,normalH);
        widthOfMine.setBounds(left,top2,normalW,normalH);
        widthOfMineField.setBounds(left2,top2,normalW,normalH);
        heightOfMine.setBounds(left3,top2,normalW,normalH);
        heightOfMineField.setBounds(left4,top2,normalW,normalH);
        this.getContentPane().add(rows);
        this.getContentPane().add(rowsField);
        this.getContentPane().add(columns);
        this.getContentPane().add(columnsField);
        this.getContentPane().add(widthOfMine);
        this.getContentPane().add(widthOfMineField);
        this.getContentPane().add(heightOfMine);
        this.getContentPane().add(heightOfMineField);
        start.setBounds(designw/2-50,designh/2-25,100,50);
        this.getContentPane().add(start);
        start.addActionListener(e->{
            int x = Integer.parseInt(columnsField.getText());
            int y = Integer.parseInt(rowsField.getText());
            int w = Integer.parseInt(widthOfMineField.getText());
            int h = Integer.parseInt(heightOfMineField.getText());
            Mine mine = new Mine32_21(x,y,w,h);
            mine.setVisible(true);
            MineStarter.this.setVisible(false);
        });

        widthOfMineField.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode()==0){
                    heightOfMineField.setText(widthOfMineField.getText());
                    widthOfMineField.setText(heightOfMineField.getText());
                }else{
                    System.out.println(e.getKeyCode());
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        heightOfMineField.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode()==0){
                    widthOfMineField.setText(heightOfMineField.getText());
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }
}
