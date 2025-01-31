package lekavar.lma.drinkbeer.screen;

import lekavar.lma.drinkbeer.block.TradeboxBlock;
import lekavar.lma.drinkbeer.manager.TradeboxManager;
import lekavar.lma.drinkbeer.networking.NetWorking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

public class TradeBoxScreen extends HandledScreen<ScreenHandler> {
    private static final Identifier TRADE_BOX_GUI = new Identifier("drinkbeer", "textures/gui/container/trade_box.png");
    TradeBoxScreenHandler screenHandler;

    public TradeBoxScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        screenHandler = (TradeBoxScreenHandler) handler;
    }

    @Override
    protected void drawBackground(DrawContext drawContext, float delta, int mouseX, int mouseY) {
        drawContext.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawContext.drawTexture(TRADE_BOX_GUI, x, y, 0, 0, backgroundWidth, backgroundHeight);
        if (screenHandler.isCooling()) {
            drawContext.drawTexture(TRADE_BOX_GUI, x + 84, y + 25, 178, 38, 72, 36);
            String timeStr = convertTickToTime(screenHandler.getCoolingTime());
            drawContext.drawText(textRenderer, timeStr, x + 114, y + 39, new Color(64, 64, 64, 255).getRGB(), true);
        } else if (screenHandler.isTrading()) {
            if (isPointWithinBounds(157, 6, 13, 13, (double) mouseX, (double) mouseY)) {
                drawContext.drawTexture(TRADE_BOX_GUI, x + 155, y + 4, 178, 19, 16, 16);
            } else {
                drawContext.drawTexture(TRADE_BOX_GUI, x + 155, y + 4, 178, 0, 16, 16);
            }
        }
        if (!screenHandler.isCooling()) {
            Language language = Language.getInstance();
            String youStr = language.get("drinkbeer.resident.you");
            drawContext.drawText(textRenderer, youStr, x + 85, y + 16, new Color(64, 64, 64, 255).getRGB(), true);
            String locationAndResidentStr =
                    language.get(TradeboxManager.getLocationTranslationKey(this.screenHandler.getLocationId()))
                            + "-" +
                            language.get(TradeboxManager.getResidentTranslationKey(this.screenHandler.getResidentId()));
            drawContext.drawText(textRenderer, locationAndResidentStr, x + 85, y + 63, new Color(64, 64, 64, 255).getRGB(), true);
        }
    }

    public String convertTickToTime(int tick) {
        String result;
        if (tick > 0) {
            double time = (double) tick / 20;
            int m = (int) (time / 60);
            int s = (int) (time % 60);
            result = m + ":" + s;
        } else result = "";
        return result;
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        renderBackground(drawContext);
        super.render(drawContext, mouseX, mouseY, delta);
        drawMouseoverTooltip(drawContext, mouseX, mouseY);
    }

    @Override
    protected boolean isPointWithinBounds(int xPosition, int yPosition, int width, int height, double pointX, double pointY) {
        return super.isPointWithinBounds(xPosition, yPosition, width, height, pointX, pointY);
    }

    @Override
    protected void init() {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        this.addDrawableChild(new TexturedButtonWidget(x + 156, y + 5, 15, 15, 210, 0, 0, TRADE_BOX_GUI, (buttonWidget) -> {
            if (screenHandler.isTrading()) {
                BlockPos pos = getHitTradeBoxBlockPos();
                if (pos != null)
                    NetWorking.sendRefreshTradebox(pos);
            }
        }));
        super.init();
    }

    private BlockPos getHitTradeBoxBlockPos() {
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;
        if (hit.getType().equals(HitResult.Type.BLOCK)) {
            BlockHitResult blockHit = (BlockHitResult) hit;
            BlockPos blockPos = blockHit.getBlockPos();
            BlockState blockState = client.world.getBlockState(blockPos);
            Block block = blockState.getBlock();
            if (block instanceof TradeboxBlock) {
                return blockPos;
            }
        }
        return null;
    }
}
