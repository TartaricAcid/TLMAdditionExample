package com.github.tartaricacid.example.compat;

import com.github.tartaricacid.example.compat.bauble.CustomBauble;
import com.github.tartaricacid.example.compat.event.AppleInteractMaidEvent;
import com.github.tartaricacid.example.compat.task.CustomTask;
import com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid;
import com.github.tartaricacid.touhoulittlemaid.api.LittleMaidExtension;
import com.github.tartaricacid.touhoulittlemaid.client.overlay.MaidTipsOverlay;
import com.github.tartaricacid.touhoulittlemaid.entity.task.TaskManager;
import com.github.tartaricacid.touhoulittlemaid.item.bauble.BaubleManager;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;

/**
 * 这个类是实现女仆模型相关功能的入口类。<br>
 * 这个需要实现 ILittleMaid 接口，并使用 @LittleMaidExtension 注解，女仆模组会在合适的时间自动实例化这个类，并进行相关注册。<br>
 * 注解的好处就在于，如果没有安装女仆模组时，该类不会被加载，保证了模组运行的稳定性。<br>
 * <br>
 * This class is the entry point for implementing maid model related features.<br>
 * It needs to implement the ILittleMaid interface and use the @LittleMaidExtension annotation.<br>
 * The maid mod will automatically instantiate this class at the appropriate time and perform related registrations.<br>
 * The benefit of the annotation is that if the maid mod is not installed, this class will not be loaded, ensuring the stability of the mod's operation.<br>
 */
@LittleMaidExtension
public class LittleMaidCompat implements ILittleMaid {
    // 默认构造函数，女仆模组会在合适的时间调用这个构造函数。可以在这里注册女仆专属的事件
    // Default constructor, the maid mod will call this constructor at the appropriate time. You can register maid-specific events here.
    public LittleMaidCompat() {
        MinecraftForge.EVENT_BUS.register(new AppleInteractMaidEvent());
    }

    /**
     * 绑定女仆饰品的方法，将自定义饰品与物品进行绑定。
     * <p>
     * Method to bind maid baubles, binding custom baubles to items.
     */
    @Override
    public void bindMaidBauble(BaubleManager manager) {
        // 将木棍与自定义饰品绑定
        // Bind stick item with custom bauble
        manager.bind(Items.STICK, new CustomBauble());
    }

    /**
     * 注册女仆工作任务的方法
     * <p>
     * Method to add maid work tasks
     */
    @Override
    public void addMaidTask(TaskManager manager) {
        // 添加自定义任务
        // Add custom task
        manager.add(new CustomTask());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addMaidTips(MaidTipsOverlay maidTipsOverlay) {
        // 前面的是语言文件的 key，后面的是物品
        // The first part is the key in the language file, and the second part is the item
        maidTipsOverlay.addTips("overlay.example.apple.tips", Items.APPLE);
    }
}
