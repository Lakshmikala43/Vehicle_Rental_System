#!/usr/bin/env bash
# RentRide Universal Application Launch Script
echo "============================================================"
echo " Starting RentRide Vehicle Rental & Fleet Management System "
echo "============================================================"

if command -v java &> /dev/null; then
    echo "[1/2] Launching Java Spring Boot Backend..."
    (cd backend && mvn spring-boot:run) &
fi

echo "[2/2] Launching Web Frontend Server on Port 3000..."
python3 -m http.server 3000 --directory frontend
