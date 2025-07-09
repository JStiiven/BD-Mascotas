package controlador;
import dao.PetDao;
import dao.PersonDao;
import procesos.PetProcess;
import procesos.PersonProcess;
import ventanas.VentanaMascotas;
import ventanas.VentanaPersonas;
import ventanas.VentanaPrincipal;

public class Relaciones {
    private Coordinador coordinator;
    private PetDao petDao;
    private PersonDao personDao;
    private PetProcess petProcess;
    private PersonProcess personProcess;
    private VentanaMascotas petWindow;
    private VentanaPersonas personWindow;
    private VentanaPrincipal mainWindow;

    public void start() {
        coordinator = new Coordinador();
        petDao = new PetDao();
        personDao = new PersonDao();
        petProcess = new PetProcess();
        personProcess = new PersonProcess();
        petWindow = new VentanaMascotas();
        personWindow = new VentanaPersonas();
        mainWindow = new VentanaPrincipal();

        // Link to coordinator
        coordinator.setPetDao(petDao);
        coordinator.setPersonDao(personDao);
        coordinator.setPetProcess(petProcess);
        coordinator.setPersonProcess(personProcess);
        coordinator.setPetWindow(petWindow);
        coordinator.setPersonWindow(personWindow);
        coordinator.setMainWindow(mainWindow);

        petWindow.setCoordinador(coordinator);
        personWindow.setCoordinador(coordinator);
        mainWindow.setCoordinador(coordinator);

        mainWindow.setVisible(true);


    }

    public Coordinador getCoordinator() {
        return coordinator;
    }
}
