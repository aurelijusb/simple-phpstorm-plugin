package lt.banelis.aurelijus.simpleplugin;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArrayDescriptionParser {
    public Map<String, String> parse(String comment) {
        Map<String, String> result = new HashMap<String, String>();

        Pattern arrayPattern = Pattern.compile("\\s*\\[(.+)\\]\\s*");
        Matcher arrayMatcher = arrayPattern.matcher(comment);
        if (arrayMatcher.find()) {
            String arrayContent = arrayMatcher.group(1);
            String[] elements = arrayContent.split(",");
            for (String element : elements) {
                String[] parts = element.trim().split("=>", 2);
                if (parts.length == 2) {
                    result.put(parts[0].trim(), parts[1].trim());
                } else if (parts.length == 1) {
                    result.put(parts[0].trim(), "");
                }
            }
        }
        return result;
    }
}
