package club.jming.utils;

import java.io.UnsupportedEncodingException;

public class Decoding {

    /**
     * 转换字符集
     * 因为tomcat默认字符集不是utf-8，并且试过很多种方法也不能解决
     * @param source
     * @return
     */
    public static String transcoding(String source){
        try {
            byte[] dates =source.getBytes("ISO-8859-1");
            source=new String(dates,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return source;
    }
}
