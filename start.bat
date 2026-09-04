@echo off
echo ============================================================
echo  Starting RentRide Vehicle Rental & Fleet Management System
echo ============================================================

echo [1/2] Starting Frontend Web Server on http://localhost:3000...
start "" "http://localhost:3000/index.html"
python -m http.server 3000 --directory "frontend"
