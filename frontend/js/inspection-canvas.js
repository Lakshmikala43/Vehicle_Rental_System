/**
 * RideFleet Interactive Visual Inspection Car Diagram Module
 */
class InspectionCanvas {
  static renderDiagram(containerId, onPartClick) {
    const container = document.getElementById(containerId);
    if (!container) return;

    container.innerHTML = `
      <div class="rf-inspection-diagram-wrap">
        <h4 style="margin-bottom:1rem; font-weight:800; color:var(--rf-text-primary);">Visual Inspection Diagram</h4>
        <p style="font-size:0.85rem; color:var(--rf-text-secondary); margin-bottom:1.5rem;">Click on any vehicle component area below to record or view damage assessments.</p>
        
        <svg class="rf-car-schema-svg" viewBox="0 0 600 300">
          <!-- Roof -->
          <rect id="part_roof" class="rf-car-part" x="220" y="70" width="160" height="160" rx="20" />
          <!-- Hood / Front -->
          <path id="part_hood" class="rf-car-part" d="M 380 90 L 520 110 C 540 150, 540 150, 520 190 L 380 210 Z" />
          <!-- Trunk / Rear -->
          <path id="part_trunk" class="rf-car-part" d="M 220 90 L 80 110 C 60 150, 60 150, 80 190 L 220 210 Z" />
          <!-- Left Door Upper -->
          <rect id="part_door_fl" class="rf-car-part" x="300" y="30" width="80" height="35" rx="5" />
          <!-- Left Door Rear -->
          <rect id="part_door_rl" class="rf-car-part" x="220" y="30" width="75" height="35" rx="5" />
          <!-- Right Door Upper -->
          <rect id="part_door_fr" class="rf-car-part" x="300" y="235" width="80" height="35" rx="5" />
          <!-- Right Door Rear -->
          <rect id="part_door_rr" class="rf-car-part" x="220" y="235" width="75" height="35" rx="5" />
          
          <!-- Wheels -->
          <circle id="part_wheel_fl" class="rf-car-part" cx="440" cy="25" r="18" fill="#171717" />
          <circle id="part_wheel_fr" class="rf-car-part" cx="440" cy="275" r="18" fill="#171717" />
          <circle id="part_wheel_rl" class="rf-car-part" cx="140" cy="25" r="18" fill="#171717" />
          <circle id="part_wheel_rr" class="rf-car-part" cx="140" cy="275" r="18" fill="#171717" />

          <!-- Labels -->
          <text x="450" y="155" font-weight="bold" font-size="14" fill="#171717">FRONT HOOD</text>
          <text x="280" y="155" font-weight="bold" font-size="14" fill="#171717">ROOF / CABIN</text>
          <text x="120" y="155" font-weight="bold" font-size="14" fill="#171717">REAR TRUNK</text>
        </svg>
        
        <div style="display:flex; justify-content:center; gap:2rem; margin-top:1.5rem; font-size:0.85rem;">
          <span style="display:flex; align-items:center; gap:0.4rem;"><span style="width:12px; height:12px; background:#EAE5DB; border:1px solid #171717; border-radius:2px;"></span> Normal OK</span>
          <span style="display:flex; align-items:center; gap:0.4rem;"><span style="width:12px; height:12px; background:#D99A2B; border-radius:2px;"></span> Selected Zone</span>
          <span style="display:flex; align-items:center; gap:0.4rem;"><span style="width:12px; height:12px; background:#B83A2C; border-radius:2px;"></span> Reported Damage</span>
        </div>
      </div>
    `;

    container.querySelectorAll('.rf-car-part').forEach(part => {
      part.addEventListener('click', (e) => {
        container.querySelectorAll('.rf-car-part').forEach(p => p.classList.remove('selected'));
        e.target.classList.add('selected');
        if (onPartClick) onPartClick(e.target.id);
      });
    });
  }
}

window.InspectionCanvas = InspectionCanvas;
