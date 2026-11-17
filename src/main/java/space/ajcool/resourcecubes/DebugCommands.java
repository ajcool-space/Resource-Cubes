package space.ajcool.resourcecubes;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.cubemob.SulfurCube;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class DebugCommands
{
    public static void register()
    {
        CommandRegistrationCallback.EVENT.register(((dispatcher, buildContext, selection) ->
        {
            dispatcher.register(Commands.literal("resourcecubes")
                    .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                    .then(Commands.literal("forcedrop").executes(ctx -> {
                        CommandSourceStack source = ctx.getSource();
                        ServerLevel level = source.getLevel();

                        List<? extends SulfurCube> cubes = level.getEntities(EntityTypes.SULFUR_CUBE,
                                cube -> cube.getItemBySlot(EquipmentSlot.BODY).is(ResourceCubesItemTags.RESOURCE_CUBES_FARMABLE));

                        cubes.forEach(cube -> ((ResourceCube) cube).resourceCubes$setDropTime(0));

                        source.sendSuccess(() -> Component.literal("Reset " + cubes.size() + " cube timers to 0s."), true);

                        return cubes.size();
                    }))
                    .then(Commands.literal("spawnall").executes(ctx ->
                    {
                        CommandSourceStack source = ctx.getSource();
                        ServerLevel level = source.getLevel();
                        Direction facing = Direction.fromYRot(source.getRotation().y);
                        Direction right = facing.getClockWise();
                        BlockPos start = BlockPos.containing(source.getPosition()).relative(facing, 2);

                        int count = 0;

                        for (Holder<Item> item : BuiltInRegistries.ITEM.getTagOrEmpty(ResourceCubesItemTags.RESOURCE_CUBES_FARMABLE)) {
                            SulfurCube sulfurCube = EntityTypes.SULFUR_CUBE.create(level, EntitySpawnReason.COMMAND);
                            if (sulfurCube == null) continue;

                            int column = count % 10;
                            int row = count / 10;
                            BlockPos pos = start.relative(facing, column * 2).relative(right, row * 2);

                            sulfurCube.snapTo(Vec3.atBottomCenterOf(pos), facing.toYRot(), 0);
                            sulfurCube.setItemSlot(EquipmentSlot.BODY, new ItemStack(item));
                            sulfurCube.setPersistenceRequired();
                            sulfurCube.setSize(2, true);

                            level.addFreshEntity(sulfurCube);

                            count++;
                        }

                        int finalCount = count;
                        source.sendSuccess(() -> Component.literal("Spawned " + finalCount + " cubes."), true);

                        return count;
                    })));
        }));
    }
}
