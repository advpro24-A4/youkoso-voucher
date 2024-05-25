plugins {
	java
	jacoco
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.flywaydb.flyway") version "10.11.0"
}

group = "id.ac.ui.cs.advprog"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

flyway {
    url = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:5432/postgres"
    user = "postgres.lsrqqazbjxscfnbfyfin"
    password = "adpro-youkoso"
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.flywaydb:flyway-core")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	implementation("org.springframework.boot:spring-boot-starter-actuator:3.2.5")
	runtimeOnly("io.micrometer:micrometer-registry-prometheus:1.12.5")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate6:2.15.2")
}

buildscript {
	dependencies {
		classpath("org.postgresql:postgresql:42.7.1")
		classpath("org.flywaydb:flyway-database-postgresql:10.11.0")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()
}

tasks.test {
	filter {
		excludeTestsMatching("*FunctionalTest")
	}

	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	classDirectories.setFrom(files(classDirectories.files.map {
       fileTree(it) { 
			exclude("**/*Application**")
			exclude("**/*Config**")
		}
   	}))
	dependsOn(tasks.test)
}