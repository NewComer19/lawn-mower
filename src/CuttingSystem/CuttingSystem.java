package CuttingSystem;

public class CuttingSystem {
    private  String cuttingPart;
    private int widthOfCutting;

    public CuttingSystem(String cuttingPart)
    {
        this.cuttingPart = cuttingPart;
    }

    public int getWidthOfCutting() {
        return widthOfCutting;
    }

    public void setWidthOfCutting(int widthOfCutting) {
        this.widthOfCutting = widthOfCutting;
    }
}
