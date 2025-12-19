📦 AutoError

AutoError is a modular Java error-handling utility framework that simplifies error handling using annotations, runtime helpers, and optional Spring Boot integration. The project includes an annotation library, a compile-time processor, runtime support, and a demo to help you get started quickly.

🚀 Features

📝 Annotation-based error definitions for cleaner code

🔌 A processor that scans and generates necessary error metadata at compile time

⚙️ Runtime support to handle and process errors consistently

🌱 Optional Spring Boot Starter for seamless integration

📊 A demo project to illustrate usage patterns and best practices

📁 Repository Structure
autoerror/
├── autoerror-annotations         # Error annotations for developers
├── autoerror-processor           # Annotation processor
├── autoerror-runtime             # Runtime support library
├── autoerror-spring-boot-starter # Spring Boot integration
├── autoerror-demo                # Example/demo project
├── .gitignore
├── LICENSE
└── pom.xml                      # Parent build file


Each subproject has its own Maven coordinates and can be published or referenced separately.

📦 Getting Started
1. Add Dependencies

Add the modules you need to your Maven or Gradle project.

Maven Example:

<dependencies>
    <!-- Runtime support -->
    <dependency>
        <groupId>io.autoerror</groupId>
        <artifactId>autoerror-runtime</artifactId>
        <version>0.1.0</version>
    </dependency>

    <!-- Optional Spring Boot Starter -->
    <dependency>
        <groupId>io.autoerror</groupId>
        <artifactId>autoerror-spring-boot-starter</artifactId>
        <version>0.1.0</version>
    </dependency>

    <!-- Annotation Processor -->
    <dependency>
        <groupId>io.autoerror</groupId>
        <artifactId>autoerror-processor</artifactId>
        <version>0.1.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>

📖 Demo

The autoerror-demo project contains working examples showcasing how the annotations and processor work together, including:

Error definitions

Generated metadata

Custom handlers

Spring Boot usage

Clone the demo, build it, and run to see it in action:

git clone https://github.com/surinder-rajpal/autoerror.git
cd autoerror/autoerror-demo
mvn clean install
mvn spring-boot:run

🤝 Contributing

Contributions are welcome! Feel free to:

Open issues for bugs or enhancement requests

Submit pull requests with fixes or new features

📜 License

Distributed under the GPL-3.0 License (or whatever the project’s LICENSE file specifies).
