package ru.narod.jcommander.plugins;

import ru.narod.jcommander.plugin.api.AppPlugin;

import java.util.Iterator;

public interface PluginService
{
    Iterator<AppPlugin> getPlugins();
    void initPlugins();
}
