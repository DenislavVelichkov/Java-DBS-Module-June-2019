package spaceStation.models.bags;

import java.util.Collection;
import java.util.LinkedHashSet;

public class Backpack implements Bag {
    private Collection<String> items;

    public Backpack() {
        this.items = new LinkedHashSet<>();
    }

    @Override
    public Collection<String> getItems() {
        return this.items;
    }
}
