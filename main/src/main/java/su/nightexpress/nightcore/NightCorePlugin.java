package su.nightexpress.nightcore;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import com.tcoded.folialib.FoliaLib;
import com.tcoded.folialib.wrapper.task.WrappedTask;

import su.nightexpress.nightcore.command.CommandManager;
import su.nightexpress.nightcore.command.api.NightPluginCommand;
import su.nightexpress.nightcore.config.FileConfig;
import su.nightexpress.nightcore.config.PluginDetails;
import su.nightexpress.nightcore.language.LangManager;
import su.nightexpress.nightcore.util.wrapper.UniTask;

import java.util.function.Consumer;

public interface NightCorePlugin extends Plugin {

    //boolean isEngine();

    void enable();

    void disable();

    void reload();

    @Deprecated
    NightPluginCommand getBaseCommand();

    @Override
    @NotNull FileConfig getConfig();

    @NotNull FileConfig getLang();

    @NotNull PluginDetails getDetails();

    void extractResources(@NotNull String jarPath);

    void extractResources(@NotNull String jarParh, @NotNull String toPath);

    @NotNull
    default String getNameLocalized() {
        return this.getDetails().getName();
    }

    @NotNull
    default String getPrefix() {
        return this.getDetails().getPrefix();
    }

    @NotNull
    default String[] getCommandAliases() {
        return this.getDetails().getCommandAliases();
    }

    @NotNull
    default String getLanguage() {
        return this.getDetails().getLanguage();
    }

    default void info(@NotNull String msg) {
        this.getLogger().info(msg);
    }

    default void warn(@NotNull String msg) {
        this.getLogger().warning(msg);
    }

    default void error(@NotNull String msg) {
        this.getLogger().severe(msg);
    }

    default void debug(@NotNull String msg) {
        this.info("[DEBUG] " + msg);
    }

    @NotNull LangManager getLangManager();

    @NotNull CommandManager getCommandManager();

    @NotNull
    default FoliaLib getFoliaLib() {
        return ((NightPlugin) this).getFoliaLib();
    }

    @NotNull
    default PluginManager getPluginManager() {
        return this.getServer().getPluginManager();
    }

    default void runTask(@NotNull Consumer<WrappedTask> consumer) {
        this.getFoliaLib().getScheduler().runNextTick(consumer);
    }

    default void runTaskAsync(@NotNull Consumer<WrappedTask> consumer) {
        this.getFoliaLib().getScheduler().runAsync(consumer);
    }

    default void runTaskLater(@NotNull Consumer<WrappedTask> consumer, long delay) {
        this.getFoliaLib().getScheduler().runLater(consumer, delay);
    }

    default void runTaskLaterAsync(@NotNull Consumer<WrappedTask> consumer, long delay) {
        this.getFoliaLib().getScheduler().runLaterAsync(consumer, delay);
    }

    default void runTaskTimer(@NotNull Consumer<WrappedTask> consumer, long delay, long interval) {
        this.getFoliaLib().getScheduler().runTimer(consumer, delay, interval);
    }

    default void runTaskTimerAsync(@NotNull Consumer<WrappedTask> consumer, long delay, long interval) {
        this.getFoliaLib().getScheduler().runTimerAsync(consumer, delay, interval);
    }

    @NotNull
    @Deprecated
    default UniTask createTask(@NotNull Runnable runnable) {
        return new UniTask(this, runnable);
    }

    @NotNull
    @Deprecated
    default UniTask createAsyncTask(@NotNull Runnable runnable) {
        return this.createTask(runnable).setAsync();
    }
}
