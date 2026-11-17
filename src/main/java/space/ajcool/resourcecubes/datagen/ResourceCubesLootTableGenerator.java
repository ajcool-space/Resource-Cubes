package space.ajcool.resourcecubes.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.jspecify.annotations.NonNull;
import space.ajcool.resourcecubes.ResourceDropLoot;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ResourceCubesLootTableGenerator extends SimpleFabricLootTableSubProvider
{
    private static final Map<Block, ItemLike> LEAF_SAPLINGS = Map.ofEntries(
            Map.entry(Blocks.OAK_LEAVES, Blocks.OAK_SAPLING),
            Map.entry(Blocks.SPRUCE_LEAVES, Blocks.SPRUCE_SAPLING),
            Map.entry(Blocks.BIRCH_LEAVES, Blocks.BIRCH_SAPLING),
            Map.entry(Blocks.JUNGLE_LEAVES, Blocks.JUNGLE_SAPLING),
            Map.entry(Blocks.ACACIA_LEAVES, Blocks.ACACIA_SAPLING),
            Map.entry(Blocks.DARK_OAK_LEAVES, Blocks.DARK_OAK_SAPLING),
            Map.entry(Blocks.PALE_OAK_LEAVES, Blocks.PALE_OAK_SAPLING),
            Map.entry(Blocks.CHERRY_LEAVES, Blocks.CHERRY_SAPLING),
            Map.entry(Blocks.RED_POPLAR_LEAVES, Blocks.POPLAR_SAPLING),
            Map.entry(Blocks.ORANGE_POPLAR_LEAVES, Blocks.POPLAR_SAPLING),
            Map.entry(Blocks.YELLOW_POPLAR_LEAVES, Blocks.POPLAR_SAPLING),
            Map.entry(Blocks.AZALEA_LEAVES, Blocks.AZALEA),
            Map.entry(Blocks.FLOWERING_AZALEA_LEAVES, Blocks.FLOWERING_AZALEA)
    );

    public ResourceCubesLootTableGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture)
    {
        super(output, registryLookupFuture, LootContextParamSets.BLOCK);
    }

    @Override
    public void generate(@NonNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer)
    {
        biConsumer.accept(ResourceDropLoot.lootTableKeyFor(Blocks.GRAVEL), singleItem(Items.FLINT));
        biConsumer.accept(ResourceDropLoot.lootTableKeyFor(Blocks.BEEHIVE), singleItem(Items.HONEYCOMB));
        biConsumer.accept(ResourceDropLoot.lootTableKeyFor(Blocks.BEE_NEST), singleItem(Items.HONEYCOMB));
        biConsumer.accept(ResourceDropLoot.lootTableKeyFor(Blocks.BROWN_MUSHROOM_BLOCK), singleItem(Items.BROWN_MUSHROOM));
        biConsumer.accept(ResourceDropLoot.lootTableKeyFor(Blocks.RED_MUSHROOM_BLOCK), singleItem(Items.RED_MUSHROOM));

        biConsumer.accept(ResourceDropLoot.lootTableKeyFor(Blocks.MANGROVE_LEAVES), singleItem(Items.STICK));

        LEAF_SAPLINGS.forEach((leaves, sapling) -> {
            int saplingWeight = leaves == Blocks.JUNGLE_LEAVES ? 5 : 10;
            boolean hasApples = leaves == Blocks.OAK_LEAVES || leaves == Blocks.DARK_OAK_LEAVES;

            if (hasApples)
            {
                biConsumer.accept(ResourceDropLoot.lootTableKeyFor(leaves),
                        lootPool(
                                weightedItem(sapling, saplingWeight),
                                weightedItem(Items.STICK, 4),
                                weightedItem(Items.APPLE, 1)
                        )
                );
            }
            else
            {
                biConsumer.accept(ResourceDropLoot.lootTableKeyFor(leaves),
                        lootPool(
                                weightedItem(sapling, saplingWeight),
                                weightedItem(Items.STICK, 4)
                        )
                );
            }
        });
    }

    private static LootTable.Builder lootPool(LootPoolEntryContainer.Builder<?>... entries)
    {
        LootPool.Builder pool = LootPool.lootPool().setRolls(ContextIntProviders.exactly(1));
        for (LootPoolEntryContainer.Builder<?> entry : entries) pool.add(entry);
        return LootTable.lootTable().withPool(pool);
    }

    private static LootPoolEntryContainer.Builder<?> weightedItem(ItemLike item, int weight)
    {
        return LootItem.lootTableItem(item).setWeight(weight);
    }

    private static LootTable.Builder singleItem(ItemLike item)
    {
        return lootPool(weightedItem(item, 1));
    }
}
