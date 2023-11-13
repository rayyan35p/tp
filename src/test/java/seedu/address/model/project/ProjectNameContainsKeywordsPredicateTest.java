package seedu.address.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ProjectBuilder;

public class ProjectNameContainsKeywordsPredicateTest {

    /*
    Test case design used: Equivalence Partition

    EPs for predicate:
    1. Project name contains at least one of the keywords
    2. Project name contains none of the keywords

    Note: Here, the number of keywords could be any non-negative integer.
    */

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ProjectNameContainsKeywordsPredicate firstPredicate =
                new ProjectNameContainsKeywordsPredicate(firstPredicateKeywordList);
        ProjectNameContainsKeywordsPredicate secondPredicate =
                new ProjectNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        ProjectNameContainsKeywordsPredicate firstPredicateCopy =
                new ProjectNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different employee -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    // EP used: EP 1
    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        ProjectNameContainsKeywordsPredicate predicate =
                new ProjectNameContainsKeywordsPredicate(Collections.singletonList("Website"));
        assertTrue(predicate.test(new ProjectBuilder().withName("Create Website").build()));

        // Multiple keywords
        predicate = new ProjectNameContainsKeywordsPredicate(Arrays.asList("Create", "Website"));
        assertTrue(predicate.test(new ProjectBuilder().withName("Create Website").build()));

        // Only one matching keyword
        predicate = new ProjectNameContainsKeywordsPredicate(Arrays.asList("Build", "Website"));
        assertTrue(predicate.test(new ProjectBuilder().withName("Create Website").build()));

        // Mixed-case keywords
        predicate = new ProjectNameContainsKeywordsPredicate(Arrays.asList("creATe", "WebsItE"));
        assertTrue(predicate.test(new ProjectBuilder().withName("Create Website").build()));
    }

    // EP used: EP 2
    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ProjectNameContainsKeywordsPredicate predicate =
                new ProjectNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ProjectBuilder().withName("Create Website").build()));

        // Non-matching keyword
        predicate = new ProjectNameContainsKeywordsPredicate(Arrays.asList("Build"));
        assertFalse(predicate.test(new ProjectBuilder().withName("Create Website").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ProjectNameContainsKeywordsPredicate predicate = new ProjectNameContainsKeywordsPredicate(keywords);

        String expected = ProjectNameContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
