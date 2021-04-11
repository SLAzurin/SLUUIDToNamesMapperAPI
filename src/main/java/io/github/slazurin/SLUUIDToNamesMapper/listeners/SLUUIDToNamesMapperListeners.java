package io.github.slazurin.SLUUIDToNamesMapper.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import io.github.slazurin.SLUUIDToNamesMapper.SLUUIDToNamesMapperPlugin;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;

public class SLUUIDToNamesMapperListeners implements Listener {
    private final SLUUIDToNamesMapperPlugin plugin;

    public SLUUIDToNamesMapperListeners(SLUUIDToNamesMapperPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player p = event.getPlayer();
        String uuid = p.getUniqueId().toString();
        String name = PlainComponentSerializer.plain().serialize(p.displayName());
        // get player name
        String storedName = this.plugin.getApi().getName(uuid);

        // if not there / not same, then update name in store
        if (storedName == null || !storedName.equals(name)) {
            this.plugin.getApi().savePlayer(name, uuid);
        }
    }
}
