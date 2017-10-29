package cz.muni.fi.pa165.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

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
    private List<RegionalBranch> children = new ArrayList<>();

    @OneToMany
    private List<User> employees = new ArrayList<>();

    @OneToMany
    private List<Car> cars = new ArrayList<>();

    @NotNull
    @Column(nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

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
        if (parent == this)
            throw new IllegalArgumentException("You cannot set this it as it's own Parent!");
        if (this.parent == parent)
            throw new IllegalArgumentException("This parent is already set!");
        this.parent = parent;
    }

    public List<RegionalBranch> getChildren() {
        return children;
    }

    public void addChild(RegionalBranch children) {
        if (!this.children.contains(children))
            throw new IllegalArgumentException("Already conrains this branch!");
        if (children == null)
            throw new IllegalArgumentException("You cannot add null branch!");
        this.children.add(children);
    }

    public List<User> getEmployees() {
        return employees;
    }

    public void addEmployee(User employee) {
        if (this.employees.contains(employee))
            throw new IllegalArgumentException("This branch already contains this employee");
        if (employee == null)
            throw new IllegalArgumentException("You cannot add null employee");
        this.employees.add(employee);
    }

    public List<Car> getCars() {
        return cars;
    }

    public void addCar(Car car) {
        if (this.cars.contains(car))
            throw new IllegalArgumentException("This branch already contains this car!");
        if (car == null)
            throw new IllegalArgumentException("You cannot add null car");
        this.cars.add(car);
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
