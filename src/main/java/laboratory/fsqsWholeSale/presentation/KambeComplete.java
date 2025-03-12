package laborartory.fsqsWholeSale.presentation;

//implement check out system with 3rd party (orange money)

import laborartory.fsqsWholeSale.integration.WariPaymentMethods;

public class KambeComplete extends WariPaymentMethods {
    private KambeComplete() {
    }

    public static KambeComplete createKambeComplete() {
        return new KambeComplete();
    }
}
