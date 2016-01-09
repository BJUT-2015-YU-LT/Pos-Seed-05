/**
 * Created by mrcai on 2016/1/5.
 */
import java.text.DecimalFormat;
public class Item {
    DecimalFormat df =new DecimalFormat("0.00");
    String name;//商品名
    String unit;//单位
    String barcode;//自己条码
    String giftBarcode;//赠品条码
    double price;//单价
    int number;//数量
    double discount;//折扣（xx折）
    int onSale;//买二送一活动标记（0为不参加，1为参加买二送一活动，2为非卖品）
    double subTotal(){
        if(onSale==2)
            return 0;
        else
            return price*number*discount;
    }
    double subSave(){
        if(onSale==2)
            return price*number;
        else
            return price *number*(1-discount);
    }
    Item(){
        name="NULL";
        unit = "NULL";
        giftBarcode="NULL";
        price = 0;
        number = 1;
        discount=1;
        onSale=0;

    }
    Item(String n,  String u, double p){
        name=n;
        unit = u;
        price = p;
        number = 1;
        discount=1;
    }
    Item(Item i){
        name=i.name;
        giftBarcode=i.giftBarcode;
        unit =i.unit;
        price = i.price;
        number = i.number;
        barcode=i.barcode;
        discount=i.discount;
        onSale=i.onSale;
    }
    @Override
    public String toString() {
        String noDnoS="条码："+barcode+"，名称："+name+",数量："+number+unit+",单价："+price+"（元），折扣：无，小计："+df.format(subTotal())+"（元）";
        String yesDnoS="条码："+barcode+"，名称："+name+",数量："+number+unit+",单价："+price+"（元），折扣："+discount*10+"（折）,小计："+df.format(subTotal())+"（元）";
        String noDyesS="条码："+barcode+"，名称："+name+"(促),数量："+number+unit+",单价："+price+"（元），折扣：无，小计："+df.format(subTotal())+"（元）";
        String yesDyesS="条码："+barcode+"，名称："+name+"(促),数量："+number+unit+",单价："+price+"（元），折扣："+discount*10+"（折）,小计："+df.format(subTotal())+"（元）";
        String gift="条码："+barcode+"，名称："+name+"（非卖品），数量："+number+unit+"，单价："+0+"（元）";
        if(discount==1&&onSale==0)
            return noDnoS;
        else if(discount!=1&&onSale==0)
            return yesDnoS;
        else if(discount==1&&onSale==1)
            return noDyesS;
        else if(discount!=1&&onSale==1)
            return yesDyesS;
        else if(discount==1&&onSale==2)
            return gift;

        return "POS混乱了，没有得出正确的计算价格!";
    }
}