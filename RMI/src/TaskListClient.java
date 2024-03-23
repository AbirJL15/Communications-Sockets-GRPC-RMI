
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class TaskListClient {
    public static void main(String[] args) {
        try {
            // Récupération du registre RMI sur localhost et le port 1099
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            // Recherche de l'objet distant TaskListServer dans le registre
            RemoteTaskList taskList = (RemoteTaskList) registry.lookup("TaskListServer");

            // Exemple d'utilisation :
            taskList.addTask("Faire les courses");
            taskList.addTask("Préparer le rapport");
            taskList.addTask("Faire du sport");

            taskList.removeTask("Faire les courses");

            List<String> allTasks = taskList.getAllTasks();
            System.out.println("Liste des tâches : ");
            for (String task : allTasks) {
                System.out.println(task);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

