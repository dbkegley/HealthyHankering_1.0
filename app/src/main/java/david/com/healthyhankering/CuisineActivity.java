package david.com.healthyhankering;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class CuisineActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cuisine, menu);
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
    public void openResult(View view) {
        RadioGroup group = (RadioGroup) findViewById(R.id.radGroupCuisine);
        int selectedId = group.getCheckedRadioButtonId();
        RadioButton radioCuisine = null;
        //set Default to Barbecue if no radio checked to prevent crash
        if(selectedId == -1)
            radioCuisine = (RadioButton) findViewById(R.id.radButtonBarbecue);
        else
            radioCuisine = (RadioButton) findViewById(selectedId);

        String cuisine = radioCuisine.getText().toString();


        SharedPreferences sharedPref = getSharedPreferences(HomeActivity.PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("CUISINE",cuisine);
        editor.commit();

        Intent intent = new Intent(this, resultActivity.class);
        startActivity(intent);
    }
}
