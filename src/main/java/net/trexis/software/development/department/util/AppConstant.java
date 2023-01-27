package net.trexis.software.development.department.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class AppConstant {

    //JsonProperties
    public static final String PROP_DETAIL_EMPLOYEES = "employees";

    //Role
    public static final String ROLE_DEVELOPER = "Developer";

    //Response string templates
    public static final String MANAGER_COST_TEMPLATE = "%s's cost is %s units";
    public static final String DEPARTMENT_COST_TEMPLATE = "The Department cost is %s units";
    public static final String LITTLE_RESPONSIBILITY_TEMPLATE = "%s has too little responsibility";
    public static final String NO_LITTLE_RESPONSIBILITY_TEMPLATE =
            "There's any managers with too little responsibility";

    public static final String MANAGER_UNDERSTAFFED_TEMPLATE = "%s have understaffed Development groups";

    //Miscellaneous
    public static final String STRING_JOIN_DELIMITER = ", ";

    //Logger
    public static final String ERROR_GETTING_DATA_MSG = "Error while getting data, message: {}";
    public static final String ERROR_SAVING_DATA_MSG = "Error while saving data, message: {}";
}
