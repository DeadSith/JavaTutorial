package lab4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {
    public final static String NAME_REGEX = "Name: *([a-zA-z, ]+);";
    public final static String CREATION_DATE_REGEX = "Created: *(\\d{4}-\\d{2}-\\d{2});";

    private RegexHelper() {

    }

    /**
     * @param input String to test
     * @param regex Regex to use for testing
     * @return Last group that matches specified regex
     */
    public static String getRegexGroup(String input, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher match = p.matcher(input);
        if (!match.find())
            throw new IllegalArgumentException();
        return match.group(1);
    }

}
