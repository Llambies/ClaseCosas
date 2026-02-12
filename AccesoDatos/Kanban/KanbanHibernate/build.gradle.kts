plugins {
    id("java")
}

group = "org.adrian"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Hibernate ORM
    implementation("org.hibernate.orm:hibernate-core:7.2.0.Final")
    // Driver JDBC de PostgreSQL
    implementation("org.postgresql:postgresql:42.7.8")
}

tasks.test {
    useJUnitPlatform()
}