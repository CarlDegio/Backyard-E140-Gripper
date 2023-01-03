import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class extract_num {
    public static void main(String[] args) {
        String regex = "\\[(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*)\\]\\n";
        String string = "[33, 0, 158.52045523977029, 0.0, 0.0, 12.0, 41, 1, 158.5922287540639]\n";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        matcher.find();
        for (int i = 0; i < 10; i++) {
            System.out.printf("matcher.group(%s) value:%s\n",i, matcher.group(i));
        }
        double[] a={12,23,3};
        System.out.println(Arrays.toString(a));
        //正则化字符串
    }
}
