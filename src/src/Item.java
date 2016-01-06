/**
 * Created by mrcai on 2016/1/5.
 */
public class Item {
    String name;
    String unit;
    String barcode;
    double price;
    int number;
    double subTotal(){
       return price *number;
    }
    Item(){
        name="NULL";
        unit = "NNNN";
        price = 0;
        number = 1;
    }
    Item(    String n,  String u, double p){
        name=n;
        unit = u;
        price = p;
        number = 1;
    }
    Item(Item i){
        name=i.name;
        unit =i.unit;
        price = i.price;
        number = i.number;
        barcode=i.barcode;
    }
    @Override
    public String toString() {
        return "名称："+name+",数量："+number+unit+",单价："+price+"（元），小计："+subTotal()+"（元）" ;
    }
}