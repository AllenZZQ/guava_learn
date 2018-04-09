package collect;

import com.google.common.collect.Lists;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

public class ListTool extends TestCase {

    public void test02() {
    }

    public void test01() {
        List<Integer> list = Arrays.asList(1, 1, 2, 2, 3);
        System.out.println(Lists.partition(list, 2));
    }


}
