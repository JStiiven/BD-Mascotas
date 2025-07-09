package controlador;

import dao.PetDao;
import dao.PersonDao;
import procesos.PetProcess;
import procesos.PersonProcess;
import ventanas.VentanaMascotas;
import ventanas.VentanaPersonas;
import ventanas.VentanaPrincipal;
import vo.PetVo;
import vo.PersonVo;

public class Coordinador {
    private PetDao petDao;
    private PersonDao personDao;
    private PetProcess petProcess;
    private PersonProcess personProcess;
    private VentanaMascotas petWindow;
    private VentanaPersonas personWindow;
    private VentanaPrincipal mainWindow;

    public VentanaPrincipal getMainWindow() {
        return mainWindow;
    }

    public void setMainWindow(VentanaPrincipal mainWindow) {
        this.mainWindow = mainWindow;
    }

    public PetDao getPetDao() {
        return petDao;
    }

    public void setPetDao(PetDao petDao) {
        this.petDao = petDao;
    }

    public PersonDao getPersonDao() {
        return personDao;
    }

    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    public PetProcess getPetProcess() {
        return petProcess;
    }

    public void setPetProcess(PetProcess petProcess) {
        this.petProcess = petProcess;
    }

    public PersonProcess getPersonProcess() {
        return personProcess;
    }

    public void setPersonProcess(PersonProcess personProcess) {
        this.personProcess = personProcess;
    }

    public VentanaMascotas getPetWindow() {
        return petWindow;
    }

    public void setPetWindow(VentanaMascotas petWindow) {
        this.petWindow = petWindow;
    }

    public VentanaPersonas getPersonWindow() {
        return personWindow;
    }

    public void setPersonWindow(VentanaPersonas personWindow) {
        this.personWindow = personWindow;
    }


    public Coordinador() {
        petDao = new PetDao();
        personDao = new PersonDao();
    }

    public void registerPet(PetVo pet) {
        try {
            petDao.registerPet(pet);
        }catch (RuntimeException e){
            throw e;
        }

    }

    public void registerPerson(PersonVo person){
        personDao.registerPerson(person);
    }

    public void updatePerson(PersonVo person){
        personDao.update(person);
        personProcess.update(person);
    }

    public void deletePerson(String document){
        try {
            personDao.delete(document);
            personProcess.delete(document);
        }catch (RuntimeException e){
            throw e;
        }

    }

    public String listPerson(){
        return personDao.list();
    }

    public boolean personExists(String document) {
        return personDao.documentExists(document);
    }

    public  void  updatePet(PetVo pet){
        petDao.update(pet);
        petProcess.update(pet);
    }

    public void deletePet(String name){
        petDao.delete(name);
        petProcess.delete(name);
    }

    public String listPet(){
        return petDao.list();
    }

    public void showPets(){

        if (petWindow == null || !petWindow.isDisplayable()){
            petWindow = new VentanaMascotas();
            petWindow.setCoordinador(this);
            petWindow.setVisible(true);
        }else {
            petWindow.toFront();
        }

    }

    public void showPersons(){
        if (personWindow == null || !personWindow.isDisplayable()){
            personWindow = new VentanaPersonas();
            personWindow.setCoordinador(this);
            personWindow.setVisible(true);
        }else {
            personWindow.toFront();
        }

    }




}
