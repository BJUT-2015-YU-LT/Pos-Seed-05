/**
 * Created by mrcai on 2016/1/5.
 */
public class Item {
    String barcode;
    String name;
    String unit;
    double price;
    double discount;
    int number;

    Item(){
        barcode="NULL";
        name="NULL";
        unit = "NNNN";
        price = 0;
        number = 1;
        discount = 0;
    }
    Item(String b,String n,String u,double p,double d){
        barcode=b;
        name=n;
        unit = u;
        price = p;
        number = 1;
        discount = d;
    }
    @Override
    public String toString() {
        return "名称："+name+"，数量："+number+unit+"，单价："+price+"（元），优惠："+discount+"（元）小计："+(price*number-discount)+"（元）" ;
    }
}