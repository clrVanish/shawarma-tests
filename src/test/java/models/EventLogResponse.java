package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class EventLogResponse {

    private final Long id;
    private final String type;
    private final Map<String, Object> payload;
    private final String ts;

    @JsonCreator
    public EventLogResponse(@JsonProperty("id") Long id,
                            @JsonProperty("type") String type,
                            @JsonProperty("payload") Map<String, Object> payload,
                            @JsonProperty("ts") String ts) {
        this.id = id;
        this.type = type;
        this.payload = payload;
        this.ts = ts;
    }

    public Long id() { return id; }
    public String type() { return type; }
    public Map<String, Object> payload() { return payload; }
    public String ts() { return ts; }
}