package com.lunarclient.bukkitapi.object;

import com.google.common.base.Preconditions;
import java.time.Duration;

public final class LCNotification
{
    private final String message;
    private final long durationMs;
    private final Level level;
    
    public LCNotification(final String message, final Duration duration) {
        this(message, duration, Level.INFO);
    }
    
    public LCNotification(final String message, final Duration duration, final Level level) {
        this.message = (String)Preconditions.checkNotNull((Object)message, "message");
        this.durationMs = duration.toMillis();
        this.level = (Level)Preconditions.checkNotNull((Object)level, "level");
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public long getDurationMs() {
        return this.durationMs;
    }
    
    public Level getLevel() {
        return this.level;
    }
    
    public enum Level
    {
        INFO, 
        ERROR
    }
}
