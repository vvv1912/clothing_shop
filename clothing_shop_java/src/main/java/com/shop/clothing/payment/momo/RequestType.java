package com.shop.clothing.payment.momo;

public enum  RequestType {
    QR_CODE("captureWallet"),
    PAY_WITH_ATM("payWithATM");

    private final String type;
    /**
     * @param type
     */
    RequestType(final String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return type;
    }
}
