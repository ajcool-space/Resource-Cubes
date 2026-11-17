package space.ajcool.resourcecubes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.cubemob.SulfurCube;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ResourceDropLoot
{
    public static ResourceKey<LootTable> lootTableKeyFor(Block block)
    {
        Identifier identifier = BuiltInRegistries.BLOCK.getKey(block);

        return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(
                ResourceCubes.MOD_ID, identifier.getNamespace() + "/" + identifier.getPath()
        ));
    }

    public static void drop(SulfurCube sulfurCube, ServerLevel level)
    {
        ItemStack bodyItemStack = sulfurCube.getItemBySlot(EquipmentSlot.BODY);
        if (!(bodyItemStack.getItem() instanceof BlockItem blockItem)) return;

        BlockPos blockPos = sulfurCube.blockPosition();
        BlockState blockState = blockItem.getBlock().defaultBlockState();

        LootTable lootTable = level.getServer().reloadableRegistries()
                .getLootTable(lootTableKeyFor(blockState.getBlock()));

        List<ItemStack> drops = lootTable == LootTable.EMPTY
                ? Block.getDrops(blockState, level, blockPos, null)
                : lootTable.getRandomItems(
                        new LootParams.Builder(level)
                        .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos))
                        .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                        .withParameter(LootContextParams.BLOCK_STATE, blockState)
                        .withOptionalParameter(LootContextParams.THIS_ENTITY, sulfurCube)
                        .create(LootContextParamSets.BLOCK)
        );

        Vec3 equipmentSpawnOffset = sulfurCube.getAttachments().getAverage(EntityAttachment.PASSENGER);
        drops.forEach(drop -> sulfurCube.spawnAtLocation(level, drop.copyWithCount(1), equipmentSpawnOffset));

        if (bodyItemStack.is(ResourceCubesItemTags.RESOURCE_CUBES_DROP_EXPERIENCE))
            blockState.spawnAfterBreak(level, sulfurCube.blockPosition(), ItemStack.EMPTY, true);

        sulfurCube.playSound(SoundEvents.SULFUR_CUBE_EJECT);
    }
}
