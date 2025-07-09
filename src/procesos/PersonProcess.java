package procesos;

import vo.PersonVo;

import java.util.HashMap;

public class PersonProcess extends ProcesosGenerales{
    private HashMap<String, PersonVo> persons = new HashMap<>();


    @Override
    public void register(Object obj) {
        PersonVo person = (PersonVo) obj;
        persons.put(person.getDocument(), person);
    }

    @Override
    public void update(Object obj) {
        PersonVo person = (PersonVo) obj;
        persons.put(person.getDocument(), person);
    }

    @Override
    public void delete(String document) {
        persons.remove(document);
    }

    @Override
    public Object query(String document) {
        return persons.get(document);
    }

    @Override
    public String list() {
        StringBuilder sb = new StringBuilder();
        for (PersonVo p: persons.values()){
            sb.append(p.showInfo()).append("\n\n");
        }
        return sb.toString();
    }


} 