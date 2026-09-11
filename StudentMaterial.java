import java.time.LocalDate;

public class StudentMaterial {
    private String title;
    private String subject;
    private String content;
    private LocalDate dateAdded;

    public StudentMaterial(String title, String subject, String content) {
        this.title = title;
        this.subject = subject;
        this.content = content;
        this.dateAdded = LocalDate.now();
    }

    public String getTitle() { return title; }
    public String getSubject() { return subject; }
    public String getContent() { return content; }
    public LocalDate getDateAdded() { return dateAdded; }

    @Override
    public String toString() {
        return title + " (" + subject + ") - " + dateAdded;
    }
}
