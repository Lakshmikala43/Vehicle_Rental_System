import os

base_dir = r"C:\Users\WINDOWS\.gemini\antigravity\scratch\RideFleet"

# Packages to generate rich production classes for
modules = [
    "auth", "customer", "staff", "driver", "branch", "vehicle",
    "booking", "pricing", "payment", "inspection", "maintenance",
    "fleet", "intelligence", "analytics", "audit", "common", "config"
]

for m in modules:
    pkg_dir = os.path.join(base_dir, "backend", "src", "main", "java", "com", "ridefleet", m)
    os.makedirs(pkg_dir, exist_ok=True)

migrations_dir = os.path.join(base_dir, "database", "migrations")
os.makedirs(migrations_dir, exist_ok=True)

print("Created directories for production code generation.")
