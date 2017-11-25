package cz.muni.fi.pa165.dto;

import java.time.LocalDateTime;

/*
@author Matej Kralik, modified by Martin Miskeje
*/
public class CarDTO {

    private Long id;
    private String name;
    private LocalDateTime creationDate;
    private LocalDateTime activationDate;
    private LocalDateTime modificationDate;
    private RegionalBranchDTO regionalBranch = null;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDateTime activationDate) {
        this.activationDate = activationDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public RegionalBranchDTO getRegionalBranch() {
        return regionalBranch;
    }

    public void setRegionalBranch(RegionalBranchDTO regionalBranch) {
        this.regionalBranch = regionalBranch;
    }

}
