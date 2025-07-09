package vo;
public class PersonVo {

    private String document;
    private String name;
    private String phone;

    public PersonVo(String document, String name, String phone) {
        this.document = document;
        this.name = name;
        this.phone = phone;
    }

    public PersonVo() {

    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String showInfo(){
        return "Document: " + document + "\nName: " + name + "\nPhone: " + phone;
    }
} 