package Project.General;

/**
 * Created by Marv on 05.11.2014.
 */
public enum Rating {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);
    private int value;
    Rating(int i) {
       this.value = value;
    }

    public int getValue() {
        return value;
    }


}
