package i5.las2peer.services.servicePackage.General;

/**
 * Created by Marv on 05.11.2014.
 */
public enum Rating {
    UNRATED(0),ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);
    private int value;
    Rating(int i) {
       this.value = value;
    }

    public static Rating fromInt(int x) {

        switch(x) {
            case 0:
                return UNRATED;
            case 2:
                return TWO;
            case 3:
                return THREE;
            case 4:
                return FOUR;
            case 5:
                return FIVE;
            default:
                return UNRATED;
        }
    }

    public int getValue() {
        return value;
    }


}
