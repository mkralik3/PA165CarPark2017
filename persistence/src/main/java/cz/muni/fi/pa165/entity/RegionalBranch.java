package cz.muni.fi.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
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
    private Set<RegionalBranch> children = new HashSet<>();

    @OneToMany
    private Set<User> employees = new HashSet<>();

    @OneToMany
    private Set<Car> cars = new HashSet<>();

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
        return Collections.unmodifiableSet(children);
    }

    public void addChild(RegionalBranch children) { this.children.add(children);}

    public Set<User> getEmployees() {
        return Collections.unmodifiableSet(employees);
    }

    public void addEmployee(User employee) {
        this.employees.add(employee);
    }

    public Set<Car> getCars() {
        return Collections.unmodifiableSet(cars);
    }

    public void setCar(Car car) { this.cars.add(car); }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof RegionalBranch)) return false;

        RegionalBranch that = (RegionalBranch) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        return getCreationDate() != null ? getCreationDate().equals(that.getCreationDate()) : that.getCreationDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getCreationDate() != null ? getCreationDate().hashCode() : 0);
        return result;
    }
}
