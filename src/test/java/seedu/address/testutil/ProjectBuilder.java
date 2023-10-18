package seedu.address.testutil;

import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.BOB;

import seedu.address.model.employee.Employee;
import seedu.address.model.employee.Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectBuilder {
    public static final String DEFAULT_NAME = "Alpha";
    public static final List<Employee> DEFAULT_LIST = new ArrayList<>(Arrays.asList(ALICE));
    private String projectName;
    private List<Employee> employeeList;

    public ProjectBuilder() {
        projectName = DEFAULT_NAME;
        employeeList = DEFAULT_LIST;
    }

    public ProjectBuilder(Project toCopy) {
        projectName = toCopy.name;
        employeeList = toCopy.getEmployees();
    }

    public ProjectBuilder withName(String name) {
        this.projectName = name;
        return this;
    }

    public ProjectBuilder withEmployees(Employee... employees){
        this.employeeList = new ArrayList<>(Arrays.asList(employees));
        return this;
    }

    public Project build(){
        return new Project(projectName,employeeList);
    }
}
