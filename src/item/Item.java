package item;


import javax.swing.*;
import java.awt.*;

public class Item extends JButton {
    private JDialog dialog;
    private JFrame jf;
    private String message;
    private final int dialogY_expend = 39;
    private final int dialogX_expend = 16;
    private final int dialogDesignw;
    private final int dialogDesignh;
    public Item(JFrame jf){
        super();
        this.message = "";
        setMessage("");
        this.jf = jf;
        dialogDesignw = 300;
        dialogDesignh = 200;
        action();
    }
    public Item(String text,JFrame jf){
        super("");
        this.message = text;
        setMessage("");
        this.jf = jf;
        dialogDesignw = 300;
        dialogDesignh = 200;
        action();
    }
   public void setMessage(String text){
        super.setText("");
        this.message = text;
   }
   public String getMessage(){
        return this.message;
   }
    protected void action(){
        addActionListener(e->{
//            if(dialog==null){
//                dialog = new JDialog(jf,"hello",true);
//            }
//            dialog.setBounds(200,100, dialogDesignw + dialogX_expend, dialogDesignh + dialogY_expend);
//            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//            dialog.setVisible(true);
            Item.this.setMessage("99");//会激发重绘
        });
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.black);
        int width = Item.this.getWidth();
        int height = Item.this.getHeight();
        g2.setFont(new Font("微软雅黑",Font.PLAIN,16));
        int size = g2.getFont().getSize();
        g2.drawString(Item.this.getMessage(),calculateX(calLength(Item.this.getMessage()),size,width),calculateY(size,height));
    }
    public int calculateX(int length,int size,int width){
        int totalX = length*size/2;
        if(length>0){
            System.out.println(totalX+" "+(width/2-totalX/2));
        }
        return (width/2-totalX/2);
    }
    public int calculateY(int size,int height){
        return height/2+size/2;
    }
    public int calLength(String str){
        int length = 0;
        for(char c:str.toCharArray()){
            if(isAscii(c)){
                length++;
            }else{
                length+=2;
            }
        }
        return length;
    }
    public boolean isAscii(char c){
        return c<256;
    }

}
