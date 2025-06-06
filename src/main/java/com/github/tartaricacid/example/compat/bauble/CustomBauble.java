package com.github.tartaricacid.example.compat.bauble;

import com.github.tartaricacid.touhoulittlemaid.api.bauble.IMaidBauble;
import com.github.tartaricacid.touhoulittlemaid.api.event.MaidDamageEvent;
import com.github.tartaricacid.touhoulittlemaid.api.event.MaidDeathEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.util.ItemsUtil;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * 这个类实现了 IMaidBauble 接口，用于自定义女仆饰品的行为。<br>
 * IMaidBauble 接口的内容较少，一般我们推荐使用事件系统来实现饰品的效果。<br>
 * <br>
 * This class implements the IMaidBauble interface to customize the behavior of maid baubles.<br>
 * The IMaidBauble interface has few contents, and we generally recommend using the event system to
 * implement the effects of baubles.<br>
 */
public class CustomBauble implements IMaidBauble {
    /**
     * 构造方法，注册事件监听器到 MinecraftForge 事件总线。
     * <p>
     * Constructor, registers event listeners to the MinecraftForge event bus.
     */
    public CustomBauble() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * 每 tick 调用的方法，为女仆定期添加发光效果。
     * Called every tick, periodically adds the glowing effect to the maid.
     *
     * @param maid       女仆实体 / Maid entity
     * @param baubleItem 饰品物品实例 / Bauble item instance
     */
    @Override
    public void onTick(EntityMaid maid, ItemStack baubleItem) {
        if (maid.tickCount % 150 == 0) {
            maid.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200));
        }
    }

    /**
     * 女仆受到伤害时触发的事件处理方法。
     * 当女仆受到火焰伤害且佩戴此饰品时，给予发光效果并损坏饰品。
     * <p>
     * Event handler triggered when the maid takes damage.
     * If the maid takes fire damage and is wearing this bauble, grants glowing effect and damages the bauble.
     *
     * @param event 女仆伤害事件 / Maid damage event
     */
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onMaidDamage(MaidDamageEvent event) {
        EntityMaid maid = event.getMaid();
        DamageSource source = event.getSource();
        if (!source.is(DamageTypeTags.IS_FIRE)) {
            return;
        }
        // ItemsUtil 是一个工具类，提供了一些与物品栏操作相关的静态方法，比如这里的 getBaubleSlotInMaid 就用于寻找女仆佩戴的对应当前饰品的槽位
        // 如果女仆佩戴多个相同饰品，那么 getBaubleSlotInMaid 方法会返回第一个找到的槽位
        // ItemsUtil is a utility class that provides static methods related to inventory operations
        // Such as getBaubleSlotInMaid, which finds the slot of the corresponding bauble worn by the maid.
        // If the maid wears multiple identical baubles, the getBaubleSlotInMaid method will return the first found slot.
        int slot = ItemsUtil.getBaubleSlotInMaid(maid, this);
        if (slot >= 0) {
            maid.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200));
            // 可以使用此方法来获取女仆的饰品
            // Can use this method to get the maid's bauble
            ItemStack stack = maid.getMaidBauble().getStackInSlot(slot);
            // 消耗耐久，其中 maid.sendItemBreakMessage(stack) 方法用于女仆损坏物品时，向周围的玩家发生物品损坏的音效和粒子
            // Consume durability, where maid.sendItemBreakMessage(stack) method is used to play the item break sound and particle effect
            // to nearby players when the maid's item is damaged
            stack.hurtAndBreak(1, maid, m -> maid.sendItemBreakMessage(stack));
            event.setCanceled(true);
        }
    }
}
