import com.sun.org.apache.xerces.internal.impl.io.UTF8Reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Vector;

/**
 * Created by mrcai on 2016/1/5.
 */
public  class readfile {
    Vector<Item> list;
    Item curItem;
    int cur;
    DecimalFormat df =new DecimalFormat("0.00");
    final int  LISTSIZE=128;
    readfile() {
        list = new Vector();
        list.clear();
        curItem=new Item();
        cur=0;
    }

    public int readRequirement(String filePath) {
        try {

            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), "utf-8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    OUTLIST(lineTxt);
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
    public void  OUTLIST(String lineTxt){
        if(lineTxt.indexOf("[")!=-1){
            System.out.println("***商店购物清单***");
            return;
        }

        if(lineTxt.indexOf("{")!=-1){
            curItem=new Item();cur++;
            //System.out.println(lineTxt);
            return;
        }
        if(lineTxt.indexOf("unit:")!=-1){
            String[] strArray  = lineTxt.split("'");
            if(strArray[1]!=null)
                curItem.unit=strArray[1];
            //System.out.println(lineTxt);
            return;
        }

        if(lineTxt.indexOf("price:")!=-1){
            String[] strArray  = lineTxt.split(":");
                curItem.price=Double.parseDouble(strArray[1]);
            //curItem.price=1;
            //System.out.println(lineTxt);
            return;
        }
        if(lineTxt.indexOf("discount:")!=-1){
            String[] strArray  = lineTxt.split(":");
            curItem.discount=Double.parseDouble(strArray[1]);
            //curItem.price=1;
            //System.out.println(lineTxt);
            return;
        }
        if(lineTxt.indexOf("name:")!=-1){
            String[] strArray  = lineTxt.split("'");
            if(strArray[1]!=null)
            curItem.name=strArray[1];
            //System.out.println(lineTxt);
            return;
        }
        if(lineTxt.indexOf("barcode:")!=-1){
            String[] strArray  = lineTxt.split("'");
            if(strArray[1]!=null)
                curItem.barcode=strArray[1];
            //System.out.println(lineTxt);
            return;
        }
        if(lineTxt.indexOf("}")!=-1) {
            for(int i=0;i<list.size();i++)
            {
                if(curItem.name.equals(list.elementAt(i).name)){
                    list.elementAt(i).number++;return;}
            }
            list.add(curItem);return;
            //System.out.println(curItem.toString());
            //System.out.printf("\n");
            //System.out.println(lineTxt);
        }
        if(lineTxt.indexOf("]")!=-1){
            for(int i=0;i<list.size();i++)
            {
                System.out.println(list.elementAt(i).toString());
            }
            System.out.println("----------------------");
            System.out.println("总计"+Total()+"（元）");
            System.out.println("节省"+Save()+"（元）");
            System.out.println("**********************");
            return;
        }
    }
    public String Total(){

        double sum=0;
        for(int i=0;i<list.size();i++)
        {
            sum+=list.elementAt(i).subTotal();
        }
        return  df.format(sum);
    }
    public String Save(){
        double sv=0;
        for(int i=0;i<list.size();i++)
        {
            sv+=list.elementAt(i).subSave();
        }
        return  df.format(sv);
    }
}