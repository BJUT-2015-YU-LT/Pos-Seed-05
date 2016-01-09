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
public  class Readfile {
    Vector<Item> inventory;
    Vector<Item> list;
    Item curItem;
    Item invItem;
    Item giftItem;
    int sale;
    int errFlag;
    int cur;
    int cur_inv;
    DecimalFormat df =new DecimalFormat("0.00");
    final int  LISTSIZE=128;
    Readfile() {
        inventory=new Vector();
        list = new Vector();
        list.clear();
        inventory.clear();
        /*curItem=new Item();
        giftItem=new Item();
        invItem=new Item();*/
        cur_inv=0;
        cur=0;
        errFlag=0;
        sale=0;
    }

    public int readInventory(String filePath)
    {
        /**
         * 这个是读取仓库文件的实现
         */
        try{
            String encoding="GBK";
            File file=new File(filePath);
            if(file.isFile()&&file.exists()){
                InputStreamReader read=new InputStreamReader(new FileInputStream(file),"GBK");
                BufferedReader br=new BufferedReader(read);
                String lineTxt=null;
                while((lineTxt=br.readLine())!=null){
                    Inventory(lineTxt);
                }
                read.close();
            }
            else{
                System.out.println("#找不到指定的仓库#");
                return 1;
            }

        }catch(Exception e)
        {
            System.out.println("#读取仓库内容出错#");
            e.printStackTrace();
            return 2;
        }
        return 0;
    }
    public int readShoplist(String filePath) {
        /**
         * 这个是读取购物清单的实现
         */
        try {

            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), "utf-8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    shopList(lineTxt);
                }
                read.close();
            } else {
                System.out.println("#找不到指定的购物清单#");
                return 1;
            }
        } catch (Exception e) {
            System.out.println("#读取购物清单内容出错#");
            e.printStackTrace();
            return 2;
        }
        return 0;
    }
    public int Inventory(String lineTxt){
        if(lineTxt.indexOf("[")!=-1){
            System.out.println("***正在读取库存...***");
            return 0;
        }
        if(lineTxt.indexOf("{")!=-1){
            invItem=new Item();
            cur_inv++;
            return 0;
        }
        if(lineTxt.indexOf("giftBarcode:")!=-1){
            String[] strArray  = lineTxt.split("'");
            if(strArray[1]!=null)
                invItem.giftBarcode=strArray[1];

            return 0;
        }
        if(lineTxt.indexOf("barcode:")!=-1){
            String[] strArray  = lineTxt.split("'");
            if(strArray[1]!=null)
                invItem.barcode=strArray[1];
            return 0;
        }
        if(lineTxt.indexOf("unit:")!=-1){
            String[] strArray  = lineTxt.split("'");
            if(strArray[1]!=null)
                invItem.unit=strArray[1];
            return 0;
        }

        if(lineTxt.indexOf("price:")!=-1){
            String[] strArray  = lineTxt.split(":");
            invItem.price=Double.parseDouble(strArray[1]);
            return 0;
        }
        if(lineTxt.indexOf("discount:")!=-1){
            String[] strArray  = lineTxt.split(":");
            invItem.discount=Double.parseDouble(strArray[1]);
            return 0;
        }
        if(lineTxt.indexOf("onSale:")!=-1){
            String[] strArray=lineTxt.split(":");
            invItem.onSale=Integer.parseInt(strArray[1]);
            return 0;
        }
        if(lineTxt.indexOf("name:")!=-1){
            String[] strArray  = lineTxt.split("'");
            if(strArray[1]!=null)
                invItem.name=strArray[1];
            return 0;
        }
        if(lineTxt.indexOf("}")!=-1) {

            inventory.add(invItem);
            if(invItem.giftBarcode!="NULL"){
                giftItem=new Item();
                cur_inv++;
                giftItem.barcode=invItem.giftBarcode;
                giftItem.name=invItem.name;
                giftItem.price=invItem.price;
                giftItem.unit=invItem.unit;
                giftItem.onSale=2;
                inventory.add(giftItem);
            }
            return 0;
        }
        if(lineTxt.indexOf("]")!=-1){
            /*for(int i=0;i<inventory.size();i++)
            {
                System.out.println(inventory.elementAt(i).toString());
            }*/
            System.out.println("----------------------");
            System.out.println("成功！一共读取"+cur_inv+"种商品.");
            System.out.println("**********************");
            return 0;
        }
        return 1; //正常情况不应该返回这个 代表读取到非法输入
    }
    public int  shopList(String lineTxt){

        if(lineTxt.indexOf("[")!=-1){
            System.out.println("***日乐购 POS系统 v0.3 购物清单***");
            return 0;
        }

        if(lineTxt.indexOf("{")!=-1){
            curItem=new Item();
            cur++;
            return 0;
        }
        if(lineTxt.indexOf("barcode:")!=-1){
            String[] strArray  = lineTxt.split("'");
            if(strArray[1]!=null) {
                curItem.barcode = strArray[1];
                for(int i=0;i<inventory.size();i++)
                {
                    if(curItem.barcode.equals(inventory.elementAt(i).barcode)){
                        //在库存里发现这个条码，成功录入
                        curItem.name=inventory.elementAt(i).name;
                        curItem.unit=inventory.elementAt(i).unit;
                        curItem.price=inventory.elementAt(i).price;
                        curItem.discount=inventory.elementAt(i).discount;
                        curItem.onSale=inventory.elementAt(i).onSale;
                        curItem.giftBarcode=inventory.elementAt(i).giftBarcode;
                        return 0;
                    }
                    else if(curItem.onSale==2)
                    {
                        System.out.println("#非卖品，无效输入#");
                        errFlag=1;
                        return 3;
                    }
                }

                System.out.println("#没有找到这个条码'"+curItem.barcode+"'#");
                errFlag=1;
                return 1;
            }
            return 2;
        }
        if(lineTxt.indexOf("}")!=-1) {

            if(errFlag==0) {
                if(curItem.onSale==1)
                    sale++; //如果该商品是促销商品，促销指示器+1
                for (int i = 0; i < list.size(); i++) {
                    if (curItem.barcode.equals(list.elementAt(i).barcode)) {
                        list.elementAt(i).number++;
                        if(sale==2)//促销商品，再赠送一个（买二送一）
                        {
                                for(int j=0;j<inventory.size();j++)
                                {
                                    if(curItem.giftBarcode.equals(inventory.elementAt(j).barcode)){
                                        list.add(inventory.elementAt(j));
                                        sale=0;//促销指示器=0
                                        return 0;
                                    }
                                }
                                 System.out.println("#仓库中没有找到对应的赠品，或赠品已赠送完毕！#");
                                return 1;
                        }
                    }
                }
                list.add(curItem);//普通的情况，往购物清单里添加当前商品
                return 0;
            }
            else if(errFlag==1){
                errFlag=0;
                return 1;
            }
        }
        if(lineTxt.indexOf("]")!=-1){
            for(int i=0;i<list.size();i++)
            {
                System.out.println(list.elementAt(i).toString());
            }
            System.out.println("----------------------");
            System.out.println("总计"+Total()+"（元）");
            System.out.println("节省"+Save()+"（元）");
            System.out.println("********请付款********");
            return 0;
        }
        return 2;
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