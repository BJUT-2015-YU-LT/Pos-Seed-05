/**
 * Created by mrcai on 2016/1/13
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class GUI implements ActionListener{
    private  JTable table_list;
    private  JTable table_warehouse;
    boolean IsBuy=false;
    readfile R=new readfile();
    JFileChooser jfc=new JFileChooser();
    JFrame frame = new JFrame("日乐购POS");
    JTabbedPane pane=new JTabbedPane();
    Container con = new Container();
    Container con2 = new Container();

    JLabel label1 = new JLabel("打印小票");
    JLabel label2 = new JLabel("购物列表");
    JLabel label3 = new JLabel("商品仓库");

    JButton button1=new JButton("仓库改动");
    JButton bt_scan=new JButton("模拟扫描");
    JButton bt_balance=new JButton("确认结账");

    JTextArea tips = new JTextArea();

    JScrollPane scroll_tips = new JScrollPane(tips);
    JScrollPane scroll_list;
    JScrollPane scroll_warehous;
    GUI(){
        R.readRequirement("requirement5-index.txt","index");
        //R.readRequirement("requirement5-list.txt","list");
        jfc.setCurrentDirectory(new File("")); //初始路径
        String[] warehouse_headers = {"商品代号","名称","单位","单价","小计","会员价","活动"};
        Object[][]data_warehouse=R.data_warehouse;
        table_warehouse=new JTable(data_warehouse,warehouse_headers);
        scroll_warehous=new JScrollPane(table_warehouse);

        String[] list_headers = {"名称","数量","单位","单价","小计"};
        Object[][] data_list=new Object[0][5];
        table_list=new JTable(data_list,list_headers);
        table_list.setEnabled(false);
        scroll_list = new JScrollPane(table_list);


        double lx=Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double ly=Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        frame.setLocation(new Point((int)(lx/2)-683,(int)(ly/2)-383));
        frame.setSize(1366,732);
        //frame.setResizable(false); //随意调整大小禁止
        frame.setContentPane(pane);
        frame.setVisible(true);
        scroll_tips.setBounds(850,50,420,600);
        scroll_list.setBounds(10,50,800,500);
        scroll_warehous.setBounds(10,50,1000,600);
        label1.setBounds(1000, 10, 300, 40);
        label2.setBounds(10, 10, 300, 40);
        label3.setBounds(100,10,300,40);
        button1.setBounds(1060, 50, 100, 50); //button1是仓库读取
        bt_scan.setBounds(200, 580, 250, 40); //bt_scan是商店仓库清单读取
        bt_balance.setBounds(466, 580, 250, 40); //bt_balance是确定按钮
        button1.addActionListener(this); // 添加事件处理
        bt_scan.addActionListener(this); // 添加事件处理
        bt_balance.addActionListener(this); // 添加事件处理
        con.add(label1);
        con.add(label2);
        con2.add(label3);

        con.add(bt_scan);
        con.add(bt_balance);
        con2.add(button1);
        con.add(scroll_tips);
        con.add(scroll_list);
        con2.add(scroll_warehous);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pane.add("购物结算",con);
        pane.add("商品仓库",con2);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(button1))
        {
            jfc.setFileSelectionMode(0);//只取文件，不能目录
            int state=jfc.showOpenDialog(null);
            if(state==1)
                return;
            else {
                File f = jfc.getSelectedFile();
                R.readRequirement("requirement5-index.txt","index");
            }
        }
        if(e.getSource().equals(bt_scan))
        {
            jfc.setFileSelectionMode(0);//只取文件，不能目录
            int state=jfc.showOpenDialog(null);
            if(state==1)
                return;
            else{
                File f=jfc.getSelectedFile();
                R=new readfile();
                R.readRequirement("requirement5-index.txt","index");
                R.readRequirement("requirement5-vipList.txt","vipList");
                R.readRequirement(f.getPath(),"list");
                String[] list_headers = {"名称","数量","单位","单价","小计"};
                Object[][] data_list=R.data;
                DefaultTableModel dtm = new DefaultTableModel(data_list,list_headers);
                table_list.setModel(dtm);
                dtm.fireTableStructureChanged();// JTable刷新结构
                dtm.fireTableDataChanged();// 刷新JTable数据
                IsBuy=true;
            }
        }
        if(e.getSource().equals(bt_balance))//结账
        {
            if(IsBuy){
            R.AddIntegral();
            tips.setText(R.sb_tips.toString());
            tips.append("\n…………正在打印…………\n");
                IsBuy=false;
            }
            else tips.setText("未检测到商品或不能重复购物\n");
        }
    }

}