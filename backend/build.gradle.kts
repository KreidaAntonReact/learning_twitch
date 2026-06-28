plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"

    id("org.springframework.boot") version "4.0.6"
    id("io.spring.dependency-management") version "1.1.7"

    id("nu.studer.jooq") version "9.0"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}

dependencies {

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-liquibase")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Logging
    implementation("org.zalando:logbook-spring-boot-starter:4.0.4")

    // jOOQ runtime
    implementation("org.jooq:jooq:3.19.32")

    // DB runtime
    runtimeOnly("org.postgresql:postgresql")

    // jOOQ codegen (НЕ нужен реальный Postgres)
    jooqGenerator("org.postgresql:postgresql:42.7.10")

    jooqGenerator("org.jooq:jooq-meta-extensions-liquibase:3.19.32")

    // SLF4J для codegen (убирает warning)
    jooqGenerator("org.slf4j:slf4j-simple:2.0.13")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

jooq {
    version.set("3.19.32")

    configurations {
        create("main") {

            generateSchemaSourceOnCompilation.set(false)

            jooqConfiguration.apply {
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/twitch_db"
                    user = "user"
                    password = "password"
                }

                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.extensions.liquibase.LiquibaseDatabase"
                        properties.add(
                            org.jooq.meta.jaxb.Property()
                                .withKey("rootPath")
                                .withValue("src/main/resources")
                        )

                        properties.add(
                            org.jooq.meta.jaxb.Property()
                                .withKey("scripts")
                                .withValue("db/changelog/db.changelog-master.yaml")
                        )
                    }

                    generate.apply {
                        isDaos = false
                        isPojos = true
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                    }

                    target.apply {
                        packageName = "com.example.demo.jooq"
                        directory = "build/generated-src/jooq"
                    }
                }
            }
        }
    }
}

sourceSets {
    main {
        java {
            srcDir("build/generated-src/jooq")
        }
    }
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        dependsOn("generateJooq")
    }

    test {
        useJUnitPlatform()
    }
}