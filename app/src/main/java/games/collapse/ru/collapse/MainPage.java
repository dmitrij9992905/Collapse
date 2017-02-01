package games.collapse.ru.collapse;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        String[] items = new String[] {
                getResources().getString(R.string.continue_text),
                getResources().getString(R.string.newgame_text),
                getResources().getString(R.string.loadgame_text),
                getResources().getString(R.string.settings_text),
                getResources().getString(R.string.about_text),
                getResources().getString(R.string.exit_text)};
        ListView mainMenu = (ListView) findViewById(R.id.mainMenu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, items);
        mainMenu.setAdapter(adapter);
        mainMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // получаем элемент, на который нажал пользователь
                String language = ((TextView) view).getText().toString();

                //4. Создаем всплывающий Toast с текстом нажатого элемента
                Toast.makeText(MainPage.this, language, Toast.LENGTH_LONG).show();
                switch (position) {
                    case 0:
                        Intent intent = new Intent(MainPage.this, GamePage.class);
                        startActivity(intent);
                }
            }
        });

    }
}
