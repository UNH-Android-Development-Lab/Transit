package edu.unh.cs.android.transit.myapplication.persistence.domain;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
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

    StopPredictions(int stopId){
        XmlPullParser xml = getXmlFromUrl(stopId);

    }

    /*
    URL getStopUrl(int stopId) {

        String url = MainActivity.getAppContext().getString(R.string.baseUrl);

        //routes from 101 to 183 are valid!
        url += stopId;

        try {
            return new URL(url);
        } catch(MalformedURLException e) {
            Log.e(TAG,"Malformed URL: " + url, e);
            return null;
        }
      }
*/

    XmlPullParser getXmlFromUrl(int stopId) {

        //routes from 101 to 183 are valid!
        String url = MainActivity.getAppContext().getString(R.string.baseUrl) + stopId;

        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(in, urlConnection.getContentEncoding());

            urlConnection.disconnect();

            return xpp;
        } catch(MalformedURLException e) {
            Log.e(TAG,"Malformed URL: " + url, e);
            return null;
        } catch( Exception e) {
            Log.e(TAG, "could not connect to xml url", e);
            return null;
        }

    }

}
