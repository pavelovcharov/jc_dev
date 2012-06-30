package ru.narod.jcommander.plugins;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.Logger;
import ru.narod.jcommander.plugin.api.AppPlugin;

public class StandardPluginService implements PluginService
{
    private static StandardPluginService pluginService;
    private ServiceLoader<AppPlugin> serviceLoader;
    private Logger logger = Logger.getLogger(getClass().getName());

    private StandardPluginService()
    {
        //load all the classes in the classpath that have implemented the interface
        serviceLoader = ServiceLoader.load(AppPlugin.class);
    }

    public static StandardPluginService getInstance()
    {
        if(pluginService == null)
        {
            pluginService = new StandardPluginService();
        }
        return pluginService;
    }

    public Iterator<AppPlugin> getPlugins()
    {
        return serviceLoader.iterator();
    }

    public void initPlugins()
    {
        Iterator<AppPlugin> iterator = getPlugins();
        if(!iterator.hasNext())
        {
            logger.info("No plugins were found!");
        }
        while(iterator.hasNext())
        {
            AppPlugin plugin = iterator.next();
            logger.info("Initializing the plugin " + plugin.getName());
            plugin.init();
        }
    }
}
