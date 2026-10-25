package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SupplierInfo {

    private final String code;
    private final String channel;
    private final Integer leadTimeDays;

    @JsonCreator
    public SupplierInfo(@JsonProperty("code") String code,
                        @JsonProperty("channel") String channel,
                        @JsonProperty("leadTimeDays") Integer leadTimeDays) {
        this.code = code;
        this.channel = channel;
        this.leadTimeDays = leadTimeDays;
    }

    public String code() { return code; }
    public String channel() { return channel; }
    public Integer leadTimeDays() { return leadTimeDays; }
}