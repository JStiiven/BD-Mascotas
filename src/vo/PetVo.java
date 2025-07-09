package vo;

public class PetVo extends Animal{


    private String name;
    private String ownerId;

    public String getName() {
        return name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public PetVo() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String showInfo(){
        return "Owner Document: " + ownerId + "\nName: " + name + "\nGender: " + getGender() + "\nBreed: " + getBreed() ;
    }


} 