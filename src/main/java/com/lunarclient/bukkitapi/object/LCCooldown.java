package com.lunarclient.bukkitapi.object;

import com.google.common.base.Preconditions;
import java.util.concurrent.TimeUnit;
import org.bukkit.Material;

public final class LCCooldown
{
    private final String message;
    private final long durationMs;
    private final Material icon;
    
    public LCCooldown(final String message, final long unitCount, final TimeUnit unit, final Material icon) {
        this.message = (String)Preconditions.checkNotNull((Object)message, "message");
        this.durationMs = unit.toMillis(unitCount);
        this.icon = (Material)Preconditions.checkNotNull((Object)icon, "icon");
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public long getDurationMs() {
        return this.durationMs;
    }
    
    public Material getIcon() {
        return this.icon;
    }
}
