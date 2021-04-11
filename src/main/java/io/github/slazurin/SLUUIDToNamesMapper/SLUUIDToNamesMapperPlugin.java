package io.github.slazurin.SLUUIDToNamesMapper;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.slazurin.SLUUIDToNamesMapper.api.SLUUIDToNamesMapperAPI;
import io.github.slazurin.SLUUIDToNamesMapper.listeners.SLUUIDToNamesMapperListeners;

public class SLUUIDToNamesMapperPlugin extends JavaPlugin
{
    private SLUUIDToNamesMapperAPI api;

    @Override
    public void onEnable() {
        this.api = new SLUUIDToNamesMapperAPI(this);
        this.registerListeners();
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new SLUUIDToNamesMapperListeners(this), this);
    }

    @Override
    public void onDisable() {
        
    }

    public SLUUIDToNamesMapperAPI getApi() {
        return this.api;
    }
}
