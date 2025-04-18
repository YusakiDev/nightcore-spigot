package su.nightexpress.nightcore.util.bukkit;

import com.tcoded.folialib.wrapper.task.WrappedTask;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.nightexpress.nightcore.NightCorePlugin;
import su.nightexpress.nightcore.util.TimeUtil;

import java.util.function.Consumer;

public class NightTask {

    private final NightCorePlugin plugin;
    private final WrappedTask wrappedTask;

    public NightTask(@NotNull NightCorePlugin plugin, @Nullable WrappedTask wrappedTask) {
        this.plugin = plugin;
        this.wrappedTask = wrappedTask;
    }

    @NotNull
    public static NightTask create(@NotNull NightCorePlugin plugin, @NotNull Consumer<WrappedTask> consumer, int interval) {
        return create(plugin, consumer, TimeUtil.secondsToTicks(interval));
    }

    @NotNull
    public static NightTask create(@NotNull NightCorePlugin plugin, @NotNull Consumer<WrappedTask> consumer, long interval) {
        return createTask(plugin, consumer, interval, false);
    }

    @NotNull
    public static NightTask createAsync(@NotNull NightCorePlugin plugin, @NotNull Consumer<WrappedTask> consumer, int interval) {
        return createAsync(plugin, consumer, TimeUtil.secondsToTicks(interval));
    }

    @NotNull
    public static NightTask createAsync(@NotNull NightCorePlugin plugin, @NotNull Consumer<WrappedTask> consumer, long interval) {
        return createTask(plugin, consumer, interval, true);
    }

    private static NightTask createTask(@NotNull NightCorePlugin plugin, @NotNull Consumer<WrappedTask> consumer, long interval, boolean async) {
        if (interval <= 0) return new NightTask(plugin, null);
        
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
            public Plugin getOwningPlugin() {
                return plugin;
            }
            
            @Override
            public boolean isAsync() {
                return async;
            }
        };
        
        if (async) {
            plugin.getFoliaLib().getScheduler().runTimerAsync(() -> {
                if (!task.isCancelled()) {
                    consumer.accept(task);
                }
            }, 0L, interval);
        } else {
            plugin.getFoliaLib().getScheduler().runTimer(() -> {
                if (!task.isCancelled()) {
                    consumer.accept(task);
                }
            }, 0L, interval);
        }
        
        return new NightTask(plugin, task);
    }

    @Nullable
    public WrappedTask getWrappedTask() {
        return this.wrappedTask;
    }

    public boolean isValid() {
        return this.wrappedTask != null && !this.wrappedTask.isCancelled();
    }

    @Deprecated
    public boolean isRunning() {
        return this.isValid();
    }

    public boolean stop() {
        if (this.wrappedTask == null) return false;

        this.wrappedTask.cancel();
        return true;
    }
}
