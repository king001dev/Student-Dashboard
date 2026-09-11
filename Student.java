import java.util.ArrayList;
import java.util.List;

public class Student {
    private static int idCounter = 1000;
    
    private int id;
    private String name;
    private String rollNumber;
    private String studentClass;
    private String email;
    private List<StudentMaterial> materials;

    public Student(String name, String rollNumber, String studentClass, String email) {
        this.id = ++idCounter;
        this.name = name;
        this.rollNumber = rollNumber;
        this.studentClass = studentClass;
        this.email = email;
        this.materials = new ArrayList<>();
    }

    public void addMaterial(StudentMaterial material) {
        materials.add(material);
    }

    public int getMaterialCount() {
        return materials.size();
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getRollNumber() { return rollNumber; }
    public String getStudentClass() { return studentClass; }
    public String getEmail() { return email; }
    public List<StudentMaterial> getMaterials() { return materials; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    public void setStudentClass(String studentClass) { this.studentClass = studentClass; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return name + " (Roll: " + rollNumber + ")";
    }
}