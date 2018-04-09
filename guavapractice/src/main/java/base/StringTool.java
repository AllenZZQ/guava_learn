package base;

import com.google.common.base.Joiner;
import com.google.common.base.Joiner.MapJoiner;
import com.google.common.base.Strings;
import junit.framework.TestCase;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StringTool extends TestCase {


    public void test05() {
        System.out.println(Strings.padEnd("a", 3, 'x'));
        System.out.println(Strings.repeat("a", 3));
    }

    public void test04() {
        MapJoiner joiner = Joiner.on("|").withKeyValueSeparator("-").useForNull("a");
        Map<String, Integer> map = new HashMap<>();
//        map.put("a", 1);
//        map.put("b", 2);
        System.out.println(new StringBuilder().toString().equals(""));
    }

    public void test03() throws IOException {
        String path = "/home/allen/Desktop/test.txt";
        FileWriter fw = new FileWriter(path);
        Joiner j = Joiner.on("||");
        j.appendTo(fw, "a", "b");
        fw.flush();
    }

    public void test02() {
        System.out.println(new StringBuilder() instanceof Appendable);
    }

    public void test01() {
        Joiner joiner = Joiner.on("|").useForNull("a");
        String result = joiner.join(Arrays.asList("a", null, "c"));
        System.out.println(result);
        StringBuilder builder = new StringBuilder();
        builder.append("sss");
        StringBuilder builder1 = joiner.appendTo(builder, "a", "b");
        System.out.println(builder.toString());
        System.out.println(builder1.toString());
        System.out.println(builder == builder1);
    }


}



