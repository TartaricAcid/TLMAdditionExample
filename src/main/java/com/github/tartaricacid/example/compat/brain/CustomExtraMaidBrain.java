package com.github.tartaricacid.example.compat.brain;

import com.github.tartaricacid.touhoulittlemaid.api.entity.ai.IExtraMaidBrain;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.Collections;
import java.util.List;

public class CustomExtraMaidBrain implements IExtraMaidBrain {
    @Override
    public List<MemoryModuleType<?>> getExtraMemoryTypes() {
        // 这里仅做演示，返回一个空的列表
        // Just for demonstration, return an empty list
        return Collections.emptyList();
    }

    @Override
    public List<Pair<Integer, BehaviorControl<? super EntityMaid>>> getCoreBehaviors() {
        // 这里仅做演示，返回一个空的列表
        // Just for demonstration, return an empty list
        return Collections.emptyList();
    }
}
