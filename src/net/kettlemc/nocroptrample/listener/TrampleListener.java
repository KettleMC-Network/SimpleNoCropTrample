package net.kettlemc.nocroptrample.listener;

import net.kettlemc.nocroptrample.NoCropTramplePlugin;
import net.kettlemc.nocroptrample.event.CropTrampleEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class TrampleListener implements Listener {

    private NoCropTramplePlugin plugin;

    public TrampleListener(NoCropTramplePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMobTrample(EntityInteractEvent event) {
        if (plugin.getConfiguration().isFarmlandsBlock(event.getBlock().getType())) {
            CropTrampleEvent cropTrampleEvent = new CropTrampleEvent(event.getEntity(), CropTrampleEvent.TrampleCause.MOB, event.getBlock());
            Bukkit.getPluginManager().callEvent(cropTrampleEvent);
            if (cropTrampleEvent.isCancelled())
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerTrample(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL && plugin.getConfiguration().isFarmlandsBlock(event.getClickedBlock().getType())) {
            CropTrampleEvent cropTrampleEvent = new CropTrampleEvent(event.getPlayer(), CropTrampleEvent.TrampleCause.MOB, event.getClickedBlock());
            Bukkit.getPluginManager().callEvent(cropTrampleEvent);
            if (cropTrampleEvent.isCancelled())
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onTrample(CropTrampleEvent event) {
        if ((plugin.getConfiguration().isMobTramplingDisabled() && event.getCause() == CropTrampleEvent.TrampleCause.MOB) || (plugin.getConfiguration().isPlayerTramplingDisabled() && event.getCause() == CropTrampleEvent.TrampleCause.PLAYER))
            event.setCancelled(true);
    }
}
