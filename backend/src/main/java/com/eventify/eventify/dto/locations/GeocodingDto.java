package com.eventify.eventify.dto.locations;
import java.util.List;
import lombok.Data;

@Data
public class GeocodingDto {

    public List<Result> results;

    @Data
    public static class Result {
        private String formatted;
    }
}
