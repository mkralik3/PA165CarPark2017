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

}
