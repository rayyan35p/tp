package seedu.address.model;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeNameContainsKeywordsPredicate;
import seedu.address.model.employee.Project;

import java.util.List;
import java.util.function.Predicate;

public class ProjectNameContainsKeywordsPredicate implements Predicate<Project> {

    private final List<String> keywords;

    public ProjectNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Project project) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(project.getNameString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProjectNameContainsKeywordsPredicate)) {
            return false;
        }

        ProjectNameContainsKeywordsPredicate otherProjectNameContainsKeywordsPredicate = (ProjectNameContainsKeywordsPredicate) other;
        return keywords.equals(otherProjectNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
