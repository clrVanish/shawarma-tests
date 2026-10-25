package models;

public class RestockRequest {

    private final Integer qty;

    public RestockRequest(Integer qty) {
        this.qty = qty;
    }

    public Integer qty() { return qty; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Integer qty;

        public Builder qty(Integer qty) { this.qty = qty; return this; }
        public RestockRequest build() { return new RestockRequest(qty); }
    }
}