package david.com.healthyhankering;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.SharedPreferences;
import android.content.Context;
import android.widget.TextView;


public class resultActivity extends ActionBarActivity {
    private final String KEY = "db97c8d9e96c47c830e44e14d611d50e";
    private final String ID = "9cff8a56";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(HomeActivity.PREFS, Context.MODE_PRIVATE);
        String tasteText = prefs.getString("TASTE","DEFAULT VALUE");
        String spiceText = prefs.getString("SPICE","DEFAULT VALUE");
        String cuisineText = prefs.getString("CUISINE","DEFAULT VALUE");

        String summary = tasteText + "\n" + spiceText + "\n" + cuisineText;

        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setText(summary);

        // Set the text view as the activity layout
        setContentView(textView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up buttonbackground, so long
        // as you specify buttonbackground parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void openPreference1(View view) {
        Intent intent = new Intent(this, TasteActivity.class);
        startActivity(intent);
    }
}
