package david.com.healthyhankering;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class resultActivity extends ActionBarActivity {
    private final String KEY = "db97c8d9e96c47c830e44e14d611d50e";
    private final String ID = "9cff8a56";

    //ScrollView scrollView;
    ImageView recipeImageView;
    TextView recipeNameTextView;
    List<Recipe> recipes;
    List<MyTask> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //scrollView = (ScrollView) findViewById(R.id.scrollView);
        //scrollView.setMovementMethod(new ScrollingMovementMethod());

        recipeImageView = (ImageView) findViewById(R.id.recipeImageView);
        recipeNameTextView = (TextView) findViewById(R.id.recipeNameTextView);

        tasks = new ArrayList<>();

        requestRecipes("http://api.yummly.com/v1/api/recipes?_app_id=" + ID + "&_app_key=" + KEY + "&q=onion+soup&requirePictures=true");
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

    protected void updateDisplay(Recipe selectedRecipe) {
        recipeNameTextView.setText(selectedRecipe.getRecipeName());
    }

    /* Gets a recipe from the Yummly database */
    private void requestRecipes(String uri) {

        //print the uri (debug)
        //System.out.print(uri);

        MyTask task = new MyTask();
        task.execute(uri);
        //task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "Param1", "Param2", "Param3");

        //String content = YummlyConnection.getData(uri);

        //show the results as popup
        //Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }

    private void requestRecipeById(String id) {
        MyTask task = new MyTask();
        task.execute("http://api.yummly.com/v1/api/" + id + "?_app_id=" + ID + "&_app_key=" + KEY + "&q=onion+soup&requirePictures=true");
    }

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            //updateDisplay("Starting Task");

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

            //String recipeString = RecipeParser.getRecipeArray(result);
            //recipes = RecipeParser.parseFeed(recipeString);

            //Recipe selectedRecipe = selectRandomRecipe(recipes);

            //updateDisplay(selectedRecipe);

            tasks.remove(this);
            //if(tasks.size() == 0) {
                //pb.setVisibility(View.INVISIBLE);
            //}
        }
    }
}
