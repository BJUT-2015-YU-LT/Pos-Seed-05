import java.io.*;

/**
 * Created by mrcai on 2016/1/13.
 */
public class User {
    String code;
    String name;
    boolean isVip;
    int integral;//积分
    User(){
        code = "USER0000";
        name = "NEW";
        isVip=false;
        integral = 0;
    }
    User(User u){
        code = u.code;
        name = u.name;
        isVip = u .isVip;
        integral = u.integral;
    }
    int AddIntegral(int money){
        if(!isVip) return 0;
        try{
            String encoding = "GBK";
            File file = new File("requirement5-vipList.txt");
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader( new FileInputStream(file), "utf-8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                        if(lineTxt.indexOf(name)!=-1) {
                            if(integral>500) integral+=(money/5) *5;
                            else if(integral>200) integral+=(money/5) *3;
                            else if(integral>0) integral+=(money/5) *1;
                            replaceTxtByStr(lineTxt,lineTxt.split(",")[0]+","+integral+",");
                        }
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
        return  0;
    }
    @Override
    public String toString() {
        return "code:"+code+"\tname:"+name+"\tVIP:"+isVip;
    }
    /** *//**
     * 将文件中指定内容的第一行替换为其它内容 .
     *
     * @param oldStr
     *            查找内容
     * @param replaceStr
     *            替换内容
     */
    public static void replaceTxtByStr(String oldStr,String replaceStr) {
        String temp = "";
        try {
            File file = new File("requirement5-vipList.txt");
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            // 保存该行前面的内容
            for (int j = 1; (temp = br.readLine()) != null
                    && !temp.equals(oldStr); j++) {
                buf = buf.append(temp);
                buf = buf.append(System.getProperty("line.separator"));
            }

            // 将内容插入
            buf = buf.append(replaceStr);

            // 保存该行后面的内容
            while ((temp = br.readLine()) != null) {
                buf = buf.append(System.getProperty("line.separator"));
                buf = buf.append(temp);
            }

            br.close();
            FileOutputStream fos = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
