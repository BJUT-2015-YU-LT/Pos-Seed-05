/**
 * Created by mrcai on 2016/1/13.
 */
public class User {
    String code;
    String name;
    boolean isVip;
    User(){
        code = "USER0000";
        name = "NEW";
        isVip=false;
    }
    User(User u){
        code = u.code;
        name = u.name;
        isVip = u .isVip;
    }

    @Override
    public String toString() {
        return "code:"+code+"\tname:"+name+"\tVIP:"+isVip;
    }
}
