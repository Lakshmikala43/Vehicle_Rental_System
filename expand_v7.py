import os

base_path = r"C:\Users\WINDOWS\.gemini\antigravity\scratch\RideFleet"

v7_sql = []
v7_sql.append("-- ============================================================================")
v7_sql.append("-- RentRide Migration V7: Regional Telematics & Branch Capacity Stream Script")
v7_sql.append("-- ============================================================================")

for i in range(1, 9000):
    timestamp = f"2026-09-04 12:{(i%60):02d}:{(i*7)%60:02d}"
    v_id = (i % 1000) + 10
    lat = 12.9716 + (i % 50) * 0.001
    lng = 77.5946 + (i % 50) * 0.001
    speed = (i * 15) % 120
    fuel = 100 - (i % 80)
    v7_sql.append(f"INSERT INTO vehicle_locations (id, vehicle_id, latitude, longitude, speed_kmh, fuel_level_percent, recorded_at) VALUES ({i}, {v_id}, {lat:.4f}, {lng:.4f}, {speed}, {fuel}, '{timestamp}');")

file_path = os.path.join(base_path, "database", "migrations", "V7__seed_fleet_telematics_history_ddl.sql")
with open(file_path, "w", encoding="utf-8") as f:
    f.write("\n".join(v7_sql) + "\n")

print("V7 migration generated successfully.")
