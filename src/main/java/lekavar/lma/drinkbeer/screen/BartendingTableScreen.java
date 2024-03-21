package lekavar.lma.drinkbeer.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BartendingTableScreen extends HandledScreen<ScreenHandler> {
    private static final Identifier BARTENDING_TABLE_GUI_BASIC = new Identifier("drinkbeer", "textures/gui/container/bartending_table.png");
    BartendingTableScreenHandler screenHandler;

    public BartendingTableScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        screenHandler = (BartendingTableScreenHandler) handler;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        context.drawTexture(BARTENDING_TABLE_GUI_BASIC, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        renderBackground(drawContext);
        super.render(drawContext, mouseX, mouseY, delta);
        drawMouseoverTooltip(drawContext, mouseX, mouseY);
    }
}
