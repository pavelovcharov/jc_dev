package ru.narod.jcommander.plugin.hellojc;

import java.util.logging.Logger;
import ru.narod.jcommander.plugin.api.AppPlugin;

/**
 * Created with IntelliJ IDEA.
 * User: master
 * Date: 01.06.12
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */
public class HelloPlugin implements AppPlugin {
    private static Logger logger = Logger.getLogger(HelloPlugin.class.getName());

    @Override
    public String getName() {
        return "Hello Plugin ";
    }

    @Override
    public void init() {
        logger.info(getName() + " initialized!");
    }
}
