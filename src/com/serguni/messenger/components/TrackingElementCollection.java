package com.serguni.messenger.components;

import com.serguni.messenger.dto.models.UserInfoDto;

import java.util.*;

public class TrackingElementCollection {
    public final Map<Long, Set<Tracking>> trackingElements = new HashMap<>();


    public void updateInfo(UserInfoDto userInfoDto) {
        for (Tracking tracking : trackingElements.get(userInfoDto.getId()))
            tracking.updateInfo(userInfoDto);
    }

    public void removeAll(Set<Long> users) {
        for (Long userId : users) {
            trackingElements.remove(userId);
        }
    }

    public void put(long userId, Tracking tracking) {
        if (!trackingElements.containsKey(userId))
            trackingElements.put(userId, new HashSet<>());

        trackingElements.get(userId).add(tracking);
    }

    public void remove(Long userId, Tracking tracking) {
        trackingElements.get(userId).remove(tracking);

        if (trackingElements.get(userId).isEmpty())
            trackingElements.remove(userId);
    }
}
