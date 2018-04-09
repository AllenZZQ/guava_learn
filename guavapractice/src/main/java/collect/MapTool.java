package collect;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import junit.framework.TestCase;

public class MapTool<K> extends TestCase {

    public void test01() {
        ArrayListMultimap map = ArrayListMultimap.create();
    }

    public void test2() {
        BiMap<String, String> biMap = HashBiMap.create();
        biMap.put("a", "a");
        biMap.put("b", "a");
        biMap.inverse();
    }


}
