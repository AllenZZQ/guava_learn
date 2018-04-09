package collect;

import com.google.common.collect.Ordering;
import junit.framework.TestCase;

public class Immutable extends TestCase {

    public void test01() {
        Ordering<Integer> or = Ordering.natural();
    }
}
