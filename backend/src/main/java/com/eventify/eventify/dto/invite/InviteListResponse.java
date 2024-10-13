package com.eventify.eventify.dto.invite;

import com.eventify.eventify.models.invite.Invite;

import java.util.List;

public record InviteListResponse(List<Invite> invite, int totalInvites) {
}
