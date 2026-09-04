/**
 * RideFleet Local Payment Gateway Simulation Module
 */
class PaymentSimulator {
  static openPaymentModal(booking, onPaymentSuccess) {
    let modal = document.getElementById('paymentSimulatorModal');
    if (modal) modal.remove();

    document.body.insertAdjacentHTML('beforeend', `
      <div id="paymentSimulatorModal" class="rf-modal-overlay active">
        <div class="rf-modal" style="max-width:540px;">
          <div class="rf-modal-header">
            <h3 class="rf-modal-title">RideFleet Payment Simulation</h3>
            <button class="rf-modal-close" onclick="document.getElementById('paymentSimulatorModal').remove()">&times;</button>
          </div>
          <div class="rf-modal-body">
            <div style="background:var(--rf-bg-main); padding:1rem; border-radius:var(--rf-radius-md); margin-bottom:1.5rem;">
              <div style="display:flex; justify-content:space-between; font-size:0.9rem; margin-bottom:0.4rem;">
                <span>Booking Ref:</span> <strong>${booking.bookingReference}</strong>
              </div>
              <div style="display:flex; justify-content:space-between; font-size:0.9rem; margin-bottom:0.4rem;">
                <span>Vehicle:</span> <strong>${booking.vehicle.model.brand.name} ${booking.vehicle.model.modelName}</strong>
              </div>
              <div style="display:flex; justify-content:space-between; font-size:1.1rem; color:var(--rf-accent-orange); font-weight:800; border-top:1px solid var(--rf-border-light); pt-2; margin-top:0.5rem;">
                <span>Total Amount Due:</span> <span>₹${booking.totalAmount}</span>
              </div>
            </div>

            <div id="paymentErrorMsg" style="display:none; background:var(--rf-status-red-light); color:var(--rf-status-red); padding:0.75rem; border-radius:4px; margin-bottom:1rem; font-size:0.85rem;"></div>

            <div class="rf-form-group" style="margin-bottom:1.2rem;">
              <label>Select Payment Method</label>
              <select id="simPaymentMethod" class="rf-form-control" onchange="PaymentSimulator.toggleMethodFields()">
                <option value="CARD">Credit / Debit Card Simulation</option>
                <option value="UPI">UPI Simulation (GPay / PhonePe / Paytm)</option>
                <option value="CASH">Cash on Pickup at Branch</option>
                <option value="WALLET">RideFleet Wallet</option>
              </select>
            </div>

            <div id="cardFields" style="display:block; margin-bottom:1.2rem;">
              <div class="rf-form-group" style="margin-bottom:0.75rem;">
                <label>Card Number (Simulated)</label>
                <input type="text" id="simCardNumber" class="rf-form-control" placeholder="4532 8921 7843 1092" value="4532892178431092">
              </div>
              <div style="display:grid; grid-template-columns:1fr 1fr; gap:0.75rem;">
                <div class="rf-form-group">
                  <label>Expiry</label>
                  <input type="text" class="rf-form-control" placeholder="12/28" value="12/28">
                </div>
                <div class="rf-form-group">
                  <label>CVV</label>
                  <input type="password" class="rf-form-control" placeholder="•••" value="888">
                </div>
              </div>
              <small style="color:var(--rf-text-muted); font-size:0.75rem;">Tip: Enter card ending in 9999 to simulate bank decline error.</small>
            </div>

            <div id="upiFields" style="display:none; margin-bottom:1.2rem;">
              <div class="rf-form-group">
                <label>Virtual Payment Address (VPA)</label>
                <input type="text" id="simUpiId" class="rf-form-control" placeholder="user@upi" value="customer@okaxis">
              </div>
            </div>

            <button onclick="PaymentSimulator.submitPayment(${booking.id}, ${booking.totalAmount})" class="rf-btn rf-btn-primary" style="width:100%; padding:0.85rem;">Pay & Confirm Booking</button>
          </div>
        </div>
      </div>
    `);
  }

  static toggleMethodFields() {
    const method = document.getElementById('simPaymentMethod').value;
    document.getElementById('cardFields').style.display = method === 'CARD' ? 'block' : 'none';
    document.getElementById('upiFields').style.display = method === 'UPI' ? 'block' : 'none';
  }

  static async submitPayment(bookingId, amount) {
    const errorDiv = document.getElementById('paymentErrorMsg');
    errorDiv.style.display = 'none';

    const paymentMethod = document.getElementById('simPaymentMethod').value;
    const cardNumber = document.getElementById('simCardNumber') ? document.getElementById('simCardNumber').value : '';
    const upiId = document.getElementById('simUpiId') ? document.getElementById('simUpiId').value : '';

    try {
      const paymentData = await ApiClient.post('/payments/process', {
        bookingId,
        paymentMethod,
        amount,
        cardNumber,
        upiId
      });

      document.getElementById('paymentSimulatorModal').remove();
      alert(`Payment Successful!\nTransaction Reference: ${paymentData.paymentReference}\nBooking Ref: ${paymentData.booking.bookingReference}\nStatus: CONFIRMED`);
      
      window.location.href = '../pages/customer-dashboard.html';
    } catch (err) {
      errorDiv.innerText = err.message || 'Payment simulation failed';
      errorDiv.style.display = 'block';
    }
  }
}

window.PaymentSimulator = PaymentSimulator;
