package net.abyssdev.abyssbounties.menu.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.abyssdev.abysslib.builders.ItemBuilder;

@Getter
@AllArgsConstructor
public final class PageItem {

    private final ItemBuilder item;
    private final int slot;

}