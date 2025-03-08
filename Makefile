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

.PHONY: all build run stop