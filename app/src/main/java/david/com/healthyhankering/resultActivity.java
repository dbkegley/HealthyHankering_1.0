package david.com.healthyhankering;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class resultActivity extends ActionBarActivity {
    private final String KEY = "db97c8d9e96c47c830e44e14d611d50e";
    private final String ID = "9cff8a56";

    TableLayout recipeTableView;
    List<Recipe> recipes;
    List<MyTask> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //instantiate tasks and recipe lists as empty arraylists
        tasks = new ArrayList<>();
        recipes = new ArrayList<>();

        //get shared preferences for user selections
        SharedPreferences sharedPref = getSharedPreferences(HomeActivity.PREFS, Context.MODE_PRIVATE);
        String taste = sharedPref.getString("TASTE", "");
        String spice = sharedPref.getString("SPICE", "");
        String cuisine = sharedPref.getString("CUISINE", "");

        //Get Spice range
        String spiceMin;
        String spiceMax;
        if(spice.equalsIgnoreCase("Mild")) {
            spiceMin = "0";
            spiceMax = "0.2";
        }
        else if(spice.equalsIgnoreCase("Medium")) {
            spiceMin = "0.3";
            spiceMax = "0.6";
        }
        else {
            spiceMin = "0.6";
            spiceMax = "1";
        }

        //Get Flavor Range
        String sweetMin = "0";
        String sweetMax = "0.5";
        String savouryMin = "0";
        String savouryMax = "0.5";
        String sourMin = "0";
        String sourMax = "0.5";
        String saltyMin = "0";
        String saltyMax = "0.5";
        String bitterMin = "0";
        String bitterMax = "0.5";

        if(taste.equalsIgnoreCase("Sweet")){
            sweetMin = "0.5";
            sweetMax = "1";
        }
        if(taste.equalsIgnoreCase("Bitter")){
            bitterMin = "0.5";
            bitterMax = "1";
        }
        if(taste.equalsIgnoreCase("Savoury")){
            savouryMin = "0.5";
            savouryMax = "1";
        }
        if(taste.equalsIgnoreCase("Salty")){
            saltyMin = "0.5";
            saltyMax = "1";
        }
        if(taste.equalsIgnoreCase("Sour")){
            sourMin = "0.5";
            sourMax = "1";
        }


        //request all recipes that meet the specifications
        //requestRecipes("http://api.yummly.com/v1/api/recipes?_app_id=" + ID + "&_app_key=" + KEY + "&q=" + taste +
         //       "+" + spice + "+" + cuisine + "&requirePictures=true");

        requestRecipes("http://api.yummly.com/v1/api/recipes?_app_id=" + ID + "&_app_key=" + KEY +"&q=" + cuisine +
        "&flavor.sweet.min=" + sweetMin + "&flavor.sweet.max=" + sweetMax + "&flavor.bitter.min="+
                bitterMin + "&flavor.bitter.max=" + bitterMax + "&flavor.meaty.min=" + savouryMin
                + "&flavor.meaty.max=" + savouryMax + "&flavor.salty.min=" + saltyMin +
                "&flavor.salty.max=" + saltyMax + "&flavor.sour.min=" + sourMin +
                "&flavor.sour.max=" + sourMax +
                "&flavor.piquant.min=" + spiceMin + "&flavor.piquant.max=" + spiceMax + "&requirePictures=true");


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

    protected void updateDisplay() {

        int i = (int)Math.random()*recipes.size();
        //Recipe image
        ImageView pic = (ImageView) findViewById(R.id.resultImage);
        new ImageLoadTask(recipes.get(i).getImageURL(),pic).execute();

        //Recipe name
        TextView resultName = (TextView) findViewById(R.id.resultName);
        resultName.setText(recipes.get(i).getRecipeName());

        //Recipe link
        TextView link = (TextView) findViewById(R.id.hyperlink);
        link.setClickable(true);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='http://www.yummly.com/recipe/" + recipes.get(i).getRecipeId() + "'> View Recipe </a>";
        link.setText(Html.fromHtml(text));

    }

    /* Gets a list of recipes from the Yummly database */
    private void requestRecipes(String uri) {

        MyTask task = new MyTask();
        task.execute(uri);

        //task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "Param1", "Param2", "Param3");
        //String content = YummlyConnection.getData(uri);
    }

    /* gets a specific recipe by its id */
    private void requestRecipeById(String id) {
        MyTask task = new MyTask();
        task.execute("http://api.yummly.com/v1/api/" + id + "?_app_id=" + ID + "&_app_key=" + KEY + "&q=onion+soup&requirePictures=true");
    }

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            //if (tasks.size() == 0) {
                //pb.setVisibility(View.VISIBLE);
            //}
            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... params) {
            String content = YummlyConnection.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            String recipeString = RecipeParser.getRecipeArray(result);
            recipes = RecipeParser.parseFeed(recipeString);

            updateDisplay();

            tasks.remove(this);
            //if(tasks.size() == 0) {
                //pb.setVisibility(View.INVISIBLE);
            //}
        }
    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }
}
