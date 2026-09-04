/**
 * RentRide HTML5 Canvas Schematic Fleet Board Visualizer
 * Color Theme: Automotive Yellow & White Palette
 */
class FleetMapCanvas {
  constructor(canvasId) {
    this.canvas = document.getElementById(canvasId);
    if (!this.canvas) return;
    this.ctx = this.canvas.getContext('2d');
    this.vehicles = [];
    this.branches = [
      { id: 1, name: 'Central Metro Hub', x: 220, y: 180, color: '#EAB308' },
      { id: 2, name: 'Airport Terminal 2', x: 620, y: 120, color: '#F59E0B' },
      { id: 3, name: 'Gachibowli Financial', x: 380, y: 360, color: '#EAB308' },
      { id: 4, name: 'Bandra Kurla Hub', x: 820, y: 320, color: '#FFFFFF' }
    ];
    this.initCanvas();
    this.fetchTelemetry();
    setInterval(() => this.fetchTelemetry(), 5000);
  }

  initCanvas() {
    this.resize();
    window.addEventListener('resize', () => this.resize());
    this.canvas.addEventListener('click', (e) => this.handleCanvasClick(e));
  }

  resize() {
    const rect = this.canvas.parentElement.getBoundingClientRect();
    this.canvas.width = rect.width;
    this.canvas.height = rect.height;
    this.draw();
  }

  async fetchTelemetry() {
    try {
      const data = await ApiClient.get('/fleet/telemetry');
      if (data && data.length > 0) {
        this.vehicles = data.map((v, idx) => {
          const branchIndex = idx % this.branches.length;
          const branch = this.branches[branchIndex];
          const offsetX = (Math.sin(Date.now() / 1000 + idx) * 90);
          const offsetY = (Math.cos(Date.now() / 1200 + idx) * 60);

          return {
            ...v,
            x: branch.x + offsetX,
            y: branch.y + offsetY,
            expectedAvailableDate: v.status === 'RENTED' || v.status === 'RESERVED' ? 'Sep 12, 2026 at 18:00' : 
                                  (v.status === 'MAINTENANCE' ? 'Sep 08, 2026 at 12:00' : 'Immediately Available')
          };
        });
      }
    } catch (e) {
      if (this.vehicles.length === 0) {
        this.generateMockVehicles();
      }
    }
    this.draw();
  }

  generateMockVehicles() {
    const mock = [];
    const statuses = ['AVAILABLE', 'RENTED', 'AVAILABLE', 'RESERVED', 'MAINTENANCE'];
    const expectedDates = [
      'Immediately Available',
      'Sep 12, 2026 at 18:00',
      'Immediately Available',
      'Sep 10, 2026 at 14:00',
      'Sep 08, 2026 at 12:00 (Service Work Order)'
    ];

    for (let i = 1; i <= 15; i++) {
      const branch = this.branches[i % this.branches.length];
      const status = statuses[i % statuses.length];
      mock.push({
        vehicleId: i,
        licensePlate: `KA-01-RF-10${i}`,
        modelName: `RentRide Vehicle ${i}`,
        status: status,
        expectedAvailableDate: expectedDates[i % expectedDates.length],
        x: branch.x + (Math.sin(i) * 85),
        y: branch.y + (Math.cos(i) * 55),
        speedKmh: status === 'RENTED' ? 55 : 0,
        fuelLevelPercent: 88 - (i * 2),
        dailyRate: 2500 + (i * 300)
      });
    }
    this.vehicles = mock;
  }

  draw() {
    if (!this.ctx) return;
    const { width, height } = this.canvas;

    this.ctx.fillStyle = '#0A0A0A';
    this.ctx.fillRect(0, 0, width, height);

    // Grid Lines
    this.ctx.strokeStyle = '#262626';
    this.ctx.lineWidth = 1;
    for (let x = 0; x < width; x += 40) {
      this.ctx.beginPath();
      this.ctx.moveTo(x, 0);
      this.ctx.lineTo(x, height);
      this.ctx.stroke();
    }
    for (let y = 0; y < height; y += 40) {
      this.ctx.beginPath();
      this.ctx.moveTo(0, y);
      this.ctx.lineTo(width, y);
      this.ctx.stroke();
    }

    // Branch Connectors
    this.ctx.strokeStyle = '#EAB308';
    this.ctx.setLineDash([6, 6]);
    this.ctx.lineWidth = 2;
    for (let i = 0; i < this.branches.length; i++) {
      for (let j = i + 1; j < this.branches.length; j++) {
        this.ctx.beginPath();
        this.ctx.moveTo(this.branches[i].x, this.branches[i].y);
        this.ctx.lineTo(this.branches[j].x, this.branches[j].y);
        this.ctx.stroke();
      }
    }
    this.ctx.setLineDash([]);

    // Branch Hubs
    this.branches.forEach(b => {
      this.ctx.fillStyle = b.color;
      this.ctx.beginPath();
      this.ctx.arc(b.x, b.y, 22, 0, Math.PI * 2);
      this.ctx.fill();

      this.ctx.strokeStyle = '#171717';
      this.ctx.lineWidth = 3;
      this.ctx.stroke();

      this.ctx.fillStyle = '#FFFFFF';
      this.ctx.font = 'bold 12px Inter, sans-serif';
      this.ctx.textAlign = 'center';
      this.ctx.fillText(b.name, b.x, b.y + 36);
    });

    // Vehicles
    this.vehicles.forEach(v => {
      let color = '#EAB308'; // AVAILABLE Automotive Yellow
      if (v.status === 'RENTED' || v.status === 'RESERVED') color = '#FFFFFF'; // White
      if (v.status === 'MAINTENANCE' || v.status === 'DAMAGED') color = '#DC2626'; // Red

      this.ctx.fillStyle = color;
      this.ctx.beginPath();
      this.ctx.arc(v.x, v.y, 12, 0, Math.PI * 2);
      this.ctx.fill();

      this.ctx.strokeStyle = '#171717';
      this.ctx.lineWidth = 2.5;
      this.ctx.stroke();

      this.ctx.fillStyle = '#F59E0B';
      this.ctx.font = 'bold 11px Inter, sans-serif';
      this.ctx.textAlign = 'center';
      this.ctx.fillText(v.licensePlate, v.x, v.y - 15);
    });
  }

  handleCanvasClick(e) {
    const rect = this.canvas.getBoundingClientRect();
    const clickX = e.clientX - rect.left;
    const clickY = e.clientY - rect.top;

    const clickedVehicle = this.vehicles.find(v => {
      const dist = Math.hypot(v.x - clickX, v.y - clickY);
      return dist <= 16;
    });

    if (clickedVehicle) {
      FleetMapCanvas.showVehicleModal(clickedVehicle);
    }
  }

  static showVehicleModal(vehicle) {
    let modal = document.getElementById('fleetVehicleDetailModal');
    if (modal) modal.remove();

    const isAvailable = vehicle.status === 'AVAILABLE';
    const isRentedOrReserved = vehicle.status === 'RENTED' || vehicle.status === 'RESERVED';

    document.body.insertAdjacentHTML('beforeend', `
      <div id="fleetVehicleDetailModal" class="rf-modal-overlay active">
        <div class="rf-modal" style="max-width:520px;">
          <div class="rf-modal-header">
            <h3 class="rf-modal-title">${vehicle.modelName}</h3>
            <button class="rf-modal-close" onclick="document.getElementById('fleetVehicleDetailModal').remove()">&times;</button>
          </div>
          <div class="rf-modal-body">
            <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:1.2rem;">
              <span class="rf-badge ${isAvailable ? 'rf-badge-available' : (isRentedOrReserved ? 'rf-badge-reserved' : 'rf-badge-maintenance')}">${vehicle.status}</span>
              <span style="font-weight:900; font-size:1.1rem; color:#171717;">${vehicle.licensePlate}</span>
            </div>

            <div style="background:var(--rf-bg-main); border:1px solid var(--rf-border-light); padding:1.2rem; border-radius:var(--rf-radius-md); margin-bottom:1.5rem;">
              <div style="display:grid; grid-template-columns:1fr 1fr; gap:0.8rem; font-size:0.9rem; margin-bottom:0.8rem;">
                <div><span>Fuel / Battery:</span> <strong>${vehicle.fuelLevelPercent}%</strong></div>
                <div><span>Speed:</span> <strong>${vehicle.speedKmh} km/h</strong></div>
              </div>
              <div style="display:grid; grid-template-columns:1fr 1fr; gap:0.8rem; font-size:0.9rem;">
                <div><span>Daily Rate:</span> <strong>₹${vehicle.dailyRate || 2900} / day</strong></div>
                <div><span>Status:</span> <strong>${vehicle.status}</strong></div>
              </div>
            </div>

            ${isAvailable ? `
              <div style="background:var(--rf-accent-yellow-light); border:1.5px solid var(--rf-accent-yellow); padding:1rem; border-radius:var(--rf-radius-md); margin-bottom:1.5rem; text-align:center; font-weight:800; color:#171717;">
                ✓ This vehicle is currently AVAILABLE for instant rental!
              </div>
              <button onclick="document.getElementById('fleetVehicleDetailModal').remove(); App.openBookingModal(${vehicle.vehicleId});" class="rf-btn rf-btn-primary" style="width:100%; padding:0.85rem; font-size:1rem;">
                ⚡ Book This Available Vehicle Now
              </button>
            ` : `
              <div style="background:${isRentedOrReserved ? '#171717' : 'var(--rf-status-red-light)'}; color:${isRentedOrReserved ? 'var(--rf-accent-yellow-bright)' : 'var(--rf-status-red)'}; border:1.5px solid ${isRentedOrReserved ? 'var(--rf-accent-yellow)' : 'var(--rf-status-red)'}; padding:1.2rem; border-radius:var(--rf-radius-md); margin-bottom:1.5rem;">
                <div style="font-weight:900; font-size:0.95rem; margin-bottom:0.4rem;">
                  ${isRentedOrReserved ? '⏳ Currently Rented / Reserved' : '🔧 Under Maintenance'}
                </div>
                <div style="font-size:0.9rem;">
                  Expected Re-availability: <strong>${vehicle.expectedAvailableDate || 'Sep 12, 2026 at 18:00'}</strong>
                </div>
              </div>
              <button onclick="document.getElementById('fleetVehicleDetailModal').remove(); window.location.href='./search.html';" class="rf-btn rf-btn-secondary" style="width:100%;">
                Browse Other Available Vehicles
              </button>
            `}
          </div>
        </div>
      </div>
    `);
  }
}

window.FleetMapCanvas = FleetMapCanvas;
