
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class TaskListClient {
    public static void main(String[] args) {
        try {
            // Locate RMI registry on localhost and port 1099 (Récupération du registre RMI)
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            // Look up the RemoteTaskList object from the registry
            RemoteTaskList taskList = (RemoteTaskList) registry.lookup("TaskListServer");

            // Example of list usage
            taskList.addTask("Do the groceries");
            taskList.addTask("Study");
            taskList.addTask("Workout");
            taskList.addTask("Go shopping");

            taskList.removeTask("Go shopping");
            // Getting all tasks
            List<String> allTasks = taskList.getAllTasks();
            System.out.println("TO DO LIST : ");
            for (String task : allTasks) {
                System.out.println(task);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

