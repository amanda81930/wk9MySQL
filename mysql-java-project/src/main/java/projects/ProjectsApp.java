package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import projects.dao.DbConnection;
import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {

  private Scanner scanner = new Scanner(System.in);
  private ProjectService projectService = new ProjectService();

  // @formatter:off
  private List<String> operations = List.of(
      "\t1) Add a project"
      );
  // @formatter:on

  public static void main(String[] args) {
    // DbConnection.getConnection(); //test line
    new ProjectsApp().processUserSelections();
  } // end of Main METHOD

  private void processUserSelections() {
    boolean done = false;
    while (!done) {
      try {
        int selection = getUserSelection();
        switch (selection) {
          case -1:
            done = exitMenu();
            break;
          case 1:
            createProject();
            break;
          default:
            System.out.println("\n" + selection + " is not a valid selection. Try again.");
        } 
      } 
      catch (Exception e) {
        System.out.println("\nError: " + e + "Try again.");
      } 
    } 
  } // end of processUserSelections Method

  private void createProject() {
    String projectName = getStringInput("Enter the project name");
    BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
    BigDecimal actualHours = getDecimalInput("Enter the actual hours");
    Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
    String notes = getStringInput("Enter the project notes");

    Project project = new Project();
    project.setProjectName(projectName);
    project.setEstimatedHours(estimatedHours);
    project.setActualHours(actualHours);
    project.setDifficulty(difficulty);
    project.setNotes(notes);
    Project dbProject = projectService.addProject(project);
    System.out.println("You have successfully created project: " + dbProject);
  } // end of createProject Method

  private BigDecimal getDecimalInput(String prompt) {
    String input = getStringInput(prompt);
    if (Objects.isNull(input)) {
    }
    try {
      return new BigDecimal(input).setScale(2);
    } catch (NumberFormatException e) {
      throw new DbException(input + " is not a valid decimal number.");
    }
  } //end of getDecimalInput Method

  private int getUserSelection() {
    printOperations();
    Integer input = getIntInput("\nEnter a menu selection");
    return Objects.isNull(input) ? -1 : input;
  } // end of getUserSelection Method

  private Integer getIntInput(String prompt) {
    String input = getStringInput(prompt);
    if (Objects.isNull(input)) {
      return null;
    }
    try {
      return Integer.valueOf(input);
    } catch (NumberFormatException e) {
      throw new DbException(input + " is not a valid number.");
    }
  } //end of getIntInput Method

  private String getStringInput(String prompt) {
    System.out.print(prompt + ": ");
    String input = scanner.nextLine();
    return input.isBlank() ? null : input.trim();
  } //end of getStringInput Method

  private void printOperations() {
    System.out.println("\nThese are the available selections. Press the Enter key to quit");
    operations.forEach(line -> System.out.println("\t " + line));
  } //end of printOperations Method

  private boolean exitMenu() {
    System.out.println("\nExiting the menu.");
    return true;
  } //end of exitMenu Method

} // end of projects CLASS
