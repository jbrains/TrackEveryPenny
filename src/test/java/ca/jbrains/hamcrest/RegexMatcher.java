package ca.jbrains.hamcrest;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.regex.Pattern;

public class RegexMatcher extends BaseMatcher<CharSequence> {
    private final Pattern regex;

    public RegexMatcher(Pattern regex) {
        this.regex = regex;
    }

    public boolean matches(Object object) {
        return regex.matcher((CharSequence) object).matches();

    }

    public void describeTo(Description description) {
        description.appendText("matches /" + regex.pattern() + "/");
    }

    public static RegexMatcher matches(String regexAsText) {
        return new RegexMatcher(Pattern.compile(regexAsText));
    }

    public static RegexMatcher matches(Pattern regex) {
        return new RegexMatcher(regex);
    }
}