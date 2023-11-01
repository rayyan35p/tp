package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Address;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.Name;
import seedu.address.model.employee.Phone;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Project;
import seedu.address.model.project.Priority;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index provided was not a non-zero unsigned integer.\n"
            + "This is not valid: %1$s";
    public static final String MESSAGE_DUPLICATE_INDEX = "Duplicate indexes are not allowed. \n"
            + "Duplicate Index: %1$s";

    private static final Logger logger = LogsCenter.getLogger(ParserUtil.class);

    /**
     * Parses {@code String oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will
     * be trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            logger.warning("Expected a non-zero unsigned integer but received: " + trimmedIndex + ".");
            throw new ParseException(String.format(MESSAGE_INVALID_INDEX,
                    trimmedIndex.isEmpty() ? "<empty>" : trimmedIndex));
        }

        assert Integer.parseInt(trimmedIndex) > 0;
        assert !trimmedIndex.startsWith("+"); // Integer.parseInt("+1") returns 1, which is not what we want
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code Collection<String> oneBasedIndexes} into a {@code List<Index>} and returns it.
     * @param oneBasedIndexes
     * @throws ParseException if any of the specified indexes is invalid (not non-zero unsigned integer).
     */
    public static List<Index> parseIndexes(String oneBasedIndexes) throws ParseException {
        String[] trimmedIndexes = oneBasedIndexes.trim().split(" ");
        List<Index> indexList = new ArrayList<>();
        Set<String> uniqueStringSet = new HashSet<>();

        for (String index : trimmedIndexes) {
            if (uniqueStringSet.contains(index)) {
                logger.warning(String.format(MESSAGE_DUPLICATE_INDEX, index));
                throw new ParseException(String.format(MESSAGE_DUPLICATE_INDEX, index));
            } else {
                uniqueStringSet.add(index);
                indexList.add(parseIndex(index));
            }
        }

        assert indexList != null;
        assert indexList.size() > 0;
        return indexList;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String project} into a {@code Project}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Project} is invalid.
     */
    public static Project parseProject(String project) throws ParseException {
        requireNonNull(project);
        String trimmedProject = project.trim();
        // Fully qualified name used for clarity as Employee model has a Name class as well
        if (!seedu.address.model.project.Name.isValidName(trimmedProject)) {
            throw new ParseException(seedu.address.model.project.Name.MESSAGE_CONSTRAINTS);
        }
        return new Project(trimmedProject);
    }

    /**
     * Parses a {@code String taskName} into a {@code Task}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Task} is invalid.
     */
    public static Task parseTask(String taskName, String deadlineString) throws ParseException {
        requireNonNull(taskName, deadlineString);
        String trimmedTaskName = taskName.trim();
        if (!Task.isValidTask(trimmedTaskName)) {
            throw new ParseException(Task.MESSAGE_CONSTRAINTS);
        }
        return new Task(taskName, parseLocalDateTime(deadlineString));
    }

    /**
     * Parses a {@code String deadlineString} into a {@code LocalDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Task} is invalid.
     */
    public static LocalDateTime parseLocalDateTime(String deadlineString) throws ParseException {
        requireNonNull(deadlineString);
        String trimmedDeadlineString = deadlineString.trim();
        if (!Task.isValidDateTime(deadlineString)) {
            throw new ParseException(Task.MESSAGE_CONSTRAINTS);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
        LocalDateTime parsedDateTime = LocalDateTime.parse(trimmedDeadlineString, formatter);
        return parsedDateTime;
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a String priority into a Priority. Leading and trailing whitespaces will be trimmed.
     * All characters will also be set to lowercase.
     * @throws ParseException if the given priority is invalid.
     */
    public static Priority parsePriority(String priority) throws ParseException {
        requireNonNull(priority);
        String formattedPriority = priority.trim().toLowerCase();
        if (!Priority.isValidPriority(formattedPriority)) {
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }
        return new Priority(formattedPriority);
    }
    /**
     * Parses a {@code String deadline} into a {@code Deadline}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code deadline} is invalid.
     */
    public static Deadline parseDeadline(String deadline) throws ParseException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();
        if (!Deadline.isValidDeadline(trimmedDeadline)) {
            throw new ParseException(Deadline.MESSAGE_CONSTRAINTS);
        }
        return new Deadline(trimmedDeadline);
    }
}
