/**
 * Created by mrcai on 2016/1/5.
 */
import java.text.DecimalFormat;
public class Item {
    DecimalFormat df =new DecimalFormat("0.00");
    String name;
    String unit;
    String barcode;
    double price;
    int number;
    double discount;
    double subTotal(){
       return price *number*discount;
    }
    double subSave(){
        return price *number*(1-discount);
    }
    Item(){
        name="NULL";
        unit = "NNNN";
        price = 0;
        number = 1;
        barcode=null;
        discount=1;
    }
    Item(    String n,  String u, double p){
        name=n;
        unit = u;
        price = p;
        number = 1;
        discount=1;
        barcode=null;
    }
    Item(Item i){
        name=i.name;
        unit =i.unit;
        price = i.price;
        number = i.number;
        barcode=i.barcode;
        discount=i.discount;
    }
    @Override
    public String toString() {
        return "名称："+name+",数量："+number+unit+",单价："+price+"（元），小计："+df.format(subTotal())+"（元）" ;
    }
    public String toString2() {
        return "Barcode:"+barcode+" 名称："+name+",数量："+number+unit+",单价："+price+"（元），小计："+df.format(subTotal())+"（元）" ;
    }
}