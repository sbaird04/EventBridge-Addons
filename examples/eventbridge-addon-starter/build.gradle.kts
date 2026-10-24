plugins { java }

group = "edu.errsa"
version = "1.0.0"

repositories { mavenCentral(); maven("https://repo.papermc.io/repository/maven-public/") }

java { toolchain { languageVersion.set(JavaLanguageVersion.of(21)) } }

dependencies {
    compileOnly(files("libs/eventbridge-api.jar"))
    compileOnly("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
}
