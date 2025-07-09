package procesos;

public abstract class ProcesosGenerales {

    public abstract void register(Object object);

    public abstract void update(Object obj);

    public abstract void delete(String document);

    public abstract Object query(String id);

    public abstract String list();
}
