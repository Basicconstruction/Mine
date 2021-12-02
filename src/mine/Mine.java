package mine;

import item.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Mine extends JFrame {
    private boolean debug = true;
    private final int y_expend = 39;
    private final int x_expend = 16;
    private final int designw;
    private final int designh;
    private final int minesw;
    private final int minesh;
    private final JPanel ui = new JPanel(null);
    private final JPanel mines;
    private boolean unpack = true;
    private final int x;
    private final int y;
    ArrayList<ArrayList<Item>> items;
    private JDialog dialog;
    private final int dialogY_expend = 39;
    private final int dialogX_expend = 16;
    private final int dialogDesignw;
    private final int dialogDesignh;
    public Mine(int x,int y,int w,int h){
        super();
        setTitle("Mine");
        basicSettings();
        this.x = x;
        this.y = y;
        minesw = x*w;
        minesh = y*h;
        designw = minesw+20;
        designh = minesh+50;
        setLocation(200,100);
        mines = new JPanel(new GridLayout(y,x));
        setSize(x_expend+designw,y_expend+designh);
        basicUiControl(x,y);
        dialogDesignw = 300;
        dialogDesignh = 200;
        setBoomCheck();
    }
    private void setBoomCheck() {
        if(items==null){
            return;
        }else{
            for(ArrayList<Item> item:items){
                for(Item it:item){
                    setBoomMessage(it);
                }
            }
        }

    }
    protected void setBoomMessage(Item it){
        if(it.getIsBoom()){
            it.justMessage("Boom");
        }else{
            int x = it.getX();
            int y = it.getY();
            int count = 0;
            count += countBoom(x-1,y)?1:0;
            count += countBoom(x+1,y)?1:0;
            count += countBoom(x-1,y-1)?1:0;
            count += countBoom(x,y-1)?1:0;
            count += countBoom(x+1,y-1)?1:0;
            count += countBoom(x+1,y-1)?1:0;
            count += countBoom(x+1,y)?1:0;
            count += countBoom(x+1,y+1)?1:0;
            it.justMessage(""+count);
            System.out.println(y+" "+x+" "+count);
        }
    }
    private boolean countBoom(int x2,int y2){
        System.out.println(" "+(isValidDirection(x2,y2)&&getItemByXY(x2,y2).getIsBoom()?"true":"false")+" "+x2+" "+y2);
        return isValidDirection(x2,y2)&&getItemByXY(x2,y2).getIsBoom();
    }
    private Item getItemByXY(int x2, int y2) {
        return items.get(y2).get(x2);
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
        items = new ArrayList<>();
        for(int i =0;i<y;i++){
            //每一行有x个，共有y行
            //对于每一行
            ArrayList<Item> tmp = new ArrayList<>();
            for(int j = 0;j<x;j++){
                //对于每一行的元素
                int x_d = j;
                int y_d = i;
                Item it = new Item("",x_d,y_d);
                if(randBoolean()){
                    it.setIsBoom(true);
                }
                addActionListenerToItem(it);
                tmp.add(it);
                mines.add(it);
            }
            items.add(tmp);
        }
    }
    protected boolean randBoolean(){
        Random rd = new Random();
        return rd.nextInt(5)<=1;
    }
    protected void addActionListenerToItem(Item it){
        it.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                if(it.getIsBoom()&&unpack){
                    if(dialog==null){
                        dialog = new JDialog(Mine.this,"hello",true);
                    }
                    dialog.setBounds(200,100, dialogDesignw + dialogX_expend, dialogDesignh + dialogY_expend);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                }else{
                    it.setMessage(it.getMessage());
                }

            }

        });
    }
    protected void unpackAllButton(){
        if(debug){
            for(ArrayList<Item> item:items){
                for(Item it:item){
//    				it.setMessage();
                }
            }
        }
    }
    protected boolean isValidDirection(int x_range,int y_range){
        return (x_range>=0&&x_range<=x-1)&&(y_range>=0&&y_range<=y-1);
    }
}
