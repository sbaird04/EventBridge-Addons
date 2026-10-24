# Example Race starter

Build with Java 21 and the provided EventBridge API v1 JAR. Use IntelliJ Gradle tasks > build.

The sample registers the `EXAMPLE_RACE` game type in the EventBridge wizard. It validates fields, and records lifecycle starts/stops in the Velocity log; it does not run real block or arena gameplay.

For actual Hunger Games/Spleef-type features, create your own separate Paper companion plugin and a custom authenticated messaging path. See `../../docs/eventbridge-addons.md` for integration guidance.
