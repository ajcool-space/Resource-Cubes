package space.ajcool.resourcecubes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceCubes implements ModInitializer
{
    public static final String MOD_ID = "resource-cubes";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Resource Cubes Initialized");

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) DebugCommands.register();
    }
}
