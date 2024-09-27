package com.eventify.eventify.models.event;

public enum EventStageEnum {
    CREATED("created"),
    STARTED("started"),
    FINISHED("finished"),
    CANCELED("canceled");

    private final String stage;

    EventStageEnum(String stage) {
        this.stage = stage;
    }

    public String getStage() {
        return stage;
    }

    public static EventStageEnum fromString(String stage) {
        for (EventStageEnum eventStage : EventStageEnum.values()) {
            if (eventStage.getStage().equalsIgnoreCase(stage)) {
                return eventStage;
            }
        }
        throw new IllegalArgumentException("No enum constant for stage: " + stage);
    }
}
