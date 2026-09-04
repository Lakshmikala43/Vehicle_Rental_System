/**
 * RentRide Handcrafted Auth Manager & Strict Role Router Module
 * Ensures Uniform Brand Logo & Title Everywhere + Strict Role Scoped Dashboard Navigation
 */
class AuthManager {
  static currentTab = 'customer';

  static getUser() {
    const userStr = localStorage.getItem('rf_user');
    return userStr ? JSON.parse(userStr) : null;
  }

  static isAuthenticated() {
    return !!localStorage.getItem('rf_auth_token');
  }

  static hasRole(role) {
    const user = AuthManager.getUser();
    if (!user || !user.roles) return false;
    return user.roles.includes(role);
  }

  static login(token, user) {
    localStorage.setItem('rf_auth_token', token);
    localStorage.setItem('rf_user', JSON.stringify(user));
    AuthManager.updateAuthUI();
  }

  static logout() {
    localStorage.removeItem('rf_auth_token');
    localStorage.removeItem('rf_user');
    window.location.href = AuthManager.getRelativePath('index.html');
  }

  static getRelativePath(targetFile) {
    const path = window.location.pathname;
    if (path.includes('/pages/')) {
      return targetFile === 'index.html' ? '../index.html' : `./${targetFile}`;
    }
    return targetFile === 'index.html' ? './index.html' : `./pages/${targetFile}`;
  }

  static updateAuthUI() {
    const authActions = document.getElementById('navAuthActions');
    const isHomePage = window.location.pathname.endsWith('index.html') || window.location.pathname === '/' || window.location.pathname.endsWith('/frontend/');

    // On Home Page (index.html), hide all navigation links for clean login focus
    const navList = document.querySelector('.rf-nav');
    if (navList && isHomePage) {
      navList.style.display = 'none';
    }

    if (!authActions) return;

    if (AuthManager.isAuthenticated()) {
      const user = AuthManager.getUser();
      // Render ONLY user greeting and Sign Out button (NO cross-dashboard links!)
      authActions.innerHTML = `
        <div style="display:flex; align-items:center; gap:0.85rem;">
          <span style="font-size:0.88rem; color:#E2E8F0; font-weight:600;">Hello, <strong style="color:var(--rf-accent-yellow-bright);">${user.fullName}</strong></span>
          <button onclick="AuthManager.logout()" class="rf-btn rf-btn-secondary" style="padding:0.45rem 1rem; font-size:0.85rem;">Sign Out</button>
        </div>
      `;
    } else {
      if (isHomePage) {
        authActions.innerHTML = ''; // Keep home page header clean
      } else {
        authActions.innerHTML = `
          <button onclick="AuthManager.showLoginModal('customer')" class="rf-btn rf-btn-primary" style="padding:0.45rem 1rem; font-size:0.85rem;">Portal Sign In</button>
        `;
      }
    }
  }

  static showLoginModal(defaultTab = 'customer') {
    AuthManager.currentTab = defaultTab;
    let modal = document.getElementById('loginModalOverlay');
    if (modal) modal.remove();

    document.body.insertAdjacentHTML('beforeend', `
      <div id="loginModalOverlay" class="rf-modal-overlay active">
        <div class="rf-modal" style="max-width:540px;">
          
          <div style="background:#000000; border-bottom:2px solid var(--rf-accent-yellow); padding:1rem 1.5rem 0 1.5rem;">
            <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:1rem;">
              <h3 style="font-size:1.3rem; font-weight:900; color:var(--rf-accent-yellow-bright);">RentRide Portal Sign In</h3>
              <button class="rf-modal-close" onclick="document.getElementById('loginModalOverlay').remove()">&times;</button>
            </div>
            
            <div style="display:flex; gap:0.5rem;">
              <button onclick="AuthManager.switchLoginTab('customer')" id="tabLoginCustomer" class="rf-tab-btn active">👤 Customer Login</button>
              <button onclick="AuthManager.switchLoginTab('staff')" id="tabLoginStaff" class="rf-tab-btn">🛠️ Staff Login</button>
              <button onclick="AuthManager.switchLoginTab('admin')" id="tabLoginAdmin" class="rf-tab-btn">⚡ Admin Login</button>
            </div>
          </div>

          <div class="rf-modal-body" style="padding:1.8rem;">
            <div id="loginError" style="display:none; color:var(--rf-status-red); background:var(--rf-status-red-light); padding:0.8rem; border-radius:6px; margin-bottom:1.2rem; font-size:0.88rem; font-weight:700;"></div>
            
            <form id="loginForm" onsubmit="AuthManager.handleLoginSubmit(event)">
              <div class="rf-form-group" style="margin-bottom:1.1rem;">
                <label id="loginUsernameLabel">Customer Username / Email</label>
                <input type="text" id="loginUsername" class="rf-form-control" placeholder="Enter username" required value="customer">
              </div>
              <div class="rf-form-group" style="margin-bottom:1.6rem;">
                <label>Password</label>
                <input type="password" id="loginPassword" class="rf-form-control" placeholder="••••••••" required value="customer123">
              </div>
              <button type="submit" id="loginSubmitBtn" class="rf-btn rf-btn-primary" style="width:100%; padding:0.85rem; font-size:1rem;">
                Sign In to Customer Portal
              </button>
            </form>

            <div id="tabHintText" style="margin-top:1.5rem; text-align:center; padding:0.8rem; background:var(--rf-bg-main); border-radius:6px; border:1px solid var(--rf-border-light); font-size:0.85rem; color:var(--rf-text-secondary);">
              <strong>Demo Customer:</strong> Use <code>customer</code> / <code>customer123</code>
            </div>
          </div>
        </div>
      </div>
    `);

    AuthManager.switchLoginTab(defaultTab);
  }

  static switchLoginTab(tab) {
    AuthManager.currentTab = tab;
    const btnCust = document.getElementById('tabLoginCustomer');
    const btnStaff = document.getElementById('tabLoginStaff');
    const btnAdmin = document.getElementById('tabLoginAdmin');
    const label = document.getElementById('loginUsernameLabel');
    const inputUser = document.getElementById('loginUsername');
    const inputPass = document.getElementById('loginPassword');
    const submitBtn = document.getElementById('loginSubmitBtn');
    const hint = document.getElementById('tabHintText');

    if (!btnCust) return;

    btnCust.classList.remove('active');
    btnStaff.classList.remove('active');
    btnAdmin.classList.remove('active');

    if (tab === 'customer') {
      btnCust.classList.add('active');
      label.innerText = 'Customer Username / Email';
      inputUser.value = 'customer';
      inputPass.value = 'customer123';
      submitBtn.innerText = 'Sign In to Customer Portal';
      hint.innerHTML = '<strong>Demo Customer:</strong> Use <code>customer</code> / <code>customer123</code>';
    } else if (tab === 'staff') {
      btnStaff.classList.add('active');
      label.innerText = 'Staff Employee Code / Username';
      inputUser.value = 'staff';
      inputPass.value = 'staff123';
      submitBtn.innerText = 'Sign In to Staff Operations Portal';
      hint.innerHTML = '<strong>Demo Staff / Operations:</strong> Use <code>staff</code> / <code>staff123</code>';
    } else if (tab === 'admin') {
      btnAdmin.classList.add('active');
      label.innerText = 'Administrator Username';
      inputUser.value = 'admin';
      inputPass.value = 'admin123';
      submitBtn.innerText = 'Sign In to Admin Executive Dashboard';
      hint.innerHTML = '<strong>Demo Admin Executive:</strong> Use <code>admin</code> / <code>admin123</code>';
    }
  }

  static async handleLoginSubmit(e) {
    e.preventDefault();
    const errorDiv = document.getElementById('loginError');
    errorDiv.style.display = 'none';

    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;

    try {
      const data = await ApiClient.post('/auth/login', { username, password });
      AuthManager.login(data.token, data);
      
      const modal = document.getElementById('loginModalOverlay');
      if (modal) modal.remove();

      if (data.roles.includes('ADMIN')) {
        window.location.href = AuthManager.getRelativePath('admin-dashboard.html');
      } else if (data.roles.includes('STAFF') || data.roles.includes('FLEET_MANAGER')) {
        window.location.href = AuthManager.getRelativePath('staff-portal.html');
      } else {
        window.location.href = AuthManager.getRelativePath('customer-dashboard.html');
      }
    } catch (err) {
      errorDiv.innerText = err.message || 'Authentication failed.';
      errorDiv.style.display = 'block';
    }
  }
}

document.addEventListener('DOMContentLoaded', () => {
  AuthManager.updateAuthUI();
});

window.AuthManager = AuthManager;
