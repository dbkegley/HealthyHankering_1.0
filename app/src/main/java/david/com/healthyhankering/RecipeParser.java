package david.com.healthyhankering;

/**
 * Created by dbkegley on 4/1/15.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecipeParser {

    /* creates a list of recipe POJOs from the JSON response of recipe matches */
    public static List<Recipe> parseFeed(String content) {

        try {

            JSONArray ar = new JSONArray(content);
            List<Recipe> recipeList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                Recipe recipe = new Recipe();

                //set recipe attributes
                recipe.setRecipeId(obj.getString("id"));
                recipe.setRecipeName(obj.getString("recipeName"));

                //set the url
                //recipe.setImageURL(obj.getString("smallImageUrls"));

                //this might work?
                JSONArray imageURLArray = obj.getJSONArray("smallImageUrls");
                String url = imageURLArray.get(0).toString();

                //debug printing
                System.out.println("IMAGE URL: " + url);
                Log.i("url", "URL is: " + url);


                recipe.setImageURL(url);

                try {
                    String imageURL = recipe.getImageURL();
                    InputStream in = (InputStream) new URL(imageURL).getContent();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    recipe.setBitmap(bitmap);
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                recipeList.add(recipe);
            }

            return recipeList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;

        }

    }

    /*get the array of matching recipes from the JSON response */
    public static String getRecipeArray(String content) {

        try {
            JSONObject response = new JSONObject(content);
            String matches = response.getString("matches");

            return matches;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
