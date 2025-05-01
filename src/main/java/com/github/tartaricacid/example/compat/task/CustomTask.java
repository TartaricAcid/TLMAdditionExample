package com.github.tartaricacid.example.compat.task;

import com.github.tartaricacid.example.Example;
import com.github.tartaricacid.touhoulittlemaid.api.task.IMaidTask;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * CustomTask 类实现了 IMaidTask 接口，用于新建一个自定义女仆的工作任务。
 * <p>
 * The CustomTask class implements the IMaidTask interface to define a new custom maid work task.
 */
public class CustomTask implements IMaidTask {
    /**
     * 唯一标识符，用于区分不同的任务
     * <p>
     * Unique identifier for distinguishing different tasks
     */
    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Example.MOD_ID, "custom_task");

    /**
     * 任务图标，这里使用苹果作为示例
     * <p>
     * Task icon, using an apple as an example
     */
    private static final ItemStack ICON = Items.APPLE.getDefaultInstance();

    /**
     * 获取任务的唯一标识符
     * <p>
     * Get the unique identifier of the task
     */
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    /**
     * 获取任务的图标
     * <p>
     * Get the icon of the task
     */
    @Override
    public ItemStack getIcon() {
        return ICON;
    }

    /**
     * 获取女仆在该任务时的音效，可以为 null
     * <p>
     * Get the sound when the maid in this task, can be null
     */
    @Override
    @Nullable
    public SoundEvent getAmbientSound(EntityMaid maid) {
        return null;
    }

    /**
     * 创建女仆 AI，这一块通过 Minecraft 原版的 BehaviorControl 来实现，可以参考 com.github.tartaricacid.touhoulittlemaid.entity.task.TaskIdle
     * <p>
     * Create maid AI, this part is implemented through Minecraft's BehaviorControl, you can refer to com.github.tartaricacid.touhoulittlemaid.entity.task.TaskIdle
     */
    @Override
    public List<Pair<Integer, BehaviorControl<? super EntityMaid>>> createBrainTasks(EntityMaid maid) {
        // 返回一个空的任务列表，表示没有自定义 AI 行为
        // Return an empty task list, meaning no custom AI behavior
        return List.of();
    }
}
