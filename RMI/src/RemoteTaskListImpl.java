
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RemoteTaskListImpl extends UnicastRemoteObject implements RemoteTaskList {


    private List<String> taskList;
    // Constructor to initialize the task list
    public RemoteTaskListImpl() throws RemoteException {
        taskList = new ArrayList<>();
    }

    @Override
    // Method to add a task to the list
    public void addTask(String task) throws RemoteException {
        taskList.add(task);
    }

    @Override
    // Method to remove a task from the list
    public void removeTask(String task) throws RemoteException {
        taskList.remove(task);
    }

    @Override
    // Method to retrieve the complete list of tasks
    public List<String> getAllTasks() throws RemoteException {
        return new ArrayList<>(taskList);
    }

    public static void main(String[] args) {
        try {
            // Create an instance of the server
            RemoteTaskListImpl taskListServer = new RemoteTaskListImpl();
            // Create RMI registry on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            // Bind the server to the RMI registry with name "TaskListServer" (enregistrement)
            registry.rebind("TaskListServer", taskListServer);
            System.out.println("Task List Server ready.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
