package david.com.healthyhankering;

/**
 * Created by dbkegley on 4/1/15.
 */
public class Recipe {

    private String id;
    private String recipeName;
    private String imageURL;

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

}
