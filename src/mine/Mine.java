package mine;

import item.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class Mine extends JFrame {
    private volatile boolean gameStart = true;
    private final int y_expend = 39;
    private final int x_expend = 16;
    private final int designw;
    private final int designh;
    private final int minesw;
    private final int minesh;
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
    private final Picker rand = new Picker("直接踩", true);
    private final Picker caution = new Picker("拆弹", false);
    private final JLabel time = new JLabel("耗时");
    private final JLabel timer = new JLabel("00:00:000");
    private volatile int minutes = 0;
    private volatile int seconds = 0;
    private volatile int milliseconds = 0;


    public Mine(int x, int y, int w, int h) {
        super();
        setTitle("Mine");
        basicSettings();
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        minesw = x * w;
        minesh = y * h;
        designw = minesw + 20;
        designh = minesh + 100;
        setUi();
        addData();
        setBoomCheck();
        addToJPanel();
        Thread thread = new Thread(()->{
            while(gameStart){
                try{
                    Thread.sleep(200);
                    Mine.this.milliseconds += 200;
                    if(Mine.this.milliseconds>=1000){
                        Mine.this.milliseconds -= 1000;
                        Mine.this.seconds+=1;
                    }
                    if(Mine.this.seconds>=60){
                        Mine.this.seconds -= 60;
                        incrementMinute(1);
                    }
                    timer.setText((Mine.this.minutes>=10?""+Mine.this.minutes:"0"+Mine.this.minutes)+":"+
                                    (Mine.this.seconds>=10?""+Mine.this.seconds:"0"+Mine.this.seconds)+":"+
                                    (Mine.this.milliseconds>=100?""+milliseconds:"000"));
                    timer.repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    private synchronized void incrementMinute(int t){
        minutes+=t;
        if(minutes>=60){
            dialog = new JDialog(Mine.this, "OverTime!You are died!", true);
            showDialog();
            JLabel jl = new JLabel("OverTime!You are died!",JLabel.CENTER);
            dialog.add(jl);
            jl.setBounds(30,30,100,50);
            dialog.setVisible(true);
        }
    }

    protected void showDialog() {
        Dimension d = getToolkit().getScreenSize();
        int w = (int) d.getWidth();
        int h = (int) d.getHeight();
        dialog.setBounds(
                (w/2-(dialogDesignw + dialogX_expend)/2), h/2-(dialogDesignh + dialogY_expend)/2, dialogDesignw + dialogX_expend, dialogDesignh + dialogY_expend);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(null);
    }

    /** 布局UI * */
    private void setUi() {
        setLocation(200, 100);
        setSize(x_expend + designw, y_expend + designh);
        setLayout(null);
        this.getContentPane().add(mines);
        mines.setBounds(10, 90, minesw, minesh);
        mines.setLayout(new GridLayout(y, x));
        rand.setSize(w, h);
        caution.setSize(w, h);
        rand.setLocation(designw - 2 * w, 10);
        caution.setLocation(designw - w, 10);
        rand.setHorizontalTextPosition(JLabel.CENTER);
        caution.setHorizontalTextPosition(JLabel.CENTER);
        rand.setFont(new Font("微软雅黑", Font.PLAIN, rand.getWidth()/4));
        caution.setFont(new Font("微软雅黑", Font.PLAIN, caution.getWidth()/4));
        rand.addMouseListener(
                new BasicMouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        pickWithCaution = false;
                        rand.isSelected = true;
                        caution.isSelected = false;
                        rand.repaint();
                        caution.repaint();
                    }
                });
        caution.addMouseListener(
                new BasicMouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        pickWithCaution = true;
                        rand.isSelected = false;
                        caution.isSelected = true;
                        rand.repaint();
                        caution.repaint();
                    }
                });
        this.getContentPane().add(rand);
        this.getContentPane().add(caution);
        this.getContentPane().add(time);
        this.getContentPane().add(timer);
        time.setHorizontalTextPosition(JLabel.CENTER);
        time.setBounds(10,10,w,50);
        time.setFont(new Font("微软雅黑",Font.PLAIN, time.getWidth()/4));
        timer.setHorizontalTextPosition(JLabel.CENTER);
        timer.setBounds(10+w,10, Math.max(w * 2, 100),50);
        timer.setFont(new Font("Times New Roman",Font.PLAIN,timer.getWidth()/5));
    }

    /** 将数据添加到items中进行处理 */
    private void addData() {
        items = new ArrayList<>();
        for (int i = 0; i < y; i++) {
            // 每一行有x个，共有y行
            // 对于每一行
            ArrayList<Item> tmp = new ArrayList<>();
            for (int j = 0; j < x; j++) {
                // 对于每一行的元素
                int x_d = j;
                int y_d = i;
                Item it = new Item(x_d, y_d);
                if (randBoolean()) {
                    it.setIsBoom(true);
                }
                addActionListenerToItem(it);
                tmp.add(it);
            }
            items.add(tmp);
        }
    }
    /** debug 模式展示出 地雷以及周围地雷的数目 */
    private void setBoomCheck() {
        if (items == null) {
        } else {
            for (ArrayList<Item> item : items) {
                for (Item it : item) {
                    setBoomMessage(it);
                }
            }
        }
    }
    /** 将地雷 添加到面板 * */
    private void addToJPanel() {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                mines.add(items.get(i).get(j));
            }
        }
        setVisible(true);
    }
    /** 计算并将地雷的数据传送到地雷 * */
    protected void setBoomMessage(Item it) {
        int x = it.x;
        int y = it.y;
        int count = 0;
        count += countBoom(x - 1, y - 1) ? 1 : 0;
        count += countBoom(x, y - 1) ? 1 : 0;
        count += countBoom(x + 1, y - 1) ? 1 : 0;

        count += countBoom(x - 1, y) ? 1 : 0;
        count += countBoom(x + 1, y) ? 1 : 0;

        count += countBoom(x - 1, y + 1) ? 1 : 0;
        count += countBoom(x, y + 1) ? 1 : 0;
        count += countBoom(x + 1, y + 1) ? 1 : 0;
        it.justMessage("" + count);
    }
    /** 计算出 一个区域，如果不是地雷，他周围的地雷的数目 */
    private boolean countBoom(int x2, int y2) {
        return isValidDirection(x2, y2) && getItemByXY(x2, y2).getIsBoom();
    }
    /** 从数据中取出特定数据用于判断，一个区域周围的地雷数目 * */
    private Item getItemByXY(int x2, int y2) {
        return items.get(y2).get(x2);
    }
    /** 一些基础的设定 * */
    protected void basicSettings() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /** 提前随机布雷的随机计算器 * */
    protected boolean randBoolean() {
        Random rd = new Random();
        return rd.nextInt(5) <= 1;
    }
    /** 为单个区域设置监听器 * */
    protected void addActionListenerToItem(Item it) {
        it.addMouseListener(
                new BasicMouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        it.isPacked = false;
                        if (it.getIsBoom()) {
                            if (pickWithCaution) {
                                // 认为是雷
                                it.setIcon(new ImageIcon("G:\\Java\\Mine\\src\\mine\\cf.png"));
                                System.out.println("Unbelievably,you are right!");
                                it.setMessage(it.getMessage());
                                it.repaint();
                            } else {
                                // 认为不是雷，直接点击
                                it.setMessage("boom");
                                it.repaint();
                                dialog = new JDialog(Mine.this, "你选中了地雷，你凉了！", true);
                                Dimension d = getToolkit().getScreenSize();
                                int w = (int) d.getWidth();
                                int h = (int) d.getHeight();
                                dialog.setBounds(
                                        (w/2-(dialogDesignw + dialogX_expend)/2), h/2-(dialogDesignh + dialogY_expend)/2, dialogDesignw + dialogX_expend, dialogDesignh + dialogY_expend);
                                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                JLabel jl = new JLabel("你踩住雷了,你将要凉了!",JLabel.CENTER);
                                dialog.add(jl);
                                jl.setBounds(30,30,100,50);
                                dialog.setVisible(true);

                            }
                        } else {
                            it.repaint();
                            if (pickWithCaution) {
                                dialog = new JDialog(Mine.this, "你猜错了,加时", true);
                                showDialog();
                                JLabel jl = new JLabel("判断失误,加时两分钟",JLabel.CENTER);
                                dialog.add(jl);
                                jl.setBounds(30,30,200,50);
                                incrementMinute(2);
                                dialog.setVisible(true);
                            } else {
                                it.setMessage(it.getMessage());
                            }
                        }
                    }
                });
    }

    protected boolean isValidDirection(int x_range, int y_range) {
        return (x_range >= 0 && x_range <= x - 1) && (y_range >= 0 && y_range <= y - 1);
    }
}
