package procesos;

import vo.PetVo;
import java.util.HashMap;

public class PetProcess extends ProcesosGenerales {
    private HashMap<String, PetVo> pets = new HashMap<>();

    @Override
    public void register(Object obj) {
        PetVo pet = (PetVo) obj;
        pets.put(pet.getName(), pet);
    }

    @Override
    public PetVo query(String name) {
        return pets.get(name);
    }

    @Override
    public void update(Object obj) {
        PetVo pet = (PetVo) obj;
        pets.put(pet.getName(), pet);
    }

    @Override
    public void delete(String name) {
        pets.remove(name);
    }

    @Override
    public String list() {
        StringBuilder sb = new StringBuilder();
        for (PetVo p : pets.values()) {
            sb.append("Name: ").append(p.getName())
                    .append("\nOwner: ").append(p.getOwnerId())
                    .append("\nBreed: ").append(p.getBreed())
                    .append("\nGender: ").append(p.getGender())
                    .append("\n\n");
        }
        return sb.toString();
    }
} 