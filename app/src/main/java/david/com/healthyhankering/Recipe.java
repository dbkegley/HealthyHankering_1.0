package david.com.healthyhankering;

import android.graphics.Bitmap;

/**
 * Created by dbkegley on 4/1/15.
 */
public class Recipe {

    private String id;
    private String recipeName;
    private String imageURL;
    private Bitmap bitmap;

    public Recipe() {
        id = "";
        recipeName = "";
        imageURL = "";
    }

    public String getRecipeId() {
        return id;
    }

    public void setRecipeId(String id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
