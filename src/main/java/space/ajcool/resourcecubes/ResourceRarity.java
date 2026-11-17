package space.ajcool.resourcecubes;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Bees;

import java.util.Locale;

public enum ResourceRarity
{
    VERY_COMMON(2, 4),
    COMMON(5, 10),
    UNCOMMON(8, 12),
    RARE(11, 15),
    VERY_RARE(14, 20);

    private static final int TICKS_PER_MINUTE = 20 * TimeUtil.SECONDS_PER_MINUTE;

    public final TagKey<Item> tag;
    private final int minMinutes, maxMinutes;

    ResourceRarity(int minMinutes, int maxMinutes)
    {
        String path = "resource_cubes_rarity/" + name().toLowerCase(Locale.ROOT);
        Identifier identifier = Identifier.fromNamespaceAndPath(ResourceCubes.MOD_ID, path);

        this.tag = TagKey.create(Registries.ITEM, identifier);

        this.minMinutes = minMinutes;
        this.maxMinutes = maxMinutes;
    }

    public static ResourceRarity of(ItemStack itemStack)
    {
        Bees bees = itemStack.get(DataComponents.BEES);
        if (bees != null) return switch(bees.bees().size())
        {
            case 0 -> VERY_RARE;
            case 1 -> RARE;
            case 2 -> UNCOMMON;
            default -> COMMON;
        };

        for (ResourceRarity r : values()) if (itemStack.is(r.tag)) return r;

        return VERY_COMMON;
    }

    public int pickTickCount(RandomSource random)
    {
        return random.nextIntBetweenInclusive(minMinutes * TICKS_PER_MINUTE, maxMinutes * TICKS_PER_MINUTE);
    }
}
