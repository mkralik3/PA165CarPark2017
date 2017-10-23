package cz.muni.fi.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Matej Kralik
 */
@Entity
public class RegionalBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable=false)
    private String name;

    @ManyToOne
    private RegionalBranch parent;

    @OneToMany(mappedBy = "parent")
    private Set<RegionalBranch> children;

    @OneToMany
    private Set<User> employees;

    @OneToMany
    private Set<Car> cars;

    @NotNull
    @Column(nullable=false)
    private LocalDateTime creationDate;

    private LocalDateTime modificationDate;

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

    public RegionalBranch getParent() {
        return parent;
    }

    public void setParent(RegionalBranch parent) {
        this.parent = parent;
    }

    public Set<RegionalBranch> getChildren() {
        return children;
    }

    public void setChildren(Set<RegionalBranch> children) {
        this.children = children;
    }

    public Set<User> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<User> employees) {
        this.employees = employees;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }
}
