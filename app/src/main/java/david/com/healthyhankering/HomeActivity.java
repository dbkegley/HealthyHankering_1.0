package david.com.healthyhankering;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends ActionBarActivity {

    public static final String PREFS = "david.com.healthyhankering";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

            //LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            //Criteria criteria = new Criteria();

            //String bestProvider = locationManager.getBestProvider(criteria, true);
            //Location location = locationManager.getLastKnownLocation(bestProvider);

            //double latitude = location.getLatitude();
            //double longitude = location.getLongitude();

            //Uri gmmIntentUri = Uri.parse("geo:" + "0" + "," + "0" + "?q=restaurants");

            //Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            //mapIntent.setPackage("com.google.android.apps.maps");

            //startActivity(mapIntent);

            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.item_pedometer) {
            //start pedometer intent
            Intent intent = new Intent(this, CameraActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openPreference1(View view) {
        Intent intent = new Intent(this, TasteActivity.class);
        startActivity(intent);

    }
}
