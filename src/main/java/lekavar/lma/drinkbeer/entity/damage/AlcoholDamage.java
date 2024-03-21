package lekavar.lma.drinkbeer.entity.damage;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;

public class AlcoholDamage extends DamageSource {
    public AlcoholDamage() {
        super(RegistryEntry.of(new DamageType("drinkbeer.alcohol", 0.0F)));
    }

    @Override
    public Text getDeathMessage(LivingEntity entity) {
        String str = "death.attack." + this.getName();
        return Text.translatable(str, entity.getDisplayName());
    }
}