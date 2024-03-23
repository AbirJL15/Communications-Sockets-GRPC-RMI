
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RemoteTaskListImpl extends UnicastRemoteObject implements RemoteTaskList {
    private List<String> taskList;

    public RemoteTaskListImpl() throws RemoteException {
        taskList = new ArrayList<>();
    }

    @Override
    public void addTask(String task) throws RemoteException {
        taskList.add(task);
    }

    @Override
    public void removeTask(String task) throws RemoteException {
        taskList.remove(task);
    }

    @Override
    public List<String> getAllTasks() throws RemoteException {
        return new ArrayList<>(taskList);
    }

    public static void main(String[] args) {
        try {
            RemoteTaskListImpl taskListServer = new RemoteTaskListImpl(); // Création d'une instance du serveur
            Registry registry = LocateRegistry.createRegistry(1099); // Création du registre RMI sur le port 1099
            registry.rebind("TaskListServer", taskListServer);// Enregistrement du serveur dans le registre RMI avec le nom "TaskListServer"
            System.out.println("Task List Server ready.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
