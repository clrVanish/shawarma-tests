package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IngredientResponse {

    private final Long id;
    private final String name;
    private final String unit;
    private final Integer onHand;
    private final Integer minLevel;
    private final SupplierInfo supplier;

    @JsonCreator
    public IngredientResponse(@JsonProperty("id") Long id,
                              @JsonProperty("name") String name,
                              @JsonProperty("unit") String unit,
                              @JsonProperty("onHand") Integer onHand,
                              @JsonProperty("minLevel") Integer minLevel,
                              @JsonProperty("supplier") SupplierInfo supplier) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.onHand = onHand;
        this.minLevel = minLevel;
        this.supplier = supplier;
    }

    public Long id() { return id; }
    public String name() { return name; }
    public String unit() { return unit; }
    public Integer onHand() { return onHand; }
    public Integer minLevel() { return minLevel; }
    public SupplierInfo supplier() { return supplier; }
}