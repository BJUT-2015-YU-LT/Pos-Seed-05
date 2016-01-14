/**
 * Created by mrcai on 2016/1/5.
 */
import java.text.DecimalFormat;
import java.util.Objects;

public class Item {
    DecimalFormat df =new DecimalFormat("0.00");
    String name;
    String unit;
    String barcode;
    double price;
    int number;
    double discount;
    double vipDiscount;
    boolean promotion;
    double subTotal(){
       return price *number*discount;
    }
    double subTotalforVip(){   return price *number*discount*vipDiscount;}
    double subSave(){
        if(promotion){return (number/3)*price+price *number*(1-discount);}
        else   return price *number*(1-discount);
    }
    double subSaveforVip(){
        return price *number*(1-discount*vipDiscount);
    }
    Item(){
        name="NULL";
        unit = "NNNN";
        price = 0;
        number = 1;
        barcode=null;
        promotion=false;
        discount=1;
        vipDiscount=1;
    }
    Item(    String n,  String u, double p){
        name=n;
        unit = u;
        price = p;
        number = 1;
        discount=1;
        vipDiscount=1;
        barcode=null;
        promotion=false;
    }
    Item(Item i){
        name=i.name;
        unit =i.unit;
        price = i.price;
        number = i.number;
        barcode=i.barcode;
        discount=i.discount;
        promotion = i .promotion;
        vipDiscount=i.vipDiscount;
    }
    @Override
    public String toString() {//List
        return "名称："+name+",\t数量："+number+unit+",单价："+df.format(price)+"（元），小计："+df.format(subTotal())+"（元）" ;
    }
    public String toStringforVip() {//List
        return "名称："+name+",\t数量："+number+unit+",单价："+df.format(price)+"（元），小计："+df.format(subTotalforVip())+"（元）" ;
    }
    public String toString2() {//目录
        return "Barcode:"+barcode+"\t名称："+name+",\t数量："+number+unit+",\t单价："+df.format(price)+"元\t小计："+df.format(subTotal())+"元"+"\t会员价："+df.format(subTotalforVip())+"元"+"\t活动："+promotion ;
    }

    public String toString3() {//挥泪赠送
        return " 名称："+name+",\t数量："+number/3+unit ;
    }//挥泪赠送
    Object[] getObjectforVip(){
        return new Object[]{name,number,unit,price+"元",df.format(subTotalforVip())+"元"};
    }
    Object[] getObject(){
        return new Object[]{name,number,unit,price+"元",df.format(subTotal())+"元"};
    }
    //"商品代号","名称","单位","单价","小计"+"会员价"+"活动"
    Object[] getObject_forwarehouse(){
        return new Object[]{barcode,name,unit,price+"元",df.format(subTotal()/number)+"元",df.format(subTotalforVip())+"元",promotion};
    }
}