package edu.errsa.example;

import com.eventbridge.api.EventBridgeApi;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(id = "errsa-example-game", name = "ERRSA Example Game", version = "1.0.0",
        dependencies = {@Dependency(id = "eventbridge")})
public final class ExampleAddonPlugin {
    private final ProxyServer proxy;
    private final Logger logger;

    @Inject
    public ExampleAddonPlugin(ProxyServer proxy, Logger logger) {
        this.proxy = proxy;
        this.logger = logger;
    }

    @Subscribe
    public void onInit(ProxyInitializeEvent event) {
        Object plugin = proxy.getPluginManager().getPlugin("eventbridge")
                .flatMap(container -> container.getInstance())
                .orElseThrow(() -> new IllegalStateException("EventBridge plugin missing"));
        if (!(plugin instanceof EventBridgeApi bridge))
            throw new IllegalStateException("EventBridge API v1 not available");
        bridge.registerAddon("errsa-example-game", new ExampleRaceAddon(logger));
        logger.info("Registered EXAMPLE_RACE via the public EventBridge API");
    }
}
