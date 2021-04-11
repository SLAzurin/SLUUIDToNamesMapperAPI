package io.github.slazurin.SLUUIDToNamesMapper.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

import io.github.slazurin.SLUUIDToNamesMapper.SLUUIDToNamesMapperPlugin;
import io.github.slazurin.SLUUIDToNamesMapper.api.ymlstore.YMLStore;

public class SLUUIDToNamesMapperAPI {
    private SLUUIDToNamesMapperPlugin plugin;
    private static final String rootAccessor = "playerUUIDs.";
    private final YMLStore store;

    public SLUUIDToNamesMapperAPI(SLUUIDToNamesMapperPlugin plugin) {
        this.plugin = plugin;
        this.store = new YMLStore(this.plugin);
    }

    public String getName(String uuid) {
        if (uuid == null || uuid.equals("")) {
            return null;
        }
        FileConfiguration cache = this.store.getCache();
        String uuidAccessor = rootAccessor + uuid;
        if (!cache.isSet(uuidAccessor)) {
            return null;
        }

        return cache.getString(uuidAccessor);
    }

    public List<String> getAllName(String match) {
        FileConfiguration cache = this.store.getCache();
        String rootAccessor = SLUUIDToNamesMapperAPI.rootAccessor.substring(0, SLUUIDToNamesMapperAPI.rootAccessor.length());
        if (!cache.isSet(rootAccessor)) {
            return null;
        }
        Map<String,Object> uuids = cache.getConfigurationSection(rootAccessor).getValues(true);
        List<String> names = new ArrayList<String>();
        for (Map.Entry<String,Object> entry : uuids.entrySet()) {
            if (match != null && !match.equals("") && !entry.getValue().toString().toLowerCase().startsWith(match.toLowerCase())) {
                continue;
            }
            names.add(entry.getValue().toString());
        }
        names.sort(String.CASE_INSENSITIVE_ORDER);

        return names;
    }

    public UUID getUUID(String name) {
        if (name == null || name.equals("")) {
            return null;
        }
        FileConfiguration cache = this.store.getCache();
        String rootAccessor = SLUUIDToNamesMapperAPI.rootAccessor.substring(0, SLUUIDToNamesMapperAPI.rootAccessor.length());
        if (!cache.isSet(rootAccessor)) {
            return null;
        }
        Map<String,Object> uuids = cache.getConfigurationSection(rootAccessor).getValues(true);
        for (Map.Entry<String,Object> entry : uuids.entrySet()) {
            if (entry.getValue().toString().equalsIgnoreCase(name)) {
                return UUID.fromString(entry.getKey());
            }
        }

        return null;
    }

    public boolean savePlayer(String name, String uuid) {
        String path = SLUUIDToNamesMapperAPI.rootAccessor + uuid;
        FileConfiguration cache = this.store.getCache();
        cache.set(path, name);
        return this.store.saveStore();
    }
}