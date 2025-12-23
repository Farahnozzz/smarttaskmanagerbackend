# Step 1: Use Java 17
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Set working directory
WORKDIR /app

# Step 3: Copy Maven files
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Step 4: Download dependencies
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Step 5: Copy source code
COPY src src

# Step 6: Build the application
RUN ./mvnw clean package -DskipTests

# Step 7: Run the JAR
EXPOSE 8080
CMD ["java", "-jar", "target/*.jar"]
