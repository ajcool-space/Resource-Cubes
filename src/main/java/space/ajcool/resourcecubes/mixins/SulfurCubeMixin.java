package space.ajcool.resourcecubes.mixins;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.cubemob.AbstractCubeMob;
import net.minecraft.world.entity.monster.cubemob.SulfurCube;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.ajcool.resourcecubes.ResourceCube;
import space.ajcool.resourcecubes.ResourceCubesItemTags;
import space.ajcool.resourcecubes.ResourceDropLoot;
import space.ajcool.resourcecubes.ResourceRarity;

import java.util.Map;

import static space.ajcool.resourcecubes.ResourceCubes.MOD_ID;

@Mixin(SulfurCube.class)
public abstract class SulfurCubeMixin extends AbstractCubeMob implements ResourceCube
{
    @Unique
    private int resourceCubes$dropTime = -1;

    protected SulfurCubeMixin(EntityType<? extends AbstractCubeMob> type, Level level) {
        super(type, level);
    }

    @Override
    public void resourceCubes$setDropTime(int dropTime)
    {
        this.resourceCubes$dropTime = dropTime;
    }

    @Inject(method = "customServerAiStep", at = @At("TAIL"))
    private void resourceCubes$customServerAiStep(ServerLevel level, CallbackInfo ci)
    {
        if (FabricLoader.getInstance().isDevelopmentEnvironment())
        {
            this.setCustomName(Component.literal(this.resourceCubes$dropTime < 0
                    ? "00:00"
                    : StringUtil.formatTickDuration(this.resourceCubes$dropTime, 20f)));
            this.setCustomNameVisible(true);
        }

        if (this.resourceCubes$dropTime == -1) return;

        if (this.resourceCubes$dropTime > 0) this.resourceCubes$dropTime--;

        if (this.resourceCubes$dropTime <= 0 && this.isAlive() && this.shouldDropLoot(level))
        {
            ItemStack swallowedItem = this.getItemBySlot(EquipmentSlot.BODY);
            if (!swallowedItem.is(ResourceCubesItemTags.RESOURCE_CUBES_FARMABLE)) return;

            ResourceDropLoot.drop((SulfurCube) (Object) this,level);

            this.resourceCubes$dropTime = this.resourceCubes$pickNextResourceDropTime();
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void resourceCubes$addAdditionalSaveData(final ValueOutput output, CallbackInfo ci)
    {
        output.putInt(MOD_ID + ":drop_time", this.resourceCubes$dropTime);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void resourceCubes$readAdditionalSaveData(final ValueInput input, CallbackInfo ci)
    {
        this.resourceCubes$dropTime = input.getIntOr(MOD_ID + ":drop_time", -1);
    }

    @Inject(method = "collectEquipmentChanges", at = @At("HEAD"))
    private void resourceCubes$collectEquipmentChanges(Map<EquipmentSlot, ItemStack> lastEquipmentItems, CallbackInfoReturnable<Map<EquipmentSlot, ItemStack>> cir)
    {
        ItemStack previous = lastEquipmentItems.get(EquipmentSlot.BODY);
        ItemStack current = this.getItemBySlot(EquipmentSlot.BODY);

        if (previous.isEmpty() && this.resourceCubes$dropTime >= 0) return;
        if (!this.equipmentHasChanged(previous, current)) return;

        this.resourceCubes$dropTime = this.resourceCubes$pickNextResourceDropTime();
    }

    @Unique
    private int resourceCubes$pickNextResourceDropTime()
    {
        ItemStack swallowedItem = this.getItemBySlot(EquipmentSlot.BODY);
        if (!swallowedItem.is(ResourceCubesItemTags.RESOURCE_CUBES_FARMABLE)) return -1;
        return ResourceRarity.of(swallowedItem).pickTickCount(this.random);
    }
}
