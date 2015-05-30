package lt.banelis.aurelijus.simpleplugin;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ArrayDescriptionParserTest {
    @org.junit.Test
    @SuppressWarnings("unchecked")
    public void testParse() throws Exception {
        final Map<String, String> empty = new HashMap<String, String>();
        Object[][] testCases = new Object[][] {
            {"", empty},
            {"[]", empty},
            {"[domain=>]", map(empty, "domain", "")},
            {"[domain=>test]", map(empty, "domain", "test")},
            {"[user=>test,password=>qwerty]", map(map(empty, "user", "test"), "password", "qwerty")},
            {"[  user  =>  test\t,  password=>  qwerty   ]", map(map(empty, "user", "test"), "password", "qwerty")},
            {"[user=>test,password=>,other=>a]", map(map(map(empty, "user", "test"), "password", ""), "other", "a")},
        };

        ArrayDescriptionParser parser = new ArrayDescriptionParser();

        for (Object[] testCase : testCases) {
            String input = (String) testCase[0];
            Map<String, String> expected = (Map<String, String>) testCase[1];
            Map<String, String> actual = parser.parse(input);
            assertEquals(expected, actual);
        }
    }

    private Map<String, String> map(Map<String, String> map, String key, String value) {
        Map<String, String> newMap = new HashMap<String, String>(map);
        newMap.put(key, value);
        return newMap;
    }
}