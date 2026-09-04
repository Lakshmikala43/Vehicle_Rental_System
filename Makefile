# Makefile for RentRide Vehicle Rental & Fleet Management System

.PHONY: all build test run docker-build clean

all: build test

build:
	@echo "Building Java Spring Boot Backend..."
	cd backend && mvn clean package -DskipTests

test:
	@echo "Running Unit Tests..."
	cd backend && mvn test

run:
	@echo "Starting RentRide Platform..."
	python -m http.server 3000 --directory frontend

docker-build:
	@echo "Building Docker Container Image..."
	docker build -t rentride:latest .

clean:
	@echo "Cleaning Build Artifacts..."
	cd backend && mvn clean
