package com.eventify.eventify.models.event.participate;

public enum RoleParticipateEnum {
    ORGANIZER("organizer"),
    PARTICIPANT("participant");

    private final String role;

    RoleParticipateEnum(String stage) {
        this.role = stage;
    }

    public String getRole() {
        return role;
    }

    public static RoleParticipateEnum fromString(String stage) {
        for (RoleParticipateEnum eventStage : RoleParticipateEnum.values()) {
            if (eventStage.getRole().equalsIgnoreCase(stage)) {
                return eventStage;
            }
        }
        throw new IllegalArgumentException("No enum constant for stage: " + stage);
    }
}
