package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EMPLOYEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;
import static seedu.address.testutil.TypicalTasks.ALPHA_TASK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddEmployeeCommand;
import seedu.address.logic.commands.AddProjectCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.AssignProjectCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteEmployeeCommand;
import seedu.address.logic.commands.DeleteProjectCommand;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditEmployeeDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindEmployeeCommand;
import seedu.address.logic.commands.FindProjectCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListEmployeeAndProjectCommand;
import seedu.address.logic.commands.ListEmployeeCommand;
import seedu.address.logic.commands.ListProjectCommand;
import seedu.address.logic.commands.MarkProjectCommand;
import seedu.address.logic.commands.MarkTaskCommand;
import seedu.address.logic.commands.PriorityProjectCommand;
import seedu.address.logic.commands.ProjectDeadlineCommand;
import seedu.address.logic.commands.SortTaskCommand;
import seedu.address.logic.commands.UnassignProjectCommand;
import seedu.address.logic.commands.UnmarkProjectCommand;
import seedu.address.logic.commands.UnmarkTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeNameContainsKeywordsPredicate;
import seedu.address.model.project.Deadline;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectNameContainsKeywordsPredicate;
import seedu.address.model.project.ProjectPriority;
import seedu.address.testutil.EditEmployeeDescriptorBuilder;
import seedu.address.testutil.EmployeeBuilder;
import seedu.address.testutil.EmployeeUtil;

public class TaskHubParserTest {

    private final TaskHubParser parser = new TaskHubParser();

    @Test
    public void parseCommand_addEmployee() throws Exception {
        Employee employee = new EmployeeBuilder().build();
        AddEmployeeCommand command = (AddEmployeeCommand) parser
                .parseCommand(EmployeeUtil.getAddEmployeeCommand(employee));
        assertEquals(new AddEmployeeCommand(employee), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_deleteEmployee() throws Exception {
        DeleteEmployeeCommand command = (DeleteEmployeeCommand) parser.parseCommand(
                DeleteEmployeeCommand.COMMAND_WORD + " " + INDEX_FIRST_EMPLOYEE.getOneBased());
        assertEquals(new DeleteEmployeeCommand(INDEX_FIRST_EMPLOYEE), command);
    }

    @Test
    public void parseCommand_editEmployee() throws Exception {
        Employee employee = new EmployeeBuilder().build();
        EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder(employee).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_EMPLOYEE.getOneBased() + " " + EmployeeUtil.getEditEmployeeDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_EMPLOYEE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findEmployee() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindEmployeeCommand command = (FindEmployeeCommand) parser.parseCommand(
                FindEmployeeCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindEmployeeCommand(new EmployeeNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findProject() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindProjectCommand command = (FindProjectCommand) parser.parseCommand(
                FindProjectCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindProjectCommand(new ProjectNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_listEmployee() throws Exception {
        assertTrue(parser.parseCommand(ListEmployeeCommand.COMMAND_WORD) instanceof ListEmployeeCommand);
        assertTrue(parser.parseCommand(ListEmployeeCommand.COMMAND_WORD + " 3") instanceof ListEmployeeCommand);
    }

    @Test
    public void parseCommand_listProject() throws Exception {
        assertTrue(parser.parseCommand(ListProjectCommand.COMMAND_WORD) instanceof ListProjectCommand);
        assertTrue(parser.parseCommand(ListProjectCommand.COMMAND_WORD + " 3") instanceof ListProjectCommand);
    }

    @Test
    public void parseCommand_listEmployeeAndProject() throws Exception {
        assertTrue(parser.parseCommand(ListEmployeeAndProjectCommand.COMMAND_WORD)
                instanceof ListEmployeeAndProjectCommand);
        assertTrue(parser.parseCommand(ListEmployeeAndProjectCommand.COMMAND_WORD + " 3")
                instanceof ListEmployeeAndProjectCommand);
    }

    @Test
    public void parseCommand_assignEmployeeToProject() throws Exception {
        AssignProjectCommand command =
                (AssignProjectCommand) parser.parseCommand(AssignProjectCommand.COMMAND_WORD + " "
                 + PREFIX_PROJECT + INDEX_FIRST_EMPLOYEE.getOneBased() + " " + PREFIX_EMPLOYEE
                        + INDEX_FIRST_EMPLOYEE.getOneBased());
        assertEquals(new AssignProjectCommand(INDEX_FIRST_PROJECT,
                                                new ArrayList<>(Arrays.asList(INDEX_FIRST_EMPLOYEE))), command);
    }

    @Test
    public void parseCommand_unassignEmployeeFromProject() throws Exception {
        UnassignProjectCommand command =
                (UnassignProjectCommand) parser.parseCommand(UnassignProjectCommand.COMMAND_WORD + " "
                + PREFIX_PROJECT + INDEX_FIRST_PROJECT.getOneBased() + " "
                + PREFIX_EMPLOYEE + INDEX_FIRST_EMPLOYEE.getOneBased() + " "
                + INDEX_SECOND_EMPLOYEE.getOneBased());
        assertEquals(new UnassignProjectCommand(INDEX_FIRST_PROJECT,
                     new ArrayList<>(Arrays.asList(INDEX_FIRST_EMPLOYEE, INDEX_SECOND_EMPLOYEE))),
                     command);
    }

    @Test
    public void parseCommand_projectDeadline() throws Exception {
        final Deadline deadline = new Deadline("12-10-2022");
        ProjectDeadlineCommand command =
                (ProjectDeadlineCommand) parser.parseCommand(ProjectDeadlineCommand.COMMAND_WORD + " "
                 + INDEX_FIRST_PROJECT.getOneBased() + " " + PREFIX_DEADLINE + deadline.value);
        assertEquals(new ProjectDeadlineCommand(INDEX_FIRST_PROJECT, deadline), command);
    }

    @Test
    public void parseCommand_markProject() throws Exception {
        MarkProjectCommand command =
                (MarkProjectCommand) parser.parseCommand(MarkProjectCommand.COMMAND_WORD + " "
                 + INDEX_FIRST_PROJECT.getOneBased() + " " + INDEX_SECOND_PROJECT.getOneBased());
        List<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PROJECT);
        indexes.add(INDEX_SECOND_PROJECT);
        assertEquals(new MarkProjectCommand(indexes), command);
    }

    @Test
    public void parseCommand_unmarkProject() throws Exception {
        UnmarkProjectCommand command =
                (UnmarkProjectCommand) parser.parseCommand(UnmarkProjectCommand.COMMAND_WORD + " "
                 + INDEX_FIRST_PROJECT.getOneBased() + " " + INDEX_SECOND_PROJECT.getOneBased());
        List<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PROJECT);
        indexes.add(INDEX_SECOND_PROJECT);
        assertEquals(new UnmarkProjectCommand(indexes), command);
    }

    @Test
    public void parseCommand_addProject() throws Exception {
        AddProjectCommand command = (AddProjectCommand) parser.parseCommand(AddProjectCommand.COMMAND_WORD + " "
                            + PREFIX_PROJECT + "Alpha");
        AddProjectCommand expected = new AddProjectCommand(new Project("Alpha"), new ArrayList<>());
        assertEquals(expected, command);
    }
    @Test
    public void parseCommand_deleteProject() throws Exception {
        DeleteProjectCommand command = (DeleteProjectCommand) parser
                .parseCommand(DeleteProjectCommand.COMMAND_WORD + " 1");
        DeleteProjectCommand expected = new DeleteProjectCommand(ParserUtil.parseIndex("1"));
        assertEquals(expected, command);
    }

    @Test
    public void parseCommand_priorityProject() throws Exception {
        PriorityProjectCommand command = (PriorityProjectCommand) parser
                .parseCommand(PriorityProjectCommand.COMMAND_WORD + " 1" + " priority/low");
        PriorityProjectCommand expected = new PriorityProjectCommand(new ProjectPriority("low"),
                ParserUtil.parseIndex("1"));
        assertEquals(expected, command);
    }

    @Test
    public void parseCommand_sortTask() throws Exception {
        assertTrue(parser.parseCommand(SortTaskCommand.COMMAND_WORD) instanceof SortTaskCommand);
        assertTrue(parser.parseCommand(SortTaskCommand.COMMAND_WORD + " 3") instanceof SortTaskCommand);
    }

    @Test
    public void parseCommand_markTask() throws Exception {
        MarkTaskCommand command = (MarkTaskCommand) parser.parseCommand(MarkTaskCommand.COMMAND_WORD + " "
                + PREFIX_PROJECT + "1 "
                + PREFIX_TASK + "1 2 3");
        Index projectIndex = Index.fromOneBased(1);
        List<Index> taskIndexes = new ArrayList<>();
        taskIndexes.add(Index.fromOneBased(1));
        taskIndexes.add(Index.fromOneBased(2));
        taskIndexes.add(Index.fromOneBased(3));

        MarkTaskCommand expected = new MarkTaskCommand(projectIndex, taskIndexes);
        assertEquals(expected, command);
    }

    @Test
    public void parseCommand_unmarkTask() throws Exception {
        UnmarkTaskCommand command = (UnmarkTaskCommand) parser.parseCommand(UnmarkTaskCommand.COMMAND_WORD + " "
                + PREFIX_PROJECT + "1 "
                + PREFIX_TASK + "1 2 3");
        Index projectIndex = Index.fromOneBased(1);
        List<Index> taskIndexes = new ArrayList<>();
        taskIndexes.add(Index.fromOneBased(1));
        taskIndexes.add(Index.fromOneBased(2));
        taskIndexes.add(Index.fromOneBased(3));

        UnmarkTaskCommand expected = new UnmarkTaskCommand(projectIndex, taskIndexes);
        assertEquals(expected, command);
    }

    @Test
    public void parseCommand_addTask() throws Exception {
        AddTaskCommand command = (AddTaskCommand) parser.parseCommand(AddTaskCommand.COMMAND_WORD + " "
                            + PREFIX_NAME + "ALPHA_TASK "
                            + PREFIX_PROJECT + "1 "
                            + PREFIX_DEADLINE + "11-11-2023 2359");
        AddTaskCommand expected = new AddTaskCommand(ALPHA_TASK, ParserUtil.parseIndex("1"));
        assertEquals(expected, command);
    }

    @Test
    public void parseCommand_deleteTask() throws Exception {
        DeleteTaskCommand command = (DeleteTaskCommand) parser.parseCommand(DeleteTaskCommand.COMMAND_WORD + " "
                + PREFIX_PROJECT + "1 "
                + PREFIX_TASK + "1 2 3");
        Index projectIndex = Index.fromOneBased(1);
        List<Index> taskIndexes = new ArrayList<>();
        taskIndexes.add(Index.fromOneBased(1));
        taskIndexes.add(Index.fromOneBased(2));
        taskIndexes.add(Index.fromOneBased(3));
        taskIndexes.sort((a, b) -> b.getZeroBased() - a.getZeroBased());
        DeleteTaskCommand expected = new DeleteTaskCommand(projectIndex, taskIndexes);
        assertEquals(expected, command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
