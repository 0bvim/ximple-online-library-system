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

lint:
	@if ! command -v checkstyle > /dev/null 2>&1; then \
		echo "MISSING DEPENDENCY: checkstyle is not installed"; \
		echo "Installing checkstyle..."; \
		curl -LO https://github.com/checkstyle/checkstyle/releases/download/checkstyle-10.17.0/checkstyle-10.17.0-all.jar; \
		echo 'alias checkstyle="java -jar $(pwd)/checkstyle-10.17.0-all.jar"' >> ~/.bashrc; \
		source ~/.bashrc; \
	fi
	checkstyle -c /google_checks.xml $(shell find . -name "*.java")


.PHONY: all build run stop logs