package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RestockResponse {

    private final Long id;
    private final String name;
    private final Integer onHand;
    private final Double totalCost;
    private final String message;

    @JsonCreator
    public RestockResponse(@JsonProperty("id") Long id,
                           @JsonProperty("name") String name,
                           @JsonProperty("onHand") Integer onHand,
                           @JsonProperty("totalCost") Double totalCost,
                           @JsonProperty("message") String message) {
        this.id = id;
        this.name = name;
        this.onHand = onHand;
        this.totalCost = totalCost;
        this.message = message;
    }

    public Long id() { return id; }
    public String name() { return name; }
    public Integer onHand() { return onHand; }
    public Double totalCost() { return totalCost; }
    public String message() { return message; }
}