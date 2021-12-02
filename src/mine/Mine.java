package mine;

import item.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    private final JPanel mines = new JPanel();
    private volatile boolean pickWithCaution = false;
    private final int x;
    private final int y;
    private final int w;
    private final int h;
    ArrayList<ArrayList<Item>> items;
    private JDialog dialog;
    private final int dialogY_expend = 39;
    private final int dialogX_expend = 16;
    private final int dialogDesignw = 300;
    private final int dialogDesignh = 200;
    public Mine(int x,int y,int w,int h){
        super();
        setTitle("Mine");
        basicSettings();
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        minesw = x*w;
        minesh = y*h;
        designw = minesw+20;
        designh = minesh+100;
        setUi();
        addData();
        setBoomCheck();
        addToJPanel();


    }
    /**
     * 布局UI
     * **/
    private void setUi() {
        setLocation(200,100);
        setSize(x_expend+designw,y_expend+designh);
        setLayout(null);
        this.getContentPane().add(mines);
        mines.setBounds(10,90,minesw,minesh);
        mines.setLayout(new GridLayout(y,x));
    }

    /**
     * 将数据添加到items中进行处理
     * */
    private void addData() {
        items = new ArrayList<>();
        for(int i =0;i<y;i++){
            //每一行有x个，共有y行
            //对于每一行
            ArrayList<Item> tmp = new ArrayList<>();
            for(int j = 0;j<x;j++){
                //对于每一行的元素
                int x_d = j;
                int y_d = i;
                Item it = new Item(x_d,y_d);
                if(randBoolean()){
                    it.setIsBoom(true);
                }
                addActionListenerToItem(it);
                tmp.add(it);
            }
            items.add(tmp);
        }
    }
    /**
     * debug 模式展示出 地雷以及周围地雷的数目
     * */
    private void setBoomCheck() {
        if(items==null){
        }else{
            for(ArrayList<Item> item:items){
                for(Item it:item){
                    setBoomMessage(it);
                }
            }
        }

    }
    /**
     * 将地雷 添加到面板
     * **/
    private void addToJPanel() {
        for(int i = 0;i<y;i++){
            for(int j = 0;j<x;j++){
                mines.add(items.get(i).get(j));
            }
        }
        setVisible(true);
//        for(int i = 0;i<y;i++){
//            for(int j = 0;j<x;j++){
//                System.out.println(items.get(i).get(j).getLocation());
//            }
//        }
    }
    /**
     * 计算并将地雷的数据传送到地雷
     * **/
    protected void setBoomMessage(Item it){
        if(it.getIsBoom()){
            it.justMessage("Boom");
        }else{
            int x = it.x;
            int y = it.y;
            int count = 0;
            count += countBoom(x-1,y-1)?1:0;
            count += countBoom(x,y-1)?1:0;
            count += countBoom(x+1,y-1)?1:0;

            count += countBoom(x-1,y)?1:0;
            count += countBoom(x+1,y)?1:0;

            count += countBoom(x-1,y+1)?1:0;
            count += countBoom(x,y+1)?1:0;
            count += countBoom(x+1,y+1)?1:0;
            it.justMessage(""+count);
        }
    }
    /**
     * 计算出 一个区域，如果不是地雷，他周围的地雷的数目
     * */
    private boolean countBoom(int x2,int y2){
        return isValidDirection(x2,y2)&&getItemByXY(x2,y2).getIsBoom();
    }
    /**
     * 从数据中取出特定数据用于判断，一个区域周围的地雷数目
     * **/
    private Item getItemByXY(int x2, int y2) {
        return items.get(y2).get(x2);
    }
    /**
     * 一些基础的设定
     * **/
    protected void basicSettings(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 提前随机布雷的随机计算器
     * **/
    protected boolean randBoolean(){
        Random rd = new Random();
        return rd.nextInt(5)<=1;
    }
    /**
     * 为单个区域设置监听器
     * **/
    protected void addActionListenerToItem(Item it){
        it.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                it.isPacked = false;
//                System.out.println(it.getMessage());
//                System.out.println(it.getLocation());
                if(it.getIsBoom()){
                    if(pickWithCaution){
                        //认为是雷
                        it.setIcon(new ImageIcon("source/cc.png"));
                    }else{
                        //认为不是雷，直接点击
                        dialog = new JDialog(Mine.this,"你选中了地雷，你凉了！",true);
                        dialog.setBounds(200,100, dialogDesignw + dialogX_expend, dialogDesignh + dialogY_expend);
                        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        dialog.setVisible(true);
                    }
                }else{
                    it.repaint();
                    if(pickWithCaution){
                        dialog = new JDialog(Mine.this,"你猜错了",true);
                        dialog.setBounds(200,100, dialogDesignw + dialogX_expend, dialogDesignh + dialogY_expend);
                        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        dialog.setVisible(true);
                    }else{
                        it.setMessage(it.getMessage());
                    }
                }
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
