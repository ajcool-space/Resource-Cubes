package space.ajcool.resourcecubes.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemIds;
import net.minecraft.tags.ItemTags;
import org.jspecify.annotations.NonNull;
import space.ajcool.resourcecubes.ResourceCubesItemTags;
import space.ajcool.resourcecubes.ResourceRarity;

import java.util.concurrent.CompletableFuture;

public class ResourceCubesItemTagProvider extends FabricTagsProvider.ItemTagsProvider
{
    ResourceCubesItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries)
    {
        super(output, registries);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider registries)
    {
        // Modded Tags
        var farmable = builder(ResourceCubesItemTags.RESOURCE_CUBES_FARMABLE);
        for (ResourceRarity resourceRarity : ResourceRarity.values()) farmable.addTag(resourceRarity.tag);

        farmable.add(
                BlockItemIds.BEE_NEST,
                BlockItemIds.BEEHIVE
        );

        builder(ResourceCubesItemTags.RESOURCE_CUBES_DROP_EXPERIENCE)
                .add(
                        BlockItemIds.SCULK
                );


        builder(ResourceRarity.VERY_COMMON.tag)
                .add(
                        BlockItemIds.CLAY, // For Morgan :)
                        BlockItemIds.NETHER_QUARTZ_ORE,
                        BlockItemIds.MELON,
                        BlockItemIds.GRAVEL,
                        BlockItemIds.BROWN_MUSHROOM_BLOCK,
                        BlockItemIds.RED_MUSHROOM_BLOCK
                )
                .forceAddTag(ItemTags.LEAVES)
                .forceAddTag(ItemTags.COAL_ORES);

        builder(ResourceRarity.COMMON.tag)
                .add(
                        BlockItemIds.NETHER_GOLD_ORE,
                        BlockItemIds.SCULK
                )
                .forceAddTag(ItemTags.COPPER_ORES)
                .forceAddTag(ItemTags.IRON_ORES);

        builder(ResourceRarity.UNCOMMON.tag)
                .add(
                        BlockItemIds.AMETHYST_CLUSTER
                )
                .forceAddTag(ItemTags.REDSTONE_ORES)
                .forceAddTag(ItemTags.EMERALD_ORES);

        builder(ResourceRarity.RARE.tag)
                .add(
                        BlockItemIds.CREAKING_HEART,
                        BlockItemIds.GOLD_ORE,
                        BlockItemIds.DEEPSLATE_GOLD_ORE
                )
                .forceAddTag(ItemTags.LAPIS_ORES);

        builder(ResourceRarity.VERY_RARE.tag)
                .forceAddTag(ItemTags.DIAMOND_ORES);

        // Vanilla Tags

        builder(ItemTags.SULFUR_CUBE_SWALLOWABLE)
                .addTag(ResourceCubesItemTags.RESOURCE_CUBES_FARMABLE);

        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_BOUNCY)
                .add(
                        BlockItemIds.CREAKING_HEART,
                        BlockItemIds.BEE_NEST,
                        BlockItemIds.BEEHIVE
                );

        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_FAST_FLAT)
                .add(
                        BlockItemIds.SCULK
                )
                .forceAddTag(
                        ItemTags.LEAVES
                );

        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_REGULAR)
                .add(
                        BlockItemIds.GRAVEL
                );

        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
                .add(
                        BlockItemIds.AMETHYST_CLUSTER
                );

    }
}