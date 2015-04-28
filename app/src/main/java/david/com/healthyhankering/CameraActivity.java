package david.com.healthyhankering;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;


public class CameraActivity extends ActionBarActivity {


    private static final int REQUEST_CODE = 100;
    private Uri imageUri;
    private File image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }

    public void takePicture(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "HealthyHankerings");
        imagesFolder.mkdirs();
        String random = "img" + ((int)(Math.random()*100000000));
        image = new File(imagesFolder, random + ".jpg");
        imageUri = Uri.fromFile(image);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_CODE);

    }

    public void submitCustomRecipe(View view) {
        Toast.makeText(this, "Recipe Added to Local Database", Toast.LENGTH_LONG).show();
        ImageView image = (ImageView) findViewById(R.id.customImage);
        EditText recipe = (EditText) findViewById(R.id.editRecipe);
        EditText ingredients = (EditText) findViewById(R.id.editIngredients);
        EditText name = (EditText) findViewById(R.id.editCustomName);


        //Restore Page to default screen
        image.setImageResource(R.drawable.healthy_hankering_logo);
        recipe.setText("");
        ingredients.setText("");
        name.setText("");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Image saved to:\n" + imageUri.toString(), Toast.LENGTH_LONG).show();
            ImageView image = (ImageView) findViewById(R.id.customImage);
            image.setImageURI(imageUri);
        }
        else if (resultCode == RESULT_CANCELED) {
        }
        else {
            Toast.makeText(this, "Failed to take picture", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera, menu);
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

    /**
     * A placeholder fragment containing buttonbackground simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_camera, container, false);
            return rootView;
        }
    }



}
