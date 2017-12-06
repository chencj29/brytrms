package test;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by xiaowu on 16/8/15.
 */
public class ListTester {
    public static void main(String[] args) {
        List list1 = null;
        List list2 = null;
        testList(list1,list2);

        list1.stream().filter(element -> !list2.contains(element)).forEach(System.out::print);

    }

    public static void testList(List l1,List l2){
        l1 = Lists.newArrayList("xiaowu", "xiaowu1", "xiaowu2");
        l2 = Lists.newArrayList("xiaowu2", "xiaowu1");
    }
}
