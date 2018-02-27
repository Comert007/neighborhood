package com.coder.neighborhood;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testIds() {
        List<String> strings = new ArrayList<>();
        strings.add("1111111");
        strings.add("2222222");
        strings.add("3333333");
        strings.add("4444444");

        String ids = "";

        for (int i = 0; i < strings.size(); i++) {
            if (i == 0) {
                ids = ids + "[";
            }
            ids = ids + "{id:";
            ids = ids + strings.get(i) + "}";

            if (i != strings.size() - 1) {
                ids = ids + ",";
            } else {
                ids = ids + "]";
            }
        }

        System.out.println("ids:"+ids);
    }
}