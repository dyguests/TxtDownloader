package com.fanhl.util;

/**
 * Created by fanhl on 15/7/2.
 */
public class StringUtils {
    public static String str2html(String in) {
        StringBuffer out = new StringBuffer();
        for (int i = 0; in != null && i < in.length(); i++) {
            char c = in.charAt(i);
            if (c == '\'')
                out.append("'");
            else if (c == '\"')
                out.append("\"");
            else if (c == '<')
                out.append("<");
            else if (c == '>')
                out.append(">");
            else if (c == '&')
                out.append("&");
            else if (c == ' ')
                out.append(" ");
            else if (c == '\n')
                out.append("<br/>");
            else
                out.append(c);
        }
        return out.toString();
    }

}
