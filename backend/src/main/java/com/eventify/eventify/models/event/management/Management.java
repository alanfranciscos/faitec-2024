package com.eventify.eventify.models.event.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Management {
    private Integer id;
    private Integer participate_id;
    private ZonedDateTime managment_at;
    private String type_action;
}
