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
    Vector<Item> index;
    Vector<Item> promotion;
    Item curItem;
    int cur;
    DecimalFormat df =new DecimalFormat("0.00");
    final int  LISTSIZE=128;
    readfile() {
        list = new Vector();
        index = new Vector();
        promotion = new Vector();
        list.clear();
        index.clear();
        promotion.clear();
        curItem=new Item();
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
//                    switch (isIndex){
//                        case "index":outindex(lineTxt);break;
//                        case "list": OUTLIST(lineTxt);break;
//                        default:return 4;
//                    }
                    if(isIndex.equals("index")) OutIndex(lineTxt);
                    else if(isIndex.equals("list"))  OutList(lineTxt);
                    //else if(isIndex.equals("promotion")) OutPromotion(lineTxt);
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

        if(lineTxt.indexOf("[")!=-1){
            System.out.println("***商店购物清单***");

            return;
        }
        if(lineTxt.indexOf("ITEM")!=-1){
            curItem=new Item();
            //System.out.println(lineTxt);
            //System.out.println("index:"+index.elementAt(2).toString2());
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
                System.out.println(list.elementAt(i).toString());
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
            return;
        }
    }
    public void  OutIndex(String lineTxt){
        if(lineTxt.indexOf("[")!=-1){
            System.out.println("***商品目录***");
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
            String[] subArray =  strArray[1].split(",");
            curItem.price=Double.parseDouble(subArray[0]);
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
        if(lineTxt.indexOf("promotion:")!=-1&&lineTxt.indexOf("true")!=-1){
           curItem.promotion=true;
            //System.out.println(lineTxt);
            return;
        }
        if(lineTxt.indexOf("{")!=-1){
            curItem=new Item();cur++;
            String[] strArray  = lineTxt.split("'");
            if(strArray[1]!=null)
                curItem.barcode=strArray[1];
            return;
        }
        if(lineTxt.indexOf("}")!=-1) {
            index.add(curItem);return;
            //System.out.println(curItem.toString());
            //System.out.printf("\n");
            //System.out.println(lineTxt);
        }
        if(lineTxt.indexOf("]")!=-1){
            for(int i=0;i<index.size();i++)
            {
                System.out.println(index.elementAt(i).toString2());
            }
            System.out.println("总计 "+cur+" （种）");
            System.out.println("**********************");
            return;
        }
    }
    public Double Total(){

        double sum=0;
        for(int i=0;i<list.size();i++)
        {
            sum+=list.elementAt(i).subTotal();
        }
        return  sum;
    }
    public Double Save(){
        double sv=0;
        for(int i=0;i<list.size();i++)
        {
            sv+=list.elementAt(i).subSave();
        }
        return  sv;
    }
}