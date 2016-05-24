package other;

import entity.CategoryEntity;
import entity.UserEntity;

/**
 * Created by User on 24.05.2016.
 */
public class Session {
    public static UserEntity user;

    public static boolean haveRule() {
        return !user.getCategory().categoryType().equals(CategoryEntity.CategoryType.STAFF);
    }
}
