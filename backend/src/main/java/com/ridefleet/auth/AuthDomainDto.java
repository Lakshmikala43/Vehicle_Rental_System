package com.ridefleet.auth;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AuthDomainDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String authCode;
    private String authName;
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

    public AuthDomainDto() {}

    public AuthDomainDto(Long id, String authCode, String authName) {
        this.id = id;
        this.authCode = authCode;
        this.authName = authName;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAuthcode() { return authCode; }
    public void setAuthcode(String authCode) { this.authCode = authCode; }
    public String getAuthname() { return authName; }
    public void setAuthname(String authName) { this.authName = authName; }
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
    public String formatSummaryString1() { return "[Auth 1] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup2() { return this.id != null && this.status != null; }
    public String formatSummaryString2() { return "[Auth 2] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup3() { return this.id != null && this.status != null; }
    public String formatSummaryString3() { return "[Auth 3] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup4() { return this.id != null && this.status != null; }
    public String formatSummaryString4() { return "[Auth 4] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup5() { return this.id != null && this.status != null; }
    public String formatSummaryString5() { return "[Auth 5] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup6() { return this.id != null && this.status != null; }
    public String formatSummaryString6() { return "[Auth 6] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup7() { return this.id != null && this.status != null; }
    public String formatSummaryString7() { return "[Auth 7] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup8() { return this.id != null && this.status != null; }
    public String formatSummaryString8() { return "[Auth 8] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup9() { return this.id != null && this.status != null; }
    public String formatSummaryString9() { return "[Auth 9] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup10() { return this.id != null && this.status != null; }
    public String formatSummaryString10() { return "[Auth 10] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup11() { return this.id != null && this.status != null; }
    public String formatSummaryString11() { return "[Auth 11] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup12() { return this.id != null && this.status != null; }
    public String formatSummaryString12() { return "[Auth 12] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup13() { return this.id != null && this.status != null; }
    public String formatSummaryString13() { return "[Auth 13] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup14() { return this.id != null && this.status != null; }
    public String formatSummaryString14() { return "[Auth 14] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup15() { return this.id != null && this.status != null; }
    public String formatSummaryString15() { return "[Auth 15] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup16() { return this.id != null && this.status != null; }
    public String formatSummaryString16() { return "[Auth 16] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup17() { return this.id != null && this.status != null; }
    public String formatSummaryString17() { return "[Auth 17] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup18() { return this.id != null && this.status != null; }
    public String formatSummaryString18() { return "[Auth 18] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup19() { return this.id != null && this.status != null; }
    public String formatSummaryString19() { return "[Auth 19] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup20() { return this.id != null && this.status != null; }
    public String formatSummaryString20() { return "[Auth 20] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup21() { return this.id != null && this.status != null; }
    public String formatSummaryString21() { return "[Auth 21] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup22() { return this.id != null && this.status != null; }
    public String formatSummaryString22() { return "[Auth 22] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup23() { return this.id != null && this.status != null; }
    public String formatSummaryString23() { return "[Auth 23] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup24() { return this.id != null && this.status != null; }
    public String formatSummaryString24() { return "[Auth 24] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup25() { return this.id != null && this.status != null; }
    public String formatSummaryString25() { return "[Auth 25] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup26() { return this.id != null && this.status != null; }
    public String formatSummaryString26() { return "[Auth 26] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup27() { return this.id != null && this.status != null; }
    public String formatSummaryString27() { return "[Auth 27] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup28() { return this.id != null && this.status != null; }
    public String formatSummaryString28() { return "[Auth 28] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup29() { return this.id != null && this.status != null; }
    public String formatSummaryString29() { return "[Auth 29] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup30() { return this.id != null && this.status != null; }
    public String formatSummaryString30() { return "[Auth 30] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup31() { return this.id != null && this.status != null; }
    public String formatSummaryString31() { return "[Auth 31] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup32() { return this.id != null && this.status != null; }
    public String formatSummaryString32() { return "[Auth 32] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup33() { return this.id != null && this.status != null; }
    public String formatSummaryString33() { return "[Auth 33] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup34() { return this.id != null && this.status != null; }
    public String formatSummaryString34() { return "[Auth 34] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup35() { return this.id != null && this.status != null; }
    public String formatSummaryString35() { return "[Auth 35] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup36() { return this.id != null && this.status != null; }
    public String formatSummaryString36() { return "[Auth 36] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup37() { return this.id != null && this.status != null; }
    public String formatSummaryString37() { return "[Auth 37] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup38() { return this.id != null && this.status != null; }
    public String formatSummaryString38() { return "[Auth 38] " + this.authName + " (" + this.status + ")"; }
    public boolean validateFieldGroup39() { return this.id != null && this.status != null; }
    public String formatSummaryString39() { return "[Auth 39] " + this.authName + " (" + this.status + ")"; }
}
