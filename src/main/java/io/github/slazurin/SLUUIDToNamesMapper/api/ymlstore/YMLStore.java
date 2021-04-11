package io.github.slazurin.SLUUIDToNamesMapper.api.ymlstore;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.slazurin.SLUUIDToNamesMapper.SLUUIDToNamesMapperPlugin;

public class YMLStore {
    private final SLUUIDToNamesMapperPlugin plugin;
    private FileConfiguration cache;
    private final File store;
    private static final String storeFileName = "UUIDMap.yml";
    

    public YMLStore(SLUUIDToNamesMapperPlugin plugin) {
        this.plugin = plugin;
        this.store = new File(this.plugin.getDataFolder(), storeFileName);
        this.createStoreIfNotExists();
        this.cache = YamlConfiguration.loadConfiguration(this.store);
    }

    private void createStoreIfNotExists() {
        if (!this.store.exists()) {
            this.store.getParentFile().mkdirs();
            this.plugin.saveResource(storeFileName, false);
        }
    }

    public FileConfiguration getCache() {
        return cache;
    }

    public boolean saveStore() {
        try {
            this.getCache().save(store);
            return true;
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "{0}Couldn't write to file!", ChatColor.RED);
        }
        return false;
    }
}
