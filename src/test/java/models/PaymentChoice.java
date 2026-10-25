package models;

public class PaymentChoice {

    private final String method;

    public PaymentChoice(String method) {
        this.method = method;
    }

    public String method() { return method; }

    public static PaymentChoice card() { return new PaymentChoice("CARD"); }
    public static PaymentChoice cash() { return new PaymentChoice("CASH"); }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String method;

        public Builder method(String method) { this.method = method; return this; }
        public PaymentChoice build() { return new PaymentChoice(method); }
    }
}