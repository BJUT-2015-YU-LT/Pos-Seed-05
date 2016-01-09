import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class hoho {
    public static void main(String argvs[]) {
        readfile R = new readfile();
        R.readRequirement("requirement3-index.txt","index");
        R.readRequirement("requirement3-list.txt","list");
    }
}

