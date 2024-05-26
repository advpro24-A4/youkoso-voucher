plugins {
	java
	jacoco
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.flywaydb.flyway") version "10.11.0"
	id("org.sonarqube") version "4.4.1.3373"
}

group = "id.ac.ui.cs.advprog"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

sonar {
	properties {
		property("sonar.projectKey", "advpro24-A4_youkoso-voucher")
		property("sonar.organization", "advpro24-a4")
		property("sonar.host.url", "https://sonarcloud.io")
	}
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
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate6")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("io.micrometer:micrometer-registry-prometheus")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
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
			exclude("**/*Config**")
			exclude("**/*Payment**")
			exclude("**/*Auth**")
			exclude("**/*Exception**")
			exclude("**/*Profile**")
			exclude("**/*User**")
			exclude("**/*Security**")
			exclude("**/*Response**")
			exclude("**/*Request**")
		}
   	}))
	dependsOn(tasks.test)
}