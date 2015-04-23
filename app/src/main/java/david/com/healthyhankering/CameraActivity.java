package david.com.healthyhankering;

import android.content.Intent;
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
            //close this page
            this.finish();

            //only page left on stack is 'Home'
            return true;
        } else if (id == R.id.item_gps) {
            //close this page and open gps
            this.finish();

            //start gps intent
            Intent intent = new Intent(this, GPSActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.item_pedometer) {
            //do nothing, already on this page
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
