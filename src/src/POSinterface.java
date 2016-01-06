import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Tomas on 2016/1/6.
 */
public class POSinterface implements ActionListener{
    JFrame frame = new JFrame("日乐购POS");
    JTabbedPane pane=new JTabbedPane();
    Container con = new Container();
    JLabel label1 = new JLabel("购买清单");
    JLabel label2 = new JLabel("商店目录");
    JTextField text1= new JTextField();
    JTextField text2=new JTextField();
    JFileChooser jfc=new JFileChooser();
    JButton button1=new JButton("选择");
    JButton button2=new JButton("选择");
    JButton button3=new JButton("结账");

    POSinterface(){
        jfc.setCurrentDirectory(new File("")); //初始路径
        double lx=Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double ly=Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        frame.setLocation(new Point((int)(lx/2)-150,(int)(ly/2)-150));
        frame.setSize(350,240);
        frame.setResizable(false); //随意调整大小禁止
        frame.setContentPane(pane);
        label1.setBounds(10, 10, 70, 20);
        text1.setBounds(75, 10, 180, 20);
        button1.setBounds(260, 10, 50, 20); //button1是购物清单读取
        label2.setBounds(10, 50, 70, 20);
        text2.setBounds(75, 50, 180, 20);
        button2.setBounds(260, 50, 50, 20); //button2是商店仓库清单读取
        button3.setBounds(30, 80, 60, 60); //button3是确定按钮
        button1.addActionListener(this); // 添加事件处理
        button2.addActionListener(this); // 添加事件处理
        button3.addActionListener(this); // 添加事件处理
        con.add(label1);
        con.add(text1);
        con.add(button1);
        con.add(label2);
        con.add(text2);
        con.add(button2);
        con.add(button3);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pane.add("Panel1",con);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(button1))
        {
            jfc.setFileSelectionMode(0);//文件only
            int state=jfc.showOpenDialog(null);
            if(state==1)
                return;
            else{
                File f=jfc.getSelectedFile();
                text1.setText(f.getAbsolutePath());
            }
        }
        if(e.getSource().equals(button2))
        {
            jfc.setFileSelectionMode(0);
            int state=jfc.showOpenDialog(null);
            if(state==1) //点击撤销的话，返回
                return;
            else{
                File f=jfc.getSelectedFile();
                text2.setText(f.getAbsolutePath());
            }
        }
        if(e.getSource().equals(button3))
        {
            Readfile r = new Readfile();
            r.readRequirement(text1.getText());
            /**
             * 暂时只实装了购物清单
             * 商店列表暂时还没有
             */
        }
    }
}
