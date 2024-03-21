package lekavar.lma.drinkbeer.item;

import lekavar.lma.drinkbeer.util.beer.BeerRecipe;
import lekavar.lma.drinkbeer.util.beer.Beers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class RecipeBoardBlockItem extends BlockItem {
    public RecipeBoardBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    public RecipeBoardBlockItem(Block block) {
        super(block, new Item.Settings().maxCount(1));
    }

    public void appendRecipeBoardTooltip(ItemStack stack, List<Text> tooltip) {
        Beers beer = Beers.byRecipeBoardBlock(this.getBlock());
        if(beer == null)
            return;
        BeerRecipe beerRecipe = beer.getBeerRecipe();
        for (Map.Entry<List<Item>, Integer> materials : beerRecipe.getMaterialMap().entrySet()) {
            int materialNum = materials.getValue();
            List<Item> materialList = materials.getKey();
            Text text = Text.translatable(materialList.get(0).getTranslationKey());
            if (materialList.size() > 1) {
                for (int i = 1; i < materialList.size(); i++) {
                    ((MutableText) text).append(" / ").append(Text.translatable(materialList.get(i).getTranslationKey()));
                }
            }
            ((MutableText) text).append(" x ").append(String.valueOf(materialNum));
            ((MutableText) text).formatted(Formatting.BLUE);
            tooltip.add(text);
        }
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        appendRecipeBoardTooltip(stack, tooltip);
    }
}
