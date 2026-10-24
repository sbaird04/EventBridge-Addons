# Building an EventBridge game add-on (API v1)

## What you can do

Your plugin registers a game type which EventBridge automatically includes in its Create Event wizard. EventBridge handles scheduling, start/stop requests, public event publication, and the website Event/Mailbox feed. Your plugin supplies the game-specific form fields, parameter validation, start and stop behavior.

**No modification to the core EventBridge repository is required.** The admin needs only to install/approve your compiled JAR on Velocity, plus an optional Paper JAR if your game interacts with worlds, blocks, or inventories.

## Build an add-on

1. Install JDK 21, IntelliJ IDEA and Gradle. Use the matching Velocity 3.3 API currently used by ERRSA.
2. Copy `examples/eventbridge-addon-starter`. The `libs/eventbridge-api.jar` must be copied from `api/eventbridge-api.jar` in this repository (already included in the starter ZIP).
3. Change `@Plugin(id = "errsa-example-game")` to your own distinct Velocity plugin ID. Keep `@Dependency(id = "eventbridge")`.
4. Rename `EXAMPLE_RACE` to a stable uppercase game key such as `CTF` (2–40 characters, letters/digits/underscores, starts with a letter).
5. Define form fields (TEXT, INT, BOOL, CHOICE); validate parameters; start and stop your event in your own code. Do not block the Velocity thread with long tasks.
6. Build your JAR using `gradlew build` (or IntelliJ Gradle Build). Install only your built add-on JAR to `plugins/` on the **Velocity test proxy** and reboot the proxy.
7. Use EventBridge's Create Event wizard. Confirm your game appears and that create, schedule, start, stop and cancel work. If the wizard shows no game, check Velocity startup logs for `Registered EventBridge game add-on`.
8. Submit the JAR, README and test evidence for admin approval before deploying to main.

## Core API contracts

| Type | Purpose |
| --- | --- |
| `EventBridgeApi` | discover via Velocity PluginManager; `registerAddon(ownerPluginId, addon)` |
| `GameAddon` | `key()`, `displayName()`, `fields()`, `validate()`, `start()`, `stop()` |
| `AddonField` | wizard field, required flag, choices, default and help text |
| `AddonEvent` | immutable event ID, name, target server, dates, params and description |
| `AddonResult` | success/failure with optional admin-facing message |

EventBridge **rejects duplicate keys**, including `SPLEEF`, `HUNGER_GAMES` (depending on the built-in key), `PARKOUR` and any other registered game type. Use a new key; existing built-in keys cannot be overridden.

## Example registration

```java
var container = proxy.getPluginManager().getPlugin("eventbridge")
        .orElseThrow(() -> new IllegalStateException("EventBridge not installed"));
EventBridgeApi bridge = (EventBridgeApi) container.getInstance()
        .orElseThrow(() -> new IllegalStateException("EventBridge not loaded"));
bridge.registerAddon("errsa-example-game", new ExampleRaceAddon());
```

## Paper gameplay: Hunger Games, Spleef, arena events

EventBridge's event wizard and scheduler live on **Velocity**. Bukkit/Paper block breaking, deaths, chests, explosions, inventory changes, player movement, and arena resets must be handled by **your own Paper add-on plugin** installed on the relevant game server.

Recommended design:

- Velocity side: implement `GameAddon`. In `start()` and `stop()`, transmit your own add-on's commands to your Paper plugin using a separate plugin messaging channel you define (such as `errsa_race:control`). Do **not** reuse EventBridge's private `eventbridge:main` protocol.
- Paper side: register your own channel, validate inbound messages, check that a match ID is active, and run Bukkit world/player operations on the Paper main thread. The plugin channel requires at least one player connected to that backend. If offline starts matter, use an authenticated backend API/queue instead of assuming plugin messages always deliver.
- Consider robust matching, warmup/countdown, disconnect/reconnect, spectator rules, restoring inventories, rollback safety, idempotent stop/reset, stats persistence, and plugin disable behavior.
- Do not trust a user/client to send lifecycle commands. Authenticate the sender with the trusted Velocity/Paper backend channel or a shared secret if using an HTTP bridge.

**The example in this kit registers and starts/stops an event lifecycle but does NOT implement a real minigame.** It is a safe starting point for staff who build their own game logic.

## Limits, compatibility, and review

- API v1 returns synchronous results; long game operations must be scheduled. Return failure if the backend isn't ready.
- Keep event keys and existing config fields stable to avoid breaking stored events.
- Do not shade the API JAR. It is provided by EventBridge Velocity at runtime.
- Use `compileOnly` for the API. Do not give staff EventBridge's main source code or production database access.
- Test on the test proxy and test Paper backend with realistic stop/abort cases before production deployment.
- Mods may read and propose add-on changes, but installing arbitrary plugins or changing Velocity/Paper config remains an admin-only action.
