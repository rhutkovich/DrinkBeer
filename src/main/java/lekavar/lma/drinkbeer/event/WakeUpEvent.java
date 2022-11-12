package lekavar.lma.drinkbeer.event;

import lekavar.lma.drinkbeer.DrinkBeer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class WakeUpEvent {
    public static void onStopSleeping(LivingEntity entity, BlockPos blockPos) {
        if (entity instanceof ServerPlayerEntity player &&
        player.hasStatusEffect(DrinkBeer.DRUNK) && player.getWorld().getTimeOfDay() % 24000 == 0) {
            int amp = entity.getStatusEffect(DrinkBeer.DRUNK).getAmplifier();
            int time = entity.getStatusEffect(DrinkBeer.DRUNK).getDuration() * amp / 10;
            player.removeStatusEffect(DrinkBeer.DRUNK);
            player.removeStatusEffect(StatusEffects.NAUSEA);
            player.removeStatusEffect(StatusEffects.SLOWNESS);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, time, 1, false, false));
        }
    }
}
