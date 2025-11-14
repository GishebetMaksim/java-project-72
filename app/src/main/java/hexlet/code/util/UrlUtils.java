package hexlet.code.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class UrlUtils {
    public static String normalize(String input) throws MalformedURLException {
        URI uri = URI.create(input);
        URL url = uri.toURL();
        String result = url.getProtocol() + "://" + url.getHost();
        if (url.getPort() != -1) {
            result += ":" + url.getPort();
        }
        return result;
    }
}
