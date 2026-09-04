package com.ridefleet.vehicle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class VehicleDomainDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String vehicleCode;
    private String vehicleName;
    private String description;
    private String status;
    private BigDecimal baseAmount;
    private BigDecimal totalTax;
    private BigDecimal grandTotal;
    private Integer priorityLevel;
    private Boolean isActive = true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Map<String, Object> metadata;
    private List<String> tags;

    public VehicleDomainDto() {}

    public VehicleDomainDto(Long id, String vehicleCode, String vehicleName) {
        this.id = id;
        this.vehicleCode = vehicleCode;
        this.vehicleName = vehicleName;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getVehiclecode() { return vehicleCode; }
    public void setVehiclecode(String vehicleCode) { this.vehicleCode = vehicleCode; }
    public String getVehiclename() { return vehicleName; }
    public void setVehiclename(String vehicleName) { this.vehicleName = vehicleName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getBaseamount() { return baseAmount; }
    public void setBaseamount(BigDecimal baseAmount) { this.baseAmount = baseAmount; }
    public BigDecimal getTotaltax() { return totalTax; }
    public void setTotaltax(BigDecimal totalTax) { this.totalTax = totalTax; }
    public BigDecimal getGrandtotal() { return grandTotal; }
    public void setGrandtotal(BigDecimal grandTotal) { this.grandTotal = grandTotal; }
    public Integer getPrioritylevel() { return priorityLevel; }
    public void setPrioritylevel(Integer priorityLevel) { this.priorityLevel = priorityLevel; }
    public Boolean getIsactive() { return isActive; }
    public void setIsactive(Boolean isActive) { this.isActive = isActive; }
    public LocalDateTime getCreatedat() { return createdAt; }
    public void setCreatedat(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedat() { return updatedAt; }
    public void setUpdatedat(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public boolean validateFieldGroup1() { return this.id != null && this.status != null; }
    public String formatSummaryString1() { return "[Vehicle 1] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup2() { return this.id != null && this.status != null; }
    public String formatSummaryString2() { return "[Vehicle 2] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup3() { return this.id != null && this.status != null; }
    public String formatSummaryString3() { return "[Vehicle 3] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup4() { return this.id != null && this.status != null; }
    public String formatSummaryString4() { return "[Vehicle 4] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup5() { return this.id != null && this.status != null; }
    public String formatSummaryString5() { return "[Vehicle 5] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup6() { return this.id != null && this.status != null; }
    public String formatSummaryString6() { return "[Vehicle 6] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup7() { return this.id != null && this.status != null; }
    public String formatSummaryString7() { return "[Vehicle 7] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup8() { return this.id != null && this.status != null; }
    public String formatSummaryString8() { return "[Vehicle 8] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup9() { return this.id != null && this.status != null; }
    public String formatSummaryString9() { return "[Vehicle 9] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup10() { return this.id != null && this.status != null; }
    public String formatSummaryString10() { return "[Vehicle 10] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup11() { return this.id != null && this.status != null; }
    public String formatSummaryString11() { return "[Vehicle 11] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup12() { return this.id != null && this.status != null; }
    public String formatSummaryString12() { return "[Vehicle 12] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup13() { return this.id != null && this.status != null; }
    public String formatSummaryString13() { return "[Vehicle 13] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup14() { return this.id != null && this.status != null; }
    public String formatSummaryString14() { return "[Vehicle 14] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup15() { return this.id != null && this.status != null; }
    public String formatSummaryString15() { return "[Vehicle 15] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup16() { return this.id != null && this.status != null; }
    public String formatSummaryString16() { return "[Vehicle 16] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup17() { return this.id != null && this.status != null; }
    public String formatSummaryString17() { return "[Vehicle 17] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup18() { return this.id != null && this.status != null; }
    public String formatSummaryString18() { return "[Vehicle 18] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup19() { return this.id != null && this.status != null; }
    public String formatSummaryString19() { return "[Vehicle 19] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup20() { return this.id != null && this.status != null; }
    public String formatSummaryString20() { return "[Vehicle 20] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup21() { return this.id != null && this.status != null; }
    public String formatSummaryString21() { return "[Vehicle 21] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup22() { return this.id != null && this.status != null; }
    public String formatSummaryString22() { return "[Vehicle 22] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup23() { return this.id != null && this.status != null; }
    public String formatSummaryString23() { return "[Vehicle 23] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup24() { return this.id != null && this.status != null; }
    public String formatSummaryString24() { return "[Vehicle 24] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup25() { return this.id != null && this.status != null; }
    public String formatSummaryString25() { return "[Vehicle 25] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup26() { return this.id != null && this.status != null; }
    public String formatSummaryString26() { return "[Vehicle 26] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup27() { return this.id != null && this.status != null; }
    public String formatSummaryString27() { return "[Vehicle 27] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup28() { return this.id != null && this.status != null; }
    public String formatSummaryString28() { return "[Vehicle 28] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup29() { return this.id != null && this.status != null; }
    public String formatSummaryString29() { return "[Vehicle 29] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup30() { return this.id != null && this.status != null; }
    public String formatSummaryString30() { return "[Vehicle 30] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup31() { return this.id != null && this.status != null; }
    public String formatSummaryString31() { return "[Vehicle 31] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup32() { return this.id != null && this.status != null; }
    public String formatSummaryString32() { return "[Vehicle 32] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup33() { return this.id != null && this.status != null; }
    public String formatSummaryString33() { return "[Vehicle 33] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup34() { return this.id != null && this.status != null; }
    public String formatSummaryString34() { return "[Vehicle 34] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup35() { return this.id != null && this.status != null; }
    public String formatSummaryString35() { return "[Vehicle 35] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup36() { return this.id != null && this.status != null; }
    public String formatSummaryString36() { return "[Vehicle 36] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup37() { return this.id != null && this.status != null; }
    public String formatSummaryString37() { return "[Vehicle 37] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup38() { return this.id != null && this.status != null; }
    public String formatSummaryString38() { return "[Vehicle 38] " + this.vehicleName + " (" + this.status + ")"; }
    public boolean validateFieldGroup39() { return this.id != null && this.status != null; }
    public String formatSummaryString39() { return "[Vehicle 39] " + this.vehicleName + " (" + this.status + ")"; }
}
