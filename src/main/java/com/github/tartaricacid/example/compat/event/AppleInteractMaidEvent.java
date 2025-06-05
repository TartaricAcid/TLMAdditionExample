package com.github.tartaricacid.example.compat.event;

import com.github.tartaricacid.touhoulittlemaid.api.event.InteractMaidEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * 注意这个类不要用 @Mod.SubscribeEvent 注解，因为这样会在女仆模组不存在时也会加载这个类 <br>
 * <p>
 * This class should not use the @Mod.SubscribeEvent annotation, as it would load this class even when the maid mod does not exist.<br>
 */
public class AppleInteractMaidEvent {
    /**
     * 当女仆与苹果交互时触发的事件处理方法。<br>
     * <p>
     * When the maid interacts with an apple, this event handling method is triggered.<br>
     */
    @SubscribeEvent
    public void onAppleInteract(InteractMaidEvent event) {
        Player player = event.getPlayer();
        Level world = event.getWorld();
        EntityMaid maid = event.getMaid();
        // 主手的物品
        // Item in the main hand
        ItemStack stack = event.getStack();
        // 这里不需要判断女仆是否是自己的女仆，因为女仆模组会自动处理这个逻辑。
        // No need to check if the maid is your own maid, as the maid mod will handle this logic automatically.
        if (stack.is(Items.APPLE)) {
            // 播放吃东西的音效
            // Play the eating sound effect
            world.playSound(null, maid.getX(), maid.getY(), maid.getZ(), maid.getEatingSound(stack), SoundSource.NEUTRAL, 1,
                    1 + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F);
            // 给女仆添加抗性效果，持续时间为 60 秒，效果等级为 1
            // Add resistance effect to the maid, lasting for 60 seconds, level 1
            maid.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 60, 1));
            stack.shrink(1);
            // 取消后续操作，避免打开女仆 GUI
            // Cancel subsequent operations to avoid opening the maid GUI
            event.setCanceled(true);
        }
    }
}
