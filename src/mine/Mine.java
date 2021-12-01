package mine;

import item.Item;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Mine extends JFrame {
    private final int y_expend = 39;
    private final int x_expend = 16;
    private final int designw;
    private final int designh;
    private final int minesw;
    private final int minesh;
    private final JPanel ui = new JPanel(null);
    private final JPanel mines;
    public Mine(int x,int y,int w,int h){
        super();
        setTitle("Mine");
        basicSettings();
        minesw = x*w;
        minesh = y*h;
        designw = minesw+20;
        designh = minesh+50;
        setLocation(200,100);
        mines = new JPanel(new GridLayout(y,x));
        setSize(x_expend+designw,y_expend+designh);
        basicUiControl(x,y);
    }
    protected void basicSettings(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    protected void basicUiControl(int x,int y){
        setLayout(null);
//        setBackground(new Color(0xab,0xcd,0xef));
        setContentPane(ui);
//        mines.setBackground(new Color(0x00,0xaa,0xaa));
        ui.add(mines);
        mines.setBounds(10,40,minesw,minesh);
        ArrayList<ArrayList<Item>> items = new ArrayList<>();
        for(int i =0;i<y;i++){
            ArrayList<Item> tmp = new ArrayList<>();
            for(int j = 0;j<x;j++){
                Item it = new Item(""+i+j,this);
                tmp.add(it);
                mines.add(it);
            }
            items.add(tmp);
        }
    }
}
