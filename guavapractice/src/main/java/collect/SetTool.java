package collect;

import com.google.common.collect.Sets;

import java.util.Set;

public class SetTool {

    public void test01() {
        Set<Integer> s1 = Sets.newHashSet(1, 2, 3, 5, 5);
        Set<Integer> s2 = Sets.newHashSet(2, 3, 4);
        Sets.SetView<Integer> view = Sets.union(s1, s2);
        Sets.newTreeSet(view);
        System.out.println(view);

    }

}
