package christmas.domain;

import java.util.Arrays;
import java.util.Comparator;

public enum EventBadge {
    NONE("없음", 0),
    STAR("별", 5_000),
    TREE("트리", 10_000),
    SANTA("산타", 20_000);

    private final String name;
    private final int price;

    EventBadge(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public static EventBadge getEventBadge(int amount) {
        return Arrays.stream(values())
                .sorted(descendOrderByPrice())
                .filter(badge -> badge.price <= amount)
                .findFirst()
                .orElse(NONE);
    }

    public String getName() {
        return name;
    }
    
    private static Comparator<EventBadge> descendOrderByPrice() {
        return (o1, o2) -> o2.price - o1.price;
    }

}
