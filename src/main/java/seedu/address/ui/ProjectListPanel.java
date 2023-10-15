package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.employee.Employee;

/**
 * Panel containing the list of projects.
 */
public class ProjectListPanel extends UiPart<Region> {

    private static final String FXML = "ProjectListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ProjectListPanel.class);

    @FXML
    private ListView<Employee> projectListView;

    /**
     * Creates a {@code EmployeeListPanel} with the given {@code ObservableList}.
     */
    public ProjectListPanel(ObservableList<Employee> employeeList) {
        super(FXML);
        projectListView.setItems(employeeList);
        projectListView.setCellFactory(listView -> new ProjectListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Project} using a {@code ProjectCard}.
     */
    class ProjectListViewCell extends ListCell<Employee> {
        @Override
        protected void updateItem(Employee employee, boolean empty) {
            super.updateItem(employee, empty);

            if (empty || employee == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ProjectCard(employee, getIndex() + 1).getRoot());
            }
        }
    }

}
