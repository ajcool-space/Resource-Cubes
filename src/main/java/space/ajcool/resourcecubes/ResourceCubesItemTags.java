package space.ajcool.resourcecubes;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ResourceCubesItemTags
{
    public static final TagKey<Item> RESOURCE_CUBES_FARMABLE = bind("resource_cubes_farmable");
    public static final TagKey<Item> RESOURCE_CUBES_DROP_EXPERIENCE = bind("resource_cubes_drop_experience");

    private static TagKey<Item> bind(String name)
    {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ResourceCubes.MOD_ID, name));
    }
}
