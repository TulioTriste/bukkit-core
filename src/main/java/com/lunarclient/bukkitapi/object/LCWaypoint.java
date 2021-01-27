package com.lunarclient.bukkitapi.object;

import com.lunarclient.bukkitapi.LunarClientAPI;
import org.bukkit.Location;

public final class LCWaypoint
{
    private final String name;
    private final int x;
    private final int y;
    private final int z;
    private final String world;
    private final int color;
    private final boolean forced;
    private final boolean visible;
    
    public LCWaypoint(final String name, final Location location, final int color, final boolean forced, final boolean visible) {
        this(name, location.getBlockX(), location.getBlockY(), location.getBlockZ(), LunarClientAPI.getInstance().getWorldIdentifier(location.getWorld()), color, forced, visible);
    }
    
    public LCWaypoint(final String name, final Location location, final int color, final boolean forced) {
        this(name, location.getBlockX(), location.getBlockY(), location.getBlockZ(), LunarClientAPI.getInstance().getWorldIdentifier(location.getWorld()), color, forced, true);
    }
    
    public LCWaypoint(final String name, final int x, final int y, final int z, final String world, final int color, final boolean forced, final boolean visible) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.color = color;
        this.forced = forced;
        this.visible = visible;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LCWaypoint)) {
            return false;
        }
        final LCWaypoint other = (LCWaypoint)o;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        Label_0058: {
            if (this$name == null) {
                if (other$name == null) {
                    break Label_0058;
                }
            }
            else if (this$name.equals(other$name)) {
                break Label_0058;
            }
            return false;
        }
        if (this.getX() != other.getX()) {
            return false;
        }
        if (this.getY() != other.getY()) {
            return false;
        }
        if (this.getZ() != other.getZ()) {
            return false;
        }
        final Object this$world = this.getWorld();
        final Object other$world = other.getWorld();
        if (this$world == null) {
            if (other$world == null) {
                return this.getColor() == other.getColor() && this.isForced() == other.isForced() && this.isVisible() == other.isVisible();
            }
        }
        else if (this$world.equals(other$world)) {
            return this.getColor() == other.getColor() && this.isForced() == other.isForced() && this.isVisible() == other.isVisible();
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * 59 + (($name == null) ? 43 : $name.hashCode());
        result = result * 59 + this.getX();
        result = result * 59 + this.getY();
        result = result * 59 + this.getZ();
        final Object $world = this.getWorld();
        result = result * 59 + (($world == null) ? 43 : $world.hashCode());
        result = result * 59 + this.getColor();
        result = result * 59 + (this.isForced() ? 79 : 97);
        result = result * 59 + (this.isVisible() ? 79 : 97);
        return result;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public String getWorld() {
        return this.world;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public boolean isForced() {
        return this.forced;
    }
    
    public boolean isVisible() {
        return this.visible;
    }
}
