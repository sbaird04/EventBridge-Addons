package edu.errsa.example;

import com.eventbridge.api.AddonEvent;
import com.eventbridge.api.AddonField;
import com.eventbridge.api.AddonResult;
import com.eventbridge.api.GameAddon;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

public final class ExampleRaceAddon implements GameAddon {
    private final Logger logger;

    public ExampleRaceAddon(Logger logger) { this.logger = logger; }

    @Override public String key() { return "EXAMPLE_RACE"; }
    @Override public String displayName() { return "Example Race"; }

    @Override public List<AddonField> fields() {
        return List.of(
            new AddonField("laps", "Laps", AddonField.Type.INT, true,
                    null, "3", "Number of laps for the race"),
            new AddonField("arena", "Arena", AddonField.Type.TEXT, true,
                    null, "test_course", "Name of the arena to use")
        );
    }

    @Override public AddonResult validate(Map<String, String> params) {
        try {
            int laps = Integer.parseInt(params.getOrDefault("laps", "3"));
            if (laps < 1 || laps > 20) return AddonResult.failure("Laps must be between 1 and 20");
            if (params.getOrDefault("arena", "").isBlank())
                return AddonResult.failure("Please choose an arena");
            return AddonResult.success();
        } catch (NumberFormatException e) { return AddonResult.failure("Laps must be an integer"); }
    }

    @Override public AddonResult start(AddonEvent event) {
        logger.info("Example event {} started on {}: {}", event.id(), event.serverName(), event.params());
        // Demo lifecycle only. Real gameplay should send commands to your own Paper addon.
        return AddonResult.success();
    }

    @Override public AddonResult stop(AddonEvent event) {
        logger.info("Example event {} stopped", event.id());
        return AddonResult.success();
    }
}
