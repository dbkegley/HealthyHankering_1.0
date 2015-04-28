package david.com.healthyhankering;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.SharedPreferences;
import android.content.Context;
import android.widget.RadioGroup;
import android.widget.RadioButton;


public class TasteActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taste);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_taste, menu);
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
            //start activity intent
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.item_gps) {

            //start map intent
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            String bestProvider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(bestProvider);

            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=grocery");

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                startActivity(mapIntent);
            }

            //Intent intent = new Intent(this, MapsActivity.class);
            //startActivity(intent);
            return true;
        } else if (id == R.id.item_camera) {
            //start pedometer intent
            Intent intent = new Intent(this, CameraActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.item_home) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void openPreference3(View view) {

        RadioGroup group = (RadioGroup) findViewById(R.id.radGroupTaste);
        int selectedId = group.getCheckedRadioButtonId();
        RadioButton radioTaste = null;
        //set Default to Sweet if no radio checked to prevent crash
        if(selectedId == -1)
            radioTaste = (RadioButton) findViewById(R.id.sweetRadButton);
        else
            radioTaste = (RadioButton) findViewById(selectedId);

        String taste = radioTaste.getText().toString();


        SharedPreferences sharedPref = getSharedPreferences(HomeActivity.PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TASTE",taste);
        editor.commit();


        Intent intent = new Intent(this, SpiceActivity.class);
        startActivity(intent);
    }
}
