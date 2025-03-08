all: build run

build:
	@echo "Building..."
	@mvn clean package -DskipTests

run:
	@echo "Running..."
	@docker compose up -d --build

stop:
	@echo "Stopping..."
	@docker compose down

logs:
	@echo "Showing logs..."
	@docker compose logs -f

CHECKSTYLE_JAR=/usr/local/bin/checkstyle.jar
CHECKSTYLE_CMD=java -jar $(CHECKSTYLE_JAR)

lint:
	@if ! command -v java > /dev/null 2>&1; then \
		echo "ERROR: Java is not installed. Please install it first."; \
		exit 1; \
	fi
	@if [ ! -f "$(CHECKSTYLE_JAR)" ]; then \
		echo "MISSING DEPENDENCY: checkstyle is not installed"; \
		echo "Installing checkstyle manually..."; \
		sudo wget -O $(CHECKSTYLE_JAR) https://github.com/checkstyle/checkstyle/releases/download/checkstyle-10.17.0/checkstyle-10.17.0-all.jar; \
	fi
	$(CHECKSTYLE_CMD) -c /google_checks.xml $(shell find ./src/main/java -name "*.java")




PHONY: all build run stop logs lint