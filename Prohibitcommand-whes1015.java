package com.command;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

public class whes1015 extends PluginBase implements Listener {

    private static whes1015 plugin;
    private Config c;


    @Override
    public void onEnable() {
        this.getLogger().info(TextFormat.BLUE + "ProhibitCommand whes1015 working! "+"V 1.0.0");
        this.getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();

    }

    @EventHandler
    public void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
        c = getConfig();
        String cmd = e.getMessage().toLowerCase().replaceAll("\\s+","");
        for (String str : c.getStringList("commands")) {
            if (cmd.startsWith(str)) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(TextFormat.RED+"該指令被禁止");
                return;
            }
        }
        }
   }


