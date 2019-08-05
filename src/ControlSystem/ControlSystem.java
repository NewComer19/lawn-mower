package ControlSystem;

public class ControlSystem {
    private static boolean isStartButtongHolded = false;

    public static boolean isStartButtongHolded() {
        return isStartButtongHolded;
    }

    public static void setStartButtongHolded(boolean startButtongHolded) {
        isStartButtongHolded = startButtongHolded;
    }
}
