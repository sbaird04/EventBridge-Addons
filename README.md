# ERRSA Minecraft — Plugin Developer Docs

Documentation and a **compiled API JAR** for approved ERRSA add-on developers. No EventBridge implementation source code, internal configuration, website secrets, or production database access is included.

## EventBridge add-ons

A staff developer can create a separate Velocity plugin (such as a new Spleef variant, Capture the Flag, or Hunger Games variant), register its game type, and use the normal EventBridge **Create Event** wizard without editing or recompiling the core plugin.

1. Read [EventBridge add-on guide](docs/eventbridge-addons.md).
2. Copy `examples/eventbridge-addon-starter` into your own new project/repository.
3. Use `api/eventbridge-api.jar` as the **compileOnly** dependency. Do not shade/package this JAR in your add-on.
4. Build your own plugin JAR, send it to an administrator for testing on **ERRSA Test**; admin installs it on Velocity. Implement a separate Paper plugin if the game needs Bukkit-world interactions.

The public API version is **1**. Existing Hunger Games and Spleef integrations inside the main EventBridge code are legacy built-ins; this kit is for **future standalone add-ons**.

**Important:** Publish this documentation as a separate GitHub repository. Keep the complete EventBridge repository PRIVATE. Do not upload any `eventbridge-velocity.jar`, core `src/`, real YAML secrets, saved event/whitelist state, or internal data to this repository.
