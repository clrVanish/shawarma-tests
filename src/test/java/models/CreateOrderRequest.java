package models;

import java.util.HashMap;
import java.util.Map;

public class CreateOrderRequest {

    private final Long recipeId;
    private final Integer qty;
    private final PaymentChoice payment;

    public CreateOrderRequest(Long recipeId, Integer qty, PaymentChoice payment) {
        this.recipeId = recipeId;
        this.qty = qty;
        this.payment = payment;
    }

    public Long recipeId() { return recipeId; }
    public Integer qty() { return qty; }
    public PaymentChoice payment() { return payment; }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("recipeId", recipeId);
        map.put("qty", qty);
        map.put("payment", Map.of("method", payment.method()));
        return map;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long recipeId;
        private Integer qty;
        private PaymentChoice payment;

        public Builder recipeId(Long recipeId) { this.recipeId = recipeId; return this; }
        public Builder qty(Integer qty) { this.qty = qty; return this; }
        public Builder payment(PaymentChoice payment) { this.payment = payment; return this; }
        public CreateOrderRequest build() { return new CreateOrderRequest(recipeId, qty, payment); }
    }
}