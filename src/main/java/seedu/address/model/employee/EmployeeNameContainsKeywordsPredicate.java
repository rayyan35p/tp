package seedu.address.model.employee;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Employee}'s {@code Name} matches any of the keywords given.
 */
public class EmployeeNameContainsKeywordsPredicate implements Predicate<Employee> {
    private final List<String> keywords;

    public EmployeeNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Employee employee) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(employee.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmployeeNameContainsKeywordsPredicate)) {
            return false;
        }

        EmployeeNameContainsKeywordsPredicate otherEmployeeNameContainsKeywordsPredicate =
                (EmployeeNameContainsKeywordsPredicate) other;
        return keywords.equals(otherEmployeeNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
