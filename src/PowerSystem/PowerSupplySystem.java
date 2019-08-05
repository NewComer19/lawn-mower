package PowerSystem;

import com.sun.istack.internal.Nullable;

public class PowerSupplySystem {
    private String typeOfSystem;
    private boolean isPowerOn = false;

    public PowerSupplySystem(String typeOfSystem)
    {
        this.typeOfSystem = typeOfSystem;
    }

    public String getTypeOfSystem() {
        return typeOfSystem;
    }

    public void setTypeOfSystem(String typeOfSystem) {
        this.typeOfSystem = typeOfSystem;
    }

    public boolean isPowerOn() {
        return isPowerOn;
    }

    public void setPowerOn(boolean powerOn) {
        isPowerOn = powerOn;
    }
}
