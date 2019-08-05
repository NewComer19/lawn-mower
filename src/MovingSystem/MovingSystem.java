package MovingSystem;

public class MovingSystem {
    private String movingSystem;
    private boolean isHandleHolded;

    public MovingSystem(String movingSystem)
    {
        this.movingSystem = movingSystem;
    }

    public boolean isHandleHolded() {
        return isHandleHolded;
    }

    public void setHandleHolded(boolean handleHolded) {
        isHandleHolded = handleHolded;
    }
}
