package edu.unh.cs.android.transit.myapplication.persistence.domain;

import android.content.res.Resources;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import edu.unh.cs.android.transit.myapplication.MainActivity;
import edu.unh.cs.android.transit.myapplication.R;

/**
 * Data structure for stop predictions.
 * Created by Chris Oelerich on 10/7/15.
 */
public class StopPredictions {

    private static final String TAG = "StopPredictions";

    Resources resources = MainActivity.getAppContext().getResources();

    public StopPredictions(int stopId) throws XmlPullParserException, IOException {

        parseStop(getXmlFromUrl(stopId));
    }

    private void parseStop(XmlPullParser xml) throws XmlPullParserException, IOException {
        int eventType = xml.getEventType();

        String tagName, text;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            tagName = xml.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    Log.v(TAG, "tagname: " + tagName);
                    break;
                case XmlPullParser.TEXT:
                    text = xml.getText();
                    Log.v(TAG, "text: " + text);
                    break;
                case XmlPullParser.END_TAG:
                    break;
            }
            eventType = xml.next();
        }
    }


    /*
body
 predictions routeTitle routeTag stopTitle stopTag
  direction title
   prediction
   prediction
 */




    private XmlPullParser getXmlFromUrl(int stopId) throws XmlPullParserException, IOException {

        String url = resources.getString(R.string.baseUrl) + stopId;
        int lowStop = resources.getInteger(R.integer.lowStop);
        int highStop = resources.getInteger(R.integer.highStop);
        //try {

        //routes from 101 to 183 are valid!
        if ((stopId < lowStop) || (stopId > highStop)) {
            throw new MalformedURLException();
        }

        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);

        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput(in, urlConnection.getContentEncoding());

        urlConnection.disconnect();

        return xpp;
            /*
        } catch (MalformedURLException e) {
            Log.e(TAG, "Malformed URL: " + url, e);
            return null;
        } catch (IOException e) {
            Log.e(TAG, "could not connect to xml url: " + url, e);
            return null;
        } catch (XmlPullParserException e) {
            Log.e(TAG, "xml error", e);
            return null;
        }*/

    }

}
