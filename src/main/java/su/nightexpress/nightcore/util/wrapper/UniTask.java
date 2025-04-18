package su.nightexpress.nightcore.util.wrapper;

import com.tcoded.folialib.wrapper.task.WrappedTask;
import org.jetbrains.annotations.NotNull;
import su.nightexpress.nightcore.NightCorePlugin;

@Deprecated
public class UniTask {

    private final NightCorePlugin plugin;
    private final Runnable runnable;
    private final WrappedTask wrappedTask;

    private long interval;
    private boolean async;

    public UniTask(@NotNull NightCorePlugin plugin, @NotNull Runnable runnable) {
        this(plugin, runnable, 0L);
    }

    public UniTask(@NotNull NightCorePlugin plugin, @NotNull Runnable runnable, int interval) {
        this(plugin, runnable, interval, false);
    }

    public UniTask(@NotNull NightCorePlugin plugin, @NotNull Runnable runnable, int interval, boolean async) {
        this(plugin, runnable, interval * 20L, async);
    }

    public UniTask(@NotNull NightCorePlugin plugin, @NotNull Runnable runnable, long interval) {
        this(plugin, runnable, interval, false);
    }

    public UniTask(@NotNull NightCorePlugin plugin, @NotNull Runnable runnable, long interval, boolean async) {
        this.plugin = plugin;
        this.runnable = runnable;
        this.interval = interval;
        this.async = async;
        this.wrappedTask = null;
    }

    @Deprecated
    public UniTask setSecondsInterval(int interval) {
        return this.setTicksInterval(interval * 20L);
    }

    @Deprecated
    public UniTask setTicksInterval(long interval) {
        this.interval = interval;
        return this;
    }

    @Deprecated
    public UniTask setAsync() {
        return this.setAsync(true);
    }

    @Deprecated
    public UniTask setAsync(boolean async) {
        this.async = async;
        return this;
    }

    public boolean isRunning() {
        return this.wrappedTask != null && !this.wrappedTask.isCancelled();
    }

    public final void restart() {
        this.stop();
        this.start();
    }

    public UniTask start() {
        if (this.wrappedTask != null || this.interval <= 0L) return this;

        WrappedTask task = new WrappedTask() {
            private boolean cancelled = false;
            
            @Override
            public void cancel() {
                cancelled = true;
            }
            
            @Override
            public boolean isCancelled() {
                return cancelled;
            }
            
            @Override
            public org.bukkit.plugin.Plugin getOwningPlugin() {
                return plugin;
            }
            
            @Override
            public boolean isAsync() {
                return async;
            }
        };

        if (this.async) {
            this.plugin.getFoliaLib().getScheduler().runTimerAsync(() -> {
                if (!task.isCancelled()) {
                    runnable.run();
                }
            }, 0L, this.interval);
        } else {
            this.plugin.getFoliaLib().getScheduler().runTimer(() -> {
                if (!task.isCancelled()) {
                    runnable.run();
                }
            }, 0L, this.interval);
        }

        return this;
    }

    public boolean stop() {
        if (this.wrappedTask == null) return false;

        this.wrappedTask.cancel();
        return true;
    }
}
