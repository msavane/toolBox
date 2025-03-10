package laboratory.fsqsWholeSale.application;
//To do: make controllers to manage flow with BRs

public class LoghofehBusinessRules {


    public static class DiscountCalculator {

        public static double calculateDiscount(double price, double percentage) {
            return price - (price * (percentage / 100));
        }

        public static double calculateDiscountPromo(double price, double percentage, boolean promoCode) {
            if (promoCode) {
                return price - (price * (percentage / 100));
            }
            return 0;
        }
    }

    //============================================================

    public static double applyTaxRate(double price, String rateType) {

        double GST_RATE = 0.05;
        double QST_RATE = 0.09975; // Default 15 %
        double ON_RATE = 0.08875; // 13 %
        double HST_RATE = 0;

        if ("QST".equals(rateType)) {
            return HST_RATE = price * QST_RATE;
        } else if ("ON".equals(rateType)) {
            return HST_RATE = price * ON_RATE;
        }

        double priceWithGST = price * (1 + GST_RATE); // Apply GST first
        return priceWithGST * (1 + HST_RATE); // Apply QST on new price

    }

}
