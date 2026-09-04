/**
 * RentRide High-Performance API Client & In-Memory Data Store Engine
 * Includes Top 25 Best-Selling Cars & Motorbikes Catalog Data
 */
const API_BASE_URL = 'http://localhost:8080/api';

class ApiClient {
  static isBackendAvailable = null;

  static getAuthToken() {
    return localStorage.getItem('rf_auth_token') || '';
  }

  static getHeaders() {
    const headers = { 'Content-Type': 'application/json' };
    const token = ApiClient.getAuthToken();
    if (token) headers['Authorization'] = `Bearer ${token}`;
    return headers;
  }

  static async checkBackendStatus() {
    if (ApiClient.isBackendAvailable !== null) return ApiClient.isBackendAvailable;
    try {
      const controller = new AbortController();
      const id = setTimeout(() => controller.abort(), 200);
      const res = await fetch(`${API_BASE_URL}/vehicles`, { signal: controller.signal });
      clearTimeout(id);
      ApiClient.isBackendAvailable = res.ok;
    } catch (e) {
      ApiClient.isBackendAvailable = false;
    }
    return ApiClient.isBackendAvailable;
  }

  static async request(endpoint, options = {}) {
    const isOnline = await ApiClient.checkBackendStatus();

    if (!isOnline) {
      return MockDataStore.handle(endpoint, options);
    }

    const url = `${API_BASE_URL}${endpoint}`;
    const config = { headers: ApiClient.getHeaders(), ...options };

    try {
      const response = await fetch(url, config);
      const json = await response.json();
      if (!response.ok || !json.success) throw new Error(json.message || `HTTP ${response.status}`);
      return json.data;
    } catch (error) {
      ApiClient.isBackendAvailable = false;
      return MockDataStore.handle(endpoint, options);
    }
  }

  static get(endpoint) {
    return ApiClient.request(endpoint, { method: 'GET' });
  }

  static post(endpoint, body) {
    return ApiClient.request(endpoint, {
      method: 'POST',
      body: JSON.stringify(body)
    });
  }

  static patch(endpoint, params = {}) {
    let url = endpoint;
    const queryString = new URLSearchParams(params).toString();
    if (queryString) url += `?${queryString}`;
    return ApiClient.request(url, { method: 'PATCH' });
  }
}

/**
 * High-Speed In-Memory & LocalStorage Data Engine
 */
class MockDataStore {
  static init() {
    // Clear legacy seed to ensure fresh Top 25 Best-Selling Cars & Bikes catalog
    if (localStorage.getItem('rf_db_seeded_v3') !== 'true') {
      localStorage.removeItem('rf_vehicles');
      localStorage.setItem('rf_db_seeded_v3', 'true');
    }

    if (!localStorage.getItem('rf_vehicles') || JSON.parse(localStorage.getItem('rf_vehicles')).length < 10) {
      const branches = [
        { id: 1, branchCode: 'BR-BLR-01', name: 'Central Metro Hub', address: 'MG Road, Brigade Junction', city: 'Bangalore', latitude: 12.9716, longitude: 77.5946 },
        { id: 2, branchCode: 'BR-BLR-02', name: 'Airport Terminal 2 Branch', address: 'KIA Terminal 2 Hub', city: 'Bangalore', latitude: 13.1986, longitude: 77.7066 },
        { id: 3, branchCode: 'BR-HYD-01', name: 'Gachibowli Financial Hub', address: 'Financial District Main Rd', city: 'Hyderabad', latitude: 17.4401, longitude: 78.3489 },
        { id: 4, branchCode: 'BR-MUM-01', name: 'Bandra Kurla Hub', address: 'BKC G Block', city: 'Mumbai', latitude: 19.0600, longitude: 72.8680 }
      ];

      // TOP 25 BEST-SELLING CARS & MOTORBIKES CATALOG
      const rawCatalog = [
        { name: 'Swift ZXi', brand: 'Maruti Suzuki', cat: 'Hatchback', fuel: 'PETROL', trans: 'MANUAL', seats: 5, rate: 1800 },
        { name: 'Baleno Alpha', brand: 'Maruti Suzuki', cat: 'Hatchback', fuel: 'PETROL', trans: 'AUTOMATIC', seats: 5, rate: 2100 },
        { name: 'Brezza ZXi+', brand: 'Maruti Suzuki', cat: 'SUV', fuel: 'PETROL', trans: 'AUTOMATIC', seats: 5, rate: 2600 },
        { name: 'Ertiga ZXi', brand: 'Maruti Suzuki', cat: 'SUV', fuel: 'PETROL', trans: 'MANUAL', seats: 7, rate: 2800 },
        { name: 'Creta SX(O)', brand: 'Hyundai', cat: 'SUV', fuel: 'PETROL', trans: 'AUTOMATIC', seats: 5, rate: 3200 },
        { name: 'Dzire VXi', brand: 'Maruti Suzuki', cat: 'Sedan', fuel: 'PETROL', trans: 'MANUAL', seats: 5, rate: 1900 },
        { name: 'Fronx Alpha Turbo', brand: 'Maruti Suzuki', cat: 'SUV', fuel: 'PETROL', trans: 'AUTOMATIC', seats: 5, rate: 2400 },
        { name: 'WagonR ZXi', brand: 'Maruti Suzuki', cat: 'Hatchback', fuel: 'PETROL', trans: 'MANUAL', seats: 5, rate: 1500 },
        { name: 'Nexon Fearless+', brand: 'Tata Motors', cat: 'SUV', fuel: 'PETROL', trans: 'AUTOMATIC', seats: 5, rate: 2700 },
        { name: 'Eeco 7-STR', brand: 'Maruti Suzuki', cat: 'SUV', fuel: 'PETROL', trans: 'MANUAL', seats: 7, rate: 1600 },
        { name: 'Punch Creative', brand: 'Tata Motors', cat: 'SUV', fuel: 'PETROL', trans: 'MANUAL', seats: 5, rate: 2000 },
        { name: 'Scorpio-N Z8L', brand: 'Mahindra', cat: 'SUV', fuel: 'DIESEL', trans: 'AUTOMATIC', seats: 7, rate: 4200 },
        { name: 'Venue SX(O)', brand: 'Hyundai', cat: 'SUV', fuel: 'PETROL', trans: 'AUTOMATIC', seats: 5, rate: 2500 },
        { name: 'Seltos GTX+ Turbo', brand: 'Kia Motors', cat: 'SUV', fuel: 'PETROL', trans: 'AUTOMATIC', seats: 5, rate: 3400 },
        { name: 'Grand Vitara Alpha Hybrid', brand: 'Maruti Suzuki', cat: 'SUV', fuel: 'PETROL', trans: 'AUTOMATIC', seats: 5, rate: 3500 },
        { name: 'Tiago XZ+', brand: 'Tata Motors', cat: 'Hatchback', fuel: 'PETROL', trans: 'MANUAL', seats: 5, rate: 1600 },
        { name: 'Innova Crysta VX', brand: 'Toyota', cat: 'SUV', fuel: 'DIESEL', trans: 'AUTOMATIC', seats: 7, rate: 3900 },
        { name: 'Bolero Neo N10', brand: 'Mahindra', cat: 'SUV', fuel: 'DIESEL', trans: 'MANUAL', seats: 7, rate: 2600 },
        { name: 'Altroz XZ+', brand: 'Tata Motors', cat: 'Hatchback', fuel: 'DIESEL', trans: 'MANUAL', seats: 5, rate: 2100 },
        { name: 'Alto K10 VXi+', brand: 'Maruti Suzuki', cat: 'Hatchback', fuel: 'PETROL', trans: 'MANUAL', seats: 5, rate: 1200 },
        { name: 'Exter SX(O) Connect', brand: 'Hyundai', cat: 'SUV', fuel: 'PETROL', trans: 'AUTOMATIC', seats: 5, rate: 2200 },
        { name: 'XUV700 AX7 Luxury', brand: 'Mahindra', cat: 'SUV', fuel: 'DIESEL', trans: 'AUTOMATIC', seats: 7, rate: 4800 },
        { name: 'Carens Luxury Plus', brand: 'Kia Motors', cat: 'SUV', fuel: 'DIESEL', trans: 'AUTOMATIC', seats: 7, rate: 3300 },
        { name: 'Grand i10 Nios Asta', brand: 'Hyundai', cat: 'Hatchback', fuel: 'PETROL', trans: 'AUTOMATIC', seats: 5, rate: 1800 },
        { name: 'Thar LX 4x4', brand: 'Mahindra', cat: 'SUV', fuel: 'DIESEL', trans: 'AUTOMATIC', seats: 4, rate: 3800 },
        // MOTORBIKES
        { name: 'Himalayan 450 Sherpa', brand: 'Royal Enfield', cat: 'Motorbike', fuel: 'PETROL', trans: 'MANUAL', seats: 2, rate: 1800 },
        { name: 'Ninja 400 ABS', brand: 'Kawasaki', cat: 'Motorbike', fuel: 'PETROL', trans: 'MANUAL', seats: 2, rate: 2500 },
        { name: '450X Apex EV', brand: 'Ather Energy', cat: 'Motorbike', fuel: 'ELECTRIC', trans: 'AUTOMATIC', seats: 2, rate: 1100 },
        { name: 'Hunter 350 Dapper', brand: 'Royal Enfield', cat: 'Motorbike', fuel: 'PETROL', trans: 'MANUAL', seats: 2, rate: 1400 },
        { name: 'R 1250 GS Adventure', brand: 'BMW Motorrad', cat: 'Motorbike', fuel: 'PETROL', trans: 'MANUAL', seats: 2, rate: 4500 }
      ];

      const vehicles = rawCatalog.map((item, idx) => ({
        id: idx + 1,
        vin: `RFVIN${10000000 + idx + 1}`,
        licensePlate: `KA-01-RF-${1000 + idx + 1}`,
        color: 'Pearl Executive',
        model: {
          modelName: item.name,
          modelYear: 2024,
          seatingCapacity: item.seats,
          fuelType: item.fuel,
          transmissionType: item.trans,
          brand: { name: item.brand },
          type: { category: item.cat }
        },
        branch: branches[idx % branches.length],
        odometerKm: 8500 + (idx * 1200),
        hourlyRate: Math.round(item.rate / 14),
        dailyRate: item.rate,
        securityDeposit: item.cat === 'Motorbike' ? 2000 : 5000,
        status: 'AVAILABLE',
        fuelLevelPercent: 95 - (idx % 10),
        healthScore: 98
      }));

      const addOns = [
        { id: 1, code: 'ADD_CHILD_SEAT', name: 'Infant Safety Child Seat', pricePerDay: 350 },
        { id: 2, code: 'ADD_GPS_HW', name: 'Portable GPS Unit', pricePerDay: 200 },
        { id: 3, code: 'ADD_INS_ZERO', name: 'Zero-Depreciation Insurance Shield', pricePerDay: 650 },
        { id: 4, code: 'ADD_ROADSIDE', name: '24/7 Roadside Emergency Support', pricePerDay: 150 }
      ];

      const initialBookings = [
        {
          id: 1, bookingReference: 'RF-BK-984321',
          customer: { id: 1, fullName: 'Ananya Roy', username: 'customer' },
          vehicle: vehicles[0], pickupBranch: branches[0], returnBranch: branches[0],
          pickupDateTime: '2026-09-10T10:00', returnDateTime: '2026-09-12T18:00',
          status: 'CONFIRMED', baseAmount: 5800, addOnsAmount: 700, discountAmount: 580,
          taxAmount: 1065.6, securityDeposit: 5000, totalAmount: 11985.6, createdAt: '2026-09-04T10:00:00'
        }
      ];

      const initialInvoices = [
        {
          id: 1, invoiceNumber: 'INV-RF-984321', bookingId: 1,
          subtotal: 5920, taxTotal: 1065.6, grandTotal: 11985.6, issuedAt: '2026-09-04T10:05:00'
        }
      ];

      const initialMaintenance = [
        {
          id: 1, workOrderNumber: 'WO-RF-98431', vehicle: vehicles[1],
          vehicleLabel: 'BMW 5 Series (KA-01-RF-1002)', maintenanceType: 'PREVENTIVE',
          vendorName: 'Bosch Authorized Service Hub', cost: 14500, status: 'COMPLETED'
        },
        {
          id: 2, workOrderNumber: 'WO-RF-98432', vehicle: vehicles[0],
          vehicleLabel: 'Toyota Innova Crysta (KA-01-RF-1001)', maintenanceType: 'CORRECTIVE',
          vendorName: 'Toyota Express Service Center', cost: 8200, status: 'IN_PROGRESS'
        }
      ];

      localStorage.setItem('rf_branches', JSON.stringify(branches));
      localStorage.setItem('rf_vehicles', JSON.stringify(vehicles));
      localStorage.setItem('rf_addons', JSON.stringify(addOns));
      localStorage.setItem('rf_bookings', JSON.stringify(initialBookings));
      localStorage.setItem('rf_invoices', JSON.stringify(initialInvoices));
      localStorage.setItem('rf_maintenance', JSON.stringify(initialMaintenance));
      localStorage.setItem('rf_inspections', JSON.stringify([]));
      localStorage.setItem('rf_damages', JSON.stringify([]));
      localStorage.setItem('rf_db_seeded', 'true');
    }
  }

  static handle(endpoint, options) {
    MockDataStore.init();
    const method = options.method || 'GET';
    const body = options.body ? JSON.parse(options.body) : {};

    // Auth
    if (endpoint.startsWith('/auth/login')) {
      const username = body.username || 'customer';
      let roles = ['CUSTOMER'];
      let fullName = 'Ananya Roy';
      if (username === 'admin') { roles = ['ADMIN']; fullName = 'Executive Admin'; }
      if (username === 'staff') { roles = ['STAFF']; fullName = 'Branch Officer'; }
      if (username === 'fleetmgr') { roles = ['FLEET_MANAGER']; fullName = 'Fleet Lead'; }

      return { token: 'mock_jwt_token_' + Date.now(), id: 1, username, email: `${username}@rentride.com`, fullName, roles };
    }

    if (endpoint.startsWith('/auth/me')) {
      return JSON.parse(localStorage.getItem('rf_user')) || { id: 1, username: 'customer', fullName: 'Ananya Roy', roles: ['CUSTOMER'] };
    }

    // Maintenance Endpoints
    if (endpoint.startsWith('/maintenance/work-orders') && endpoint.includes('/complete') && method === 'PATCH') {
      const parts = endpoint.split('/');
      const woId = parseInt(parts[3]);
      const maintenanceList = JSON.parse(localStorage.getItem('rf_maintenance')) || [];
      const item = maintenanceList.find(m => m.id === woId);
      if (item) {
        item.status = 'COMPLETED';
        localStorage.setItem('rf_maintenance', JSON.stringify(maintenanceList));

        const vehicles = JSON.parse(localStorage.getItem('rf_vehicles'));
        const targetV = vehicles.find(v => v.id === item.vehicle.id);
        if (targetV) {
          targetV.status = 'AVAILABLE';
          targetV.healthScore = 100;
          localStorage.setItem('rf_vehicles', JSON.stringify(vehicles));
        }
      }
      return item;
    }

    if (endpoint.startsWith('/maintenance/work-orders') && method === 'POST') {
      const maintenanceList = JSON.parse(localStorage.getItem('rf_maintenance')) || [];
      const vehicles = JSON.parse(localStorage.getItem('rf_vehicles'));
      const vehicle = vehicles.find(v => v.id === body.vehicleId) || vehicles[0];

      const newWO = {
        id: maintenanceList.length + 1,
        workOrderNumber: 'WO-RF-' + Math.floor(10000 + Math.random() * 90000),
        vehicle: vehicle,
        vehicleLabel: `${vehicle.model.brand.name} ${vehicle.model.modelName} (${vehicle.licensePlate})`,
        maintenanceType: body.maintenanceType || 'PREVENTIVE',
        vendorName: body.vendorName || 'Authorized Service Station',
        cost: body.cost || 5000,
        status: 'IN_PROGRESS'
      };

      vehicle.status = 'MAINTENANCE';
      localStorage.setItem('rf_vehicles', JSON.stringify(vehicles));

      maintenanceList.unshift(newWO);
      localStorage.setItem('rf_maintenance', JSON.stringify(maintenanceList));
      return newWO;
    }

    if (endpoint.startsWith('/maintenance')) {
      return JSON.parse(localStorage.getItem('rf_maintenance')) || [];
    }

    if (endpoint.startsWith('/vehicles')) {
      const vehicles = JSON.parse(localStorage.getItem('rf_vehicles')) || [];
      const idMatch = endpoint.match(/\/vehicles\/(\d+)/);
      if (idMatch) return vehicles.find(v => v.id === parseInt(idMatch[1])) || vehicles[0];

      const urlObj = new URL('http://dummy' + endpoint);
      const category = urlObj.searchParams.get('category');
      const fuelType = urlObj.searchParams.get('fuelType');
      const transmission = urlObj.searchParams.get('transmission');

      return vehicles.filter(v => {
        if (category && v.model.type.category.toLowerCase() !== category.toLowerCase()) return false;
        if (fuelType && v.model.fuelType.toLowerCase() !== fuelType.toLowerCase()) return false;
        if (transmission && v.model.transmissionType.toLowerCase() !== transmission.toLowerCase()) return false;
        return true;
      });
    }

    if (endpoint.startsWith('/branches')) return JSON.parse(localStorage.getItem('rf_branches')) || [];
    if (endpoint.startsWith('/pricing/add-ons')) return JSON.parse(localStorage.getItem('rf_addons')) || [];

    if (endpoint.startsWith('/bookings') && method === 'POST') {
      const vehicles = JSON.parse(localStorage.getItem('rf_vehicles'));
      const vehicle = vehicles.find(v => v.id === body.vehicleId) || vehicles[0];
      const branches = JSON.parse(localStorage.getItem('rf_branches'));
      const pickupBranch = branches.find(b => b.id === body.pickupBranchId) || branches[0];
      const returnBranch = branches.find(b => b.id === body.returnBranchId) || branches[0];
      const bookings = JSON.parse(localStorage.getItem('rf_bookings')) || [];

      const user = JSON.parse(localStorage.getItem('rf_user')) || { id: 1, fullName: 'Ananya Roy', username: 'customer' };

      const newBooking = {
        id: bookings.length + 1,
        bookingReference: 'RF-BK-' + Math.floor(100000 + Math.random() * 900000),
        customer: user,
        vehicle,
        pickupBranch,
        returnBranch,
        pickupDateTime: body.pickupDateTime,
        returnDateTime: body.returnDateTime,
        status: 'PAYMENT_PENDING',
        baseAmount: vehicle.dailyRate * 2,
        addOnsAmount: 500,
        discountAmount: 290,
        taxAmount: 937.8,
        securityDeposit: vehicle.securityDeposit,
        totalAmount: (vehicle.dailyRate * 2) + 500 - 290 + 937.8 + vehicle.securityDeposit,
        createdAt: new Date().toISOString()
      };

      bookings.unshift(newBooking);
      localStorage.setItem('rf_bookings', JSON.stringify(bookings));
      return newBooking;
    }

    if (endpoint.startsWith('/bookings')) return JSON.parse(localStorage.getItem('rf_bookings')) || [];

    if (endpoint.startsWith('/payments/process') && method === 'POST') {
      const cardNumber = body.cardNumber || '';
      const upiId = body.upiId || '';

      if (cardNumber.endsWith('9999') || upiId.toLowerCase() === 'fail@upi') {
        throw new Error("Payment declined by simulated issuing bank (Test Rule Triggered)");
      }

      const bookings = JSON.parse(localStorage.getItem('rf_bookings'));
      const booking = bookings.find(b => b.id === body.bookingId) || booking[0];
      booking.status = 'CONFIRMED';
      localStorage.setItem('rf_bookings', JSON.stringify(bookings));

      const invoices = JSON.parse(localStorage.getItem('rf_invoices')) || [];
      const newInvoice = {
        id: invoices.length + 1,
        invoiceNumber: 'INV-RF-' + Math.floor(10000 + Math.random() * 90000),
        bookingId: booking.id,
        subtotal: booking.baseAmount + booking.addOnsAmount - booking.discountAmount,
        taxTotal: booking.taxAmount,
        grandTotal: booking.totalAmount,
        issuedAt: new Date().toISOString()
      };
      invoices.unshift(newInvoice);
      localStorage.setItem('rf_invoices', JSON.stringify(invoices));

      return {
        id: Date.now(),
        paymentReference: 'PAY-RF-' + Math.floor(10000000 + Math.random() * 90000000),
        paymentMethod: body.paymentMethod,
        paymentStatus: 'SUCCESS',
        amount: body.amount,
        booking
      };
    }

    if (endpoint.startsWith('/payments/invoices/booking/')) {
      const bId = parseInt(endpoint.split('/').pop());
      const invoices = JSON.parse(localStorage.getItem('rf_invoices')) || [];
      return invoices.find(i => i.bookingId === bId) || invoices[0];
    }

    if (endpoint.startsWith('/inspection/damage') && method === 'POST') {
      const damages = JSON.parse(localStorage.getItem('rf_damages')) || [];
      damages.unshift(body);
      localStorage.setItem('rf_damages', JSON.stringify(damages));
      return { id: Date.now(), damageCode: 'DMG-RF-8821', status: 'REPORTED', ...body };
    }

    if (endpoint.startsWith('/inspection') && method === 'POST') {
      const inspections = JSON.parse(localStorage.getItem('rf_inspections')) || [];
      inspections.unshift(body);
      localStorage.setItem('rf_inspections', JSON.stringify(inspections));

      if (body.booking && body.booking.id) {
        const bookings = JSON.parse(localStorage.getItem('rf_bookings'));
        const target = bookings.find(b => b.id === body.booking.id);
        if (target) {
          target.status = body.inspectionType === 'PICKUP' ? 'ACTIVE' : 'COMPLETED';
          localStorage.setItem('rf_bookings', JSON.stringify(bookings));
        }
      }
      return { id: Date.now(), ...body };
    }

    if (endpoint.startsWith('/intelligence/recommendations')) {
      const vehicles = JSON.parse(localStorage.getItem('rf_vehicles'));
      return vehicles.slice(0, 3).map(v => ({ vehicle: v, recommendationScorePercent: 96 }));
    }

    if (endpoint.startsWith('/analytics/summary')) {
      const vehicles = JSON.parse(localStorage.getItem('rf_vehicles')) || [];
      const bookings = JSON.parse(localStorage.getItem('rf_bookings')) || [];
      let totalRev = bookings.reduce((sum, b) => sum + (b.totalAmount || 0), 0);

      return {
        totalVehicles: vehicles.length,
        availableVehicles: vehicles.filter(v => v.status === 'AVAILABLE').length,
        rentedVehicles: vehicles.filter(v => v.status === 'RENTED' || v.status === 'RESERVED').length,
        totalRevenue: Math.round(totalRev),
        fleetUtilizationPercent: 66.7
      };
    }

    if (endpoint.startsWith('/fleet/telemetry')) {
      const vehicles = JSON.parse(localStorage.getItem('rf_vehicles')) || [];
      return vehicles.map(v => ({
        vehicleId: v.id,
        licensePlate: v.licensePlate,
        modelName: `${v.model.brand.name} ${v.model.modelName}`,
        status: v.status,
        speedKmh: v.status === 'RENTED' ? 55 : 0,
        fuelLevelPercent: v.fuelLevelPercent,
        healthScore: v.healthScore
      }));
    }

    return [];
  }
}

window.ApiClient = ApiClient;
window.MockDataStore = MockDataStore;
