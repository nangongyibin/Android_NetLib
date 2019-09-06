package com.ngyb.netlib;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/9/6 21:54
 */
public class NewsXmlUtils {

    public static List<News> parseXml(InputStream is) {
        List<News> list = null;
        News news = null;
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is, "UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("channel".equals(parser.getName())) {
                            list = new ArrayList<>();
                        } else if ("item".equals(parser.getName())) {
                            news = new News();
                        } else if ("title".equals(parser.getName())) {
                            news.setTitle(parser.nextText());
                        } else if ("description".equals(parser.getName())) {
                            news.setDescription(parser.nextText());
                        } else if ("image".equals(parser.getName())) {
                            news.setImage(parser.nextText());
                        } else if ("type".equals(parser.getName())) {
                            news.setType(parser.nextText());
                        } else if ("comment".equals(parser.getName())) {
                            news.setComment(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("item".equals(parser.getName())) {
                            list.add(news);
                        }
                        break;
                }
                eventType = parser.next();
            }
            return list;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
