package com.eventify.eventify.models.event.expense;

import com.eventify.eventify.models.event.EventStageEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Expense {

    private Integer id;
    private Integer meetup_id;
    private double cost;
    private ZonedDateTime created_at;
    private String about;

}
