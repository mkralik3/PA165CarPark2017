package cz.muni.fi.pa165.dto;

import java.util.ArrayList;
import java.util.List;

public class RegionalBranchDTO {

    private Long id;

    private String name;

    private RegionalBranchDTO parent;

    private List<RegionalBranchDTO> children = new ArrayList<>();

    private List<UserDTO> employees = new ArrayList<>();

    private List<CarDTO> cars = new ArrayList<>();

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

    public RegionalBranchDTO getParent() {
        return parent;
    }

    public void setParent(RegionalBranchDTO parent) {
        this.parent = parent;
    }

    public List<RegionalBranchDTO> getChildren() {
        return children;
    }

    public void addChild(RegionalBranchDTO children) {
        this.children.add(children);
    }

    public List<UserDTO> getEmployees() {
        return employees;
    }

    public void addEmployee(UserDTO employee) {
        this.employees.add(employee);
    }

    public List<CarDTO> getCars() {
        return cars;
    }

    public void addCar(CarDTO car) {
        this.cars.add(car);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegionalBranchDTO)) return false;

        RegionalBranchDTO that = (RegionalBranchDTO) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
