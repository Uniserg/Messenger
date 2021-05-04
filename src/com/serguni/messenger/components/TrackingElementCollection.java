package com.serguni.messenger.components;

import java.util.*;

public class TrackingElementCollection {
    public final Map<Long, Set<UserTrackingImpl>> tempTrackingElements = new HashMap<>();
    public final Map<Long, Set<UserTrackingImpl>> longTrackingElements = new HashMap<>();

    public void removeAll(Set<Long> users) {
        for (Long userId : users) {
            tempTrackingElements.remove(userId);
        }
    }

    public void putToTemp(long userId, UserTrackingImpl tracking) {
        if (!tempTrackingElements.containsKey(userId))
            tempTrackingElements.put(userId, new HashSet<>());

        tempTrackingElements.get(userId).add(tracking);
    }

    public void putToLong(long userId, UserTrackingImpl tracking) {
        if (!longTrackingElements.containsKey(userId))
            longTrackingElements.put(userId, new HashSet<>());

        longTrackingElements.get(userId).add(tracking);
    }

    public void removeTemp(Long userId, UserTracking tracking) {
        tempTrackingElements.get(userId).remove(tracking);

        if (tempTrackingElements.get(userId).isEmpty())
            tempTrackingElements.remove(userId);
    }
}
