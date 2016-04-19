package entity;

/**
 * Created by User on 19.04.2016.
 */
public class UserEntity {
    //todo class UserEntity

    private String pass;
    private String login;
    private String salt;
    private CategoryEntity category;

    public String getPass() {
        return pass;
    }

    public String getLogin() {
        return login;
    }

    public String getSalt() {
        return salt;
    }

    public CategoryEntity getCategory() {
        return category;
    }
}
