package cz.muni.fi.pa165.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "parent", "children" })
public class RegionalBranchDTOMixin {
}
