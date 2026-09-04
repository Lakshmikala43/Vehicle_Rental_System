/**
 * RideFleet Application Main Controller (app.js)
 * High-Performance Parallel Loader Architecture
 */

document.addEventListener('DOMContentLoaded', () => {
  App.init();
});

class App {
  static init() {
    // Parallelize catalog & recommendation requests for zero page lag
    if (document.getElementById('featuredVehiclesGrid')) {
      Promise.all([
        App.loadFeaturedVehicles(),
        App.loadRecommendations()
      ]).catch(console.error);
    }

    if (document.getElementById('searchVehiclesCatalogGrid')) {
      App.loadCatalogVehicles();
    }
  }

  static async loadFeaturedVehicles() {
    const grid = document.getElementById('featuredVehiclesGrid');
    if (!grid) return;

    try {
      const vehicles = await ApiClient.get('/vehicles?status=AVAILABLE');
      App.renderVehicleGrid(grid, vehicles.slice(0, 6));
    } catch (e) {
      grid.innerHTML = `<p style="color:var(--rf-text-muted);">Failed to load vehicles.</p>`;
    }
  }

  static async loadRecommendations() {
    const section = document.getElementById('recommendationsSection');
    if (!section) return;

    try {
      const recs = await ApiClient.get('/intelligence/recommendations');
      if (recs && recs.length > 0) {
        section.innerHTML = `
          <div style="background:var(--rf-surface-dark); color:var(--rf-text-on-dark); border-radius:var(--rf-radius-lg); padding:2.2rem; margin-bottom:3rem; border:2px solid var(--rf-accent-yellow); box-shadow:0 10px 30px rgba(0,0,0,0.5);">
            <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:1.6rem;">
              <div>
                <span style="color:var(--rf-accent-yellow-bright); font-size:0.82rem; font-weight:900; text-transform:uppercase; letter-spacing:1.2px;">Smart AI Recommendation Engine</span>
                <h3 style="font-size:1.65rem; font-weight:900; margin-top:0.2rem; color:#FFFFFF;">Recommended Vehicles For Your Profile</h3>
              </div>
              <span class="rf-badge rf-badge-available">96% Match Score</span>
            </div>
            <div class="rf-grid-3" id="recCardsGrid"></div>
          </div>
        `;
        const recGrid = document.getElementById('recCardsGrid');
        const vehicles = recs.map(r => r.vehicle);
        App.renderVehicleGrid(recGrid, vehicles.slice(0, 3));
      }
    } catch (e) {
      // Fallback
    }
  }

  static async loadCatalogVehicles() {
    const grid = document.getElementById('searchVehiclesCatalogGrid');
    if (!grid) return;

    const category = document.getElementById('filterCategory') ? document.getElementById('filterCategory').value : '';
    const fuelType = document.getElementById('filterFuel') ? document.getElementById('filterFuel').value : '';
    const transmission = document.getElementById('filterTransmission') ? document.getElementById('filterTransmission').value : '';

    let endpoint = `/vehicles?status=AVAILABLE`;
    if (category) endpoint += `&category=${category}`;
    if (fuelType) endpoint += `&fuelType=${fuelType}`;
    if (transmission) endpoint += `&transmission=${transmission}`;

    try {
      const vehicles = await ApiClient.get(endpoint);
      App.renderVehicleGrid(grid, vehicles);
    } catch (e) {
      grid.innerHTML = `<p style="color:var(--rf-text-muted);">No vehicles found matching criteria.</p>`;
    }
  }

  static renderVehicleGrid(container, vehicles) {
    if (!vehicles || vehicles.length === 0) {
      container.innerHTML = `<p style="grid-column:1/-1; text-align:center; padding:3rem; color:var(--rf-text-muted);">No vehicles currently available matching search criteria.</p>`;
      return;
    }

    const fallbackImages = [
      'https://images.unsplash.com/photo-1549399542-7e3f8b79c341?auto=format&fit=crop&w=600&q=80',
      'https://images.unsplash.com/photo-1552519507-da3b142c6e3d?auto=format&fit=crop&w=600&q=80',
      'https://images.unsplash.com/photo-1580273916550-e323be2ae537?auto=format&fit=crop&w=600&q=80',
      'https://images.unsplash.com/photo-1503376780353-7e6692767b70?auto=format&fit=crop&w=600&q=80'
    ];

    container.innerHTML = vehicles.map((v, idx) => `
      <div class="rf-card">
        <div class="rf-card-image-wrap">
          <img src="${fallbackImages[idx % fallbackImages.length]}" alt="${v.model.modelName}" loading="lazy">
          <div class="rf-card-badge-top">
            <span class="rf-badge ${v.status === 'AVAILABLE' ? 'rf-badge-available' : 'rf-badge-reserved'}">${v.status}</span>
          </div>
        </div>
        <div class="rf-card-body">
          <div class="rf-card-category">${v.model.type.category} • ${v.model.brand.name}</div>
          <h3 class="rf-card-title">${v.model.modelName}</h3>
          <div class="rf-card-specs">
            <div class="rf-spec-item">👥 ${v.model.seatingCapacity} Seats</div>
            <div class="rf-spec-item">⛽ ${v.model.fuelType}</div>
            <div class="rf-spec-item">⚙️ ${v.model.transmissionType}</div>
          </div>
          <div class="rf-card-footer">
            <div class="rf-card-price">₹${v.dailyRate} <span>/ day</span></div>
            <button onclick="App.openBookingModal(${v.id})" class="rf-btn rf-btn-primary" style="padding:0.5rem 1.1rem; font-size:0.85rem;">Book Ride</button>
          </div>
        </div>
      </div>
    `).join('');
  }

  static async openBookingModal(vehicleId) {
    if (!AuthManager.isAuthenticated()) {
      AuthManager.showLoginModal();
      return;
    }

    try {
      const [vehicle, addOns] = await Promise.all([
        ApiClient.get(`/vehicles/${vehicleId}`),
        ApiClient.get(`/pricing/add-ons`)
      ]);

      let modal = document.getElementById('bookingCheckoutModal');
      if (modal) modal.remove();

      document.body.insertAdjacentHTML('beforeend', `
        <div id="bookingCheckoutModal" class="rf-modal-overlay active">
          <div class="rf-modal" style="max-width:620px;">
            <div class="rf-modal-header">
              <h3 class="rf-modal-title">Book ${vehicle.model.brand.name} ${vehicle.model.modelName}</h3>
              <button class="rf-modal-close" onclick="document.getElementById('bookingCheckoutModal').remove()">&times;</button>
            </div>
            <div class="rf-modal-body">
              <form id="quickBookingForm" onsubmit="App.handleBookingSubmit(event, ${vehicle.id})">
                <div style="display:grid; grid-template-columns:1fr 1fr; gap:1rem; margin-bottom:1rem;">
                  <div class="rf-form-group">
                    <label>Pickup Date & Time</label>
                    <input type="datetime-local" id="bkPickupDate" class="rf-form-control" required value="2026-09-10T10:00">
                  </div>
                  <div class="rf-form-group">
                    <label>Return Date & Time</label>
                    <input type="datetime-local" id="bkReturnDate" class="rf-form-control" required value="2026-09-12T18:00">
                  </div>
                </div>

                <div style="display:grid; grid-template-columns:1fr 1fr; gap:1rem; margin-bottom:1rem;">
                  <div class="rf-form-group">
                    <label>Pickup Branch</label>
                    <select id="bkPickupBranch" class="rf-form-control">
                      <option value="1">Central Metro Branch (Bangalore)</option>
                      <option value="2">Airport Terminal 2 Branch (Bangalore)</option>
                      <option value="3">Gachibowli Financial Hub (Hyderabad)</option>
                    </select>
                  </div>
                  <div class="rf-form-group">
                    <label>Return Branch</label>
                    <select id="bkReturnBranch" class="rf-form-control">
                      <option value="1">Central Metro Branch (Bangalore)</option>
                      <option value="2">Airport Terminal 2 Branch (Bangalore)</option>
                    </select>
                  </div>
                </div>

                <div class="rf-form-group" style="margin-bottom:1rem;">
                  <label>Select Add-ons</label>
                  <div style="display:flex; flex-direction:column; gap:0.5rem;">
                    ${addOns.map(a => `
                      <label style="display:flex; align-items:center; gap:0.5rem; font-size:0.85rem; background:var(--rf-bg-main); padding:0.5rem 0.8rem; border-radius:4px; border:1px solid var(--rf-border-light);">
                        <input type="checkbox" name="bkAddOns" value="${a.id}"> ${a.name} (+₹${a.pricePerDay}/day)
                      </label>
                    `).join('')}
                  </div>
                </div>

                <div class="rf-form-group" style="margin-bottom:1.5rem;">
                  <label>Promo / Coupon Code</label>
                  <input type="text" id="bkCouponCode" class="rf-form-control" placeholder="e.g. WELCOME10 or FREEDOM500" value="WELCOME10">
                </div>

                <div id="bookingCollisionError" style="display:none; color:var(--rf-status-red); background:var(--rf-status-red-light); padding:0.75rem; border-radius:4px; margin-bottom:1rem; font-size:0.85rem;"></div>

                <button type="submit" class="rf-btn rf-btn-primary" style="width:100%; padding:0.85rem;">Proceed to Payment Simulation</button>
              </form>
            </div>
          </div>
        </div>
      `);
    } catch (e) {
      alert("Error loading vehicle details: " + e.message);
    }
  }

  static async handleBookingSubmit(e, vehicleId) {
    e.preventDefault();
    const errorDiv = document.getElementById('bookingCollisionError');
    errorDiv.style.display = 'none';

    const pickupDateTime = document.getElementById('bkPickupDate').value;
    const returnDateTime = document.getElementById('bkReturnDate').value;
    const pickupBranchId = parseInt(document.getElementById('bkPickupBranch').value);
    const returnBranchId = parseInt(document.getElementById('bkReturnBranch').value);
    const couponCode = document.getElementById('bkCouponCode').value;

    const addOnIds = Array.from(document.querySelectorAll('input[name="bkAddOns"]:checked')).map(cb => parseInt(cb.value));

    try {
      const booking = await ApiClient.post('/bookings', {
        vehicleId,
        pickupBranchId,
        returnBranchId,
        pickupDateTime,
        returnDateTime,
        addOnIds,
        couponCode
      });

      document.getElementById('bookingCheckoutModal').remove();
      PaymentSimulator.openPaymentModal(booking);
    } catch (err) {
      errorDiv.innerText = err.message || "Booking reservation failed due to double-booking conflict or date error.";
      errorDiv.style.display = 'block';
    }
  }
}

window.App = App;
