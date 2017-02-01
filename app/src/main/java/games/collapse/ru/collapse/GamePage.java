package games.collapse.ru.collapse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GamePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
        String tmp = "";
        String tag = "";
        String numString = "";
        boolean stop = false;
        boolean stopSignal = false;
        String requestedNum = "3";
        ListView listView = (ListView) findViewById(R.id.vars_select);

        final ArrayList<String> desicion = new ArrayList<String>();
        final ArrayList<String> ids = new ArrayList<String>();
        final ArrayList<String> hrefs = new ArrayList<String>();
        int pos = 0;

        // Создаём адаптер ArrayAdapter, чтобы привязать массив к ListView
        final ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, desicion);
        listView.setAdapter(adapter);
        final String LOG_TAG = "Brief reading";
        try {
            stop = false;
            XmlPullParser xpp = prepareXpp();


            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (xpp.getEventType()) {
                    // начало документа
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(LOG_TAG, "START_DOCUMENT");
                        break;
                    // начало тэга
                    case XmlPullParser.START_TAG:
                        Log.d(LOG_TAG, "START_TAG: name = " + xpp.getName()
                                + ", depth = " + xpp.getDepth() + ", attrCount = "
                                + xpp.getAttributeCount());
                        tmp = "";
                        tag = xpp.getName();


                        for (int i = 0; i < xpp.getAttributeCount(); i++) {
                            if (tag.equals("brief")&&xpp.getAttributeName(i).equals("num")) {
                                numString = xpp.getAttributeValue(i);

                                if (numString.equals(requestedNum)) stopSignal = true;
                            }
                            if (tag.equals("var")&&xpp.getAttributeName(0).equals("id")&&numString.equals(requestedNum)) {
                                ids.add(xpp.getAttributeValue(0));
                            }
                            if (tag.equals("var")&&xpp.getAttributeName(1).equals("href")&&numString.equals(requestedNum)) hrefs.add(xpp.getAttributeValue(1));
                        }
                        if (!TextUtils.isEmpty(tmp))
                            Log.d(LOG_TAG, "Attributes: " + tmp);

                        break;
                    // конец тэга
                    case XmlPullParser.END_TAG:
                        Log.d(LOG_TAG, "END_TAG: name = " + xpp.getName());
                        if (stopSignal&&xpp.getName().equals("brief")) stop = true;
                        if(xpp.getName().equals("var")) pos++;
                        break;
                    // содержимое тэга
                    case XmlPullParser.TEXT:
                        if (tag.equals("text")&&numString.equals(requestedNum)) desicion.add(xpp.getText());
                        if (tag.equals("var")&&numString.equals(requestedNum)) desicion.add(xpp.getText());
                        Toast.makeText(this, numString, Toast.LENGTH_LONG).show();
                        //Log.d(LOG_TAG, "text = " + xpp.getText());
                        tag = "";
                        break;

                    default:
                        break;
                }
                // следующий элемент
                if (stop) break;

                else xpp.next();
            }
            Log.d(LOG_TAG, "END_DOCUMENT");

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    XmlPullParser prepareXpp() {
        return getResources().getXml(R.xml.briefs);
    }

}
