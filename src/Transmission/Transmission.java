package Transmission;

public class Transmission {

    private static String directionOfTurn;


    public static String getDirectionOfTurn() {
        return directionOfTurn;
    }

    public static void setDirectionOfTurn(String dt) {
       directionOfTurn = dt;
    }

    //    private int gear = 0;
//
//    public boolean isEnabled() {
//        return isEnabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        isEnabled = enabled;
//    }
//
//    public int getGear() {
//        return gear;
//    }
//
//    public void setGear(int gear) {
//        if(isEnabled)
//            this.gear = gear;
//        else
//            System.out.println("No transmission");
//    }
}
