package entity;

/**
 * Created by User on 19.04.2016.
 */
public class CategoryEntity {
    //todo class CategoryEntity

    public enum CategoryType {
        ADMIN, OWNER, STAFF;

        static int admin = "admin".hashCode();
        static int owner = "owner".hashCode();
        static int staff = "staff".hashCode();

        public static CategoryType get(String s) {
            int hashCode = s.hashCode();
            if (hashCode == admin) return ADMIN;
            if (hashCode == owner) return OWNER;
            if (hashCode == staff) return STAFF;
            return null;
        }
    }

    public CategoryType categoryType() {
        return null;
    }
}
