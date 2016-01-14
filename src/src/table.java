import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * Created by mrcai on 2016/1/14.
 */
public class table extends JTable {


    Vector<Item> list;
    Vector<Item> index;
    Vector<Item> promotion;
    Vector<User>  users;
    User curUser;
    User currentUser;
    Item curItem;
    int cur;
    DecimalFormat df =new DecimalFormat("0.00");
    final int  LISTSIZE=128;
    table() {
        list = new Vector();
        index = new Vector();
        promotion = new Vector();
        users = new Vector();
        list.clear();
        index.clear();
        promotion.clear();
        users.clear();
        curItem=new Item();
        curUser = new User();
        currentUser = new User();
        cur=0;
    }

    public int readRequirement(String filePath,String isIndex) {
        try {

            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), "utf-8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if(isIndex.equals("index")) OutIndex(lineTxt);
                    else if(isIndex.equals("list"))  OutList(lineTxt);
                    else if(isIndex.equals("vipList")) OutVipList(lineTxt);
                }
                read.close();
            } else {
                System.out.println("#找不到指定的文件#");
                return 1;
            }
        } catch (Exception e) {
            System.out.println("#读取文件内容出错#");
            e.printStackTrace();
            return 2;
        }
        return 0;
    }
    public void  OutList(String lineTxt){

        if(lineTxt.equals("{")){
            System.out.println("***商店购物清单***");
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");//可以方便地修改日期格式
            String tt = dateFormat.format( now );
            System.out.printf("打印时间：%s\n",tt);
            System.out.println("----------------------");
            return;
        }
        if(lineTxt.indexOf("user")!=-1){
            currentUser=new User();
            String ccc=null;
            String[] strArray  = lineTxt.split("'");
            if(strArray[3]!=null) ccc= strArray[3];
            for(int i =0;i<users.size();i++){
                if(ccc.equals(users.elementAt(i).code)) {
                    currentUser = new User(users.elementAt(i));break;
                }
            }
            if(currentUser.isVip)System.out.printf("会员编号：%s\t 会员积分：%d 分\n",currentUser.code,currentUser.integral);
            else System.out.println("当前为非会员");
        }
        if(lineTxt.indexOf("ITEM")!=-1){
            curItem=new Item();
            String[] strArray  = lineTxt.split("'");
            if(strArray[1]!=null)
                curItem.barcode=strArray[1];
            for(int i=0;i<index.size();i++)
            {
                //System.out.println("curItem:"+curItem.barcode+"\t"+"index:"+index.elementAt(i).barcode);
                if(curItem.barcode.equals(index.elementAt(i).barcode))
                {
                    //System.out.println("curItem:"+curItem.toString2()+"\n"+index.elementAt(i).toString2()+"\n");
                    for(int j=0;j<list.size();j++)
                    {
                        if(curItem.barcode.equals(list.elementAt(j).barcode))
                        {
                            list.elementAt(j).number++;
                            return;
                        }
                    }
                    curItem=new Item(index.elementAt(i));
                    list.add(curItem);
                    //System.out.println("curItem++++:"+curItem.toString());
                    return;
                }
            }
            System.out.println("Not found:"+curItem.toString2());

            return;
        }

        if(lineTxt.indexOf("]")!=-1){
            for(int i=0;i<list.size();i++)
            {
                if(currentUser.isVip)  System.out.println(list.elementAt(i).toStringforVip());
                else System.out.println(list.elementAt(i).toString());
            }
            System.out.println("----------------------");
            System.out.println("挥泪赠送商品：");
            for(int i=0;i<list.size();i++)
            {
                if (list.elementAt(i).promotion)
                    System.out.println(list.elementAt(i).toString3());
            }
            System.out.println("----------------------");
            System.out.println("总计" + df.format(Total() - Save())+"（元）");
            System.out.println("节省"+df.format(Save())+"（元）");
            System.out.println("**********************");
            currentUser.AddIntegral((int)(Total() - Save()));
            return;
        }
    }
    public void  OutIndex(String lineTxt){
        if(lineTxt.equals("{")){
            System.out.println("***商品目录***");
            return;
        }

        if(lineTxt.indexOf("unit")!=-1){
            String[] strArray  = lineTxt.split("'");
            if(strArray[1]!=null)
                curItem.unit=strArray[3];
            //System.out.println(lineTxt);
            return;
        }

        if(lineTxt.indexOf("price")!=-1){
            String[] strArray  = lineTxt.split(":");
            String[] subArray =  strArray[1].split(",");
            curItem.price=Double.parseDouble(subArray[0]);
            //curItem.price=1;
            //System.out.println(lineTxt);
            return;
        }
        if(lineTxt.indexOf("discount")!=-1){
            String[] strArray  = lineTxt.split(":");
            String[] subArray =  strArray[1].split(",");
            curItem.discount=Double.parseDouble(subArray[0]);
            //curItem.price=1;
            //System.out.println(lineTxt);
            return;
        }
        if(lineTxt.indexOf("vipDiscount")!=-1){
            String[] strArray  = lineTxt.split(":");
            String[] subArray =  strArray[1].split(",");
            curItem.vipDiscount=Double.parseDouble(subArray[0]);
            //curItem.price=1;
            //System.out.println(lineTxt);
            return;
        }
        if(lineTxt.indexOf("name")!=-1){
            String[] strArray  = lineTxt.split("'");
            if(strArray[3]!=null)
                curItem.name=strArray[3];
            //System.out.println(lineTxt);
            return;
        }
        if(lineTxt.indexOf("promotion")!=-1&&lineTxt.indexOf("true")!=-1){
            if(curItem.vipDiscount==1)//会员商品不参与活动
                curItem.promotion=false;
            else curItem.promotion =true;
            //System.out.println(lineTxt);
            return;
        }
        if(lineTxt.indexOf("{")!=-1&&lineTxt.indexOf("ITEM")!=-1){
            curItem=new Item();cur++;
            index.add(curItem);
            String[] strArray  = lineTxt.split("'");
            if(strArray[1]!=null)
                curItem.barcode=strArray[1];
            return;
        }
        if(lineTxt.equals("}")){
            for(int i=0;i<index.size();i++)
            {
                System.out.println(index.elementAt(i).toString2());
            }
            System.out.println("总计 "+cur+" （种）");
            System.out.println("**********************");

            return;
        }
    }
    public void OutVipList(String lineTxt){
        if(lineTxt.indexOf("USER")==-1&&lineTxt.indexOf("{")!=-1){
            System.out.println("***Vip index***");
            return;
        }
        if(lineTxt.indexOf("USER")!=-1&&lineTxt.indexOf("{")!=-1){
            curUser = new User();
            String[] strArray  = lineTxt.split("'");
            curUser.code =strArray[1];
            return;
        }
        if(lineTxt.indexOf("name")!=-1){
            String[] strArray  = lineTxt.split("'");
            curUser.name =strArray[1];
            String[] strintegral =  strArray[2].split(",");
            curUser.integral = Integer.parseInt(strintegral[1]);
        }
        if(lineTxt.indexOf("isVip")!=-1){
            if(lineTxt.indexOf("true")!=-1)       curUser.isVip =true;
            else curUser.isVip=false;
            users.add(curUser);
        }
        if(lineTxt.equals("}")){
            for(int i =0 ; i <users.size();i++){
                System.out.println(users.elementAt(i));
            }
            System.out.println("*************");
        }
    }
    public Double Total(){
        double sum=0;
        for(int i=0;i<list.size();i++)
        {
            if(currentUser.isVip)sum+=list.elementAt(i).subTotalforVip();
            else sum+=list.elementAt(i).subTotal();
        }
        return  sum;
    }
    public Double Save(){
        double sv=0;
        for(int i=0;i<list.size();i++)
        {
            if(currentUser.isVip) sv+=list.elementAt(i).subSaveforVip();
            else sv+=list.elementAt(i).subSave();
        }
        return  sv;
    }

}
