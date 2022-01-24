package net.kettlemc.nocroptrample.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CropTrampleEvent extends Event implements Cancellable {

    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();
    private Entity trampler;
    private TrampleCause cause;
    private Block block;

    public CropTrampleEvent(Entity trampler, TrampleCause cause, Block block) {
        this.trampler = trampler;
        this.cause = cause;
        this.block = block;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public TrampleCause getCause() {
        return this.cause;
    }

    public Entity getTrampler() {
        return this.trampler;
    }

    public Block getBlock() {
        return this.block;
    }

    public enum TrampleCause {
        MOB(),
        PLAYER();
    }
}
