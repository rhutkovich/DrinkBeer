package lekavar.lma.drinkbeer.statuseffects;

import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.awt.*;

public class DrunkFrostWalkerStatusEffect extends StatusEffect {
    public DrunkFrostWalkerStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, new Color(30, 144, 255, 255).getRGB());
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            FrostWalkerEnchantment.freezeWater(entity, entity.getWorld(), new BlockPos(getEntityPosInt(entity)), 1);
        }
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        DrunkStatusEffect.addStatusEffect(entity);
    }

    private Vec3i getEntityPosInt(LivingEntity entity) {
        Vec3d entityPositionDouble = entity.getPos();
        return new Vec3i(
                (int) entityPositionDouble.x,
                (int) entityPositionDouble.y,
                (int) entityPositionDouble.z);
    }
}
