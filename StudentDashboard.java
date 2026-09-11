import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentDashboard extends JFrame {
    private List<Student> students = new ArrayList<>();
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    
    // Detail panels
    private JLabel lblId, lblName, lblRoll, lblClass, lblEmail, lblMaterialCount;
    private DefaultListModel<String> materialListModel;
    private JList<String> materialList;
    private JTextArea materialContentArea;

    public StudentDashboard() {
        setTitle("Student Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        
        // Sample data
        loadSampleData();

        initComponents();
    }

    private void loadSampleData() {
        Student s1 = new Student("Alice Johnson", "R101", "10-A", "alice@school.edu");
        s1.addMaterial(new StudentMaterial("Math Notes Ch 1", "Mathematics", "Algebra basics and linear equations..."));
        s1.addMaterial(new StudentMaterial("Science Lab Manual", "Science", "Experiment 1: Plant cell structure..."));
        
        Student s2 = new Student("Bob Smith", "R102", "10-B", "bob@school.edu");
        s2.addMaterial(new StudentMaterial("English Essay Guide", "English", "How to write argumentative essays..."));
        
        Student s3 = new Student("Carol Davis", "R103", "10-A", "carol@school.edu");
        s3.addMaterial(new StudentMaterial("History Timeline", "History", "World War II key events..."));
        s3.addMaterial(new StudentMaterial("Geography Maps", "Geography", "Continents and oceans..."));
        s3.addMaterial(new StudentMaterial("Math Formulas", "Mathematics", "Trigonometry identities..."));

        students.add(s1);
        students.add(s2);
        students.add(s3);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // ===== TOP: Title + Search =====
        JPanel topPanel = new JPanel(new BorderLayout(10, 0));
        topPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        topPanel.setBackground(new Color(37, 99, 235));
        
        JLabel title = new JLabel("📚 Student Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        
        searchField = new JTextField(20);
        searchField.setToolTipText("Search by name, roll number, or class...");
        JButton btnSearch = new JButton("🔍 Search");
        JButton btnClear = new JButton("Clear");
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);
        searchPanel.add(new JLabel("Search:") {{ setForeground(Color.WHITE); setFont(new Font("Segoe UI", Font.BOLD, 13)); }});
        searchPanel.add(searchField);
        searchPanel.add(btnSearch);
        searchPanel.add(btnClear);
        
        topPanel.add(title, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // ===== CENTER: Split Pane =====
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(450);
        splitPane.setBorder(null);

        // -- LEFT: Student Table --
        String[] columns = {"ID", "Name", "Roll No", "Class", "Materials"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(32);
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        studentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        studentTable.getTableHeader().setBackground(new Color(243, 244, 246));
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        refreshTable(students);
        
        JScrollPane tableScroll = new JScrollPane(studentTable);
        tableScroll.setBorder(new EmptyBorder(10, 10, 10, 5));
        
        // Left button panel
        JPanel leftBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        leftBtnPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
        JButton btnAddStudent = new JButton("➕ Add Student");
        JButton btnDeleteStudent = new JButton("🗑 Delete");
        JButton btnRefresh = new JButton("🔄 Refresh");
        leftBtnPanel.add(btnAddStudent);
        leftBtnPanel.add(btnDeleteStudent);
        leftBtnPanel.add(btnRefresh);
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(tableScroll, BorderLayout.CENTER);
        leftPanel.add(leftBtnPanel, BorderLayout.SOUTH);
        splitPane.setLeftComponent(leftPanel);

        // -- RIGHT: Student Details & Materials --
        JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
        rightPanel.setBorder(new EmptyBorder(10, 5, 10, 15));
        rightPanel.setBackground(Color.WHITE);

        // Details Card
        JPanel detailsCard = new JPanel(new GridLayout(6, 1, 5, 8));
        detailsCard.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219)), 
            " Student Details ", TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14), new Color(31, 41, 55)
        ));
        detailsCard.setBackground(Color.WHITE);
        detailsCard.setPreferredSize(new Dimension(0, 180));
        
        lblId = createDetailLabel("ID: ");
        lblName = createDetailLabel("Name: ");
        lblRoll = createDetailLabel("Roll No: ");
        lblClass = createDetailLabel("Class: ");
        lblEmail = createDetailLabel("Email: ");
        lblMaterialCount = createDetailLabel("Total Materials: ");
        
        detailsCard.add(lblId);
        detailsCard.add(lblName);
        detailsCard.add(lblRoll);
        detailsCard.add(lblClass);
        detailsCard.add(lblEmail);
        detailsCard.add(lblMaterialCount);

        // Materials Section
        JPanel materialsPanel = new JPanel(new BorderLayout(0, 5));
        materialsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219)),
            " Study Materials ", TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14), new Color(31, 41, 55)
        ));
        materialsPanel.setBackground(Color.WHITE);

        materialListModel = new DefaultListModel<>();
        materialList = new JList<>(materialListModel);
        materialList.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        materialList.setFixedCellHeight(28);
        
        materialContentArea = new JTextArea(5, 20);
        materialContentArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        materialContentArea.setLineWrap(true);
        materialContentArea.setWrapStyleWord(true);
        materialContentArea.setEditable(false);
        materialContentArea.setBackground(new Color(249, 250, 251));
        materialContentArea.setBorder(new EmptyBorder(8, 8, 8, 8));
        
        JSplitPane materialSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            new JScrollPane(materialList), new JScrollPane(materialContentArea));
        materialSplit.setDividerLocation(150);
        materialSplit.setBorder(null);
        
        JPanel matBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        matBtnPanel.setOpaque(false);
        JButton btnAddMaterial = new JButton("➕ Add Material");
        matBtnPanel.add(btnAddMaterial);
        
        materialsPanel.add(materialSplit, BorderLayout.CENTER);
        materialsPanel.add(matBtnPanel, BorderLayout.SOUTH);

        rightPanel.add(detailsCard, BorderLayout.NORTH);
        rightPanel.add(materialsPanel, BorderLayout.CENTER);
        splitPane.setRightComponent(rightPanel);

        add(splitPane, BorderLayout.CENTER);

        // ===== EVENTS =====
        
        // Table selection
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = studentTable.getSelectedRow();
                if (row >= 0) {
                    int studentId = (int) tableModel.getValueAt(row, 0);
                    Student s = findStudentById(studentId);
                    if (s != null) showStudentDetails(s);
                }
            }
        });

        // Search
        btnSearch.addActionListener(e -> performSearch());
        searchField.addActionListener(e -> performSearch());
        btnClear.addActionListener(e -> {
            searchField.setText("");
            refreshTable(students);
            clearDetails();
        });

        // Add Student
        btnAddStudent.addActionListener(e -> showAddStudentDialog());

        // Delete Student
        btnDeleteStudent.addActionListener(e -> {
            int row = studentTable.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Select a student first.");
                return;
            }
            int id = (int) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Delete student ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                students.removeIf(s -> s.getId() == id);
                refreshTable(students);
                clearDetails();
            }
        });

        // Refresh
        btnRefresh.addActionListener(e -> {
            refreshTable(students);
            clearDetails();
        });

        // Add Material
        btnAddMaterial.addActionListener(e -> {
            int row = studentTable.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Select a student first.");
                return;
            }
            int id = (int) tableModel.getValueAt(row, 0);
            Student s = findStudentById(id);
            showAddMaterialDialog(s);
        });

        // Material selection shows content
        materialList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int idx = materialList.getSelectedIndex();
                int row = studentTable.getSelectedRow();
                if (row >= 0 && idx >= 0) {
                    int id = (int) tableModel.getValueAt(row, 0);
                    Student s = findStudentById(id);
                    if (s != null && idx < s.getMaterials().size()) {
                        materialContentArea.setText(
                            "Title: " + s.getMaterials().get(idx).getTitle() + "\n" +
                            "Subject: " + s.getMaterials().get(idx).getSubject() + "\n" +
                            "Date: " + s.getMaterials().get(idx).getDateAdded() + "\n\n" +
                            s.getMaterials().get(idx).getContent()
                        );
                    }
                }
            }
        });
    }

    private JLabel createDetailLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setBorder(new EmptyBorder(0, 10, 0, 0));
        return lbl;
    }

    private void refreshTable(List<Student> list) {
        tableModel.setRowCount(0);
        for (Student s : list) {
            tableModel.addRow(new Object[]{
                s.getId(), s.getName(), s.getRollNumber(), 
                s.getStudentClass(), s.getMaterialCount()
            });
        }
    }

    private Student findStudentById(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    private void showStudentDetails(Student s) {
        lblId.setText("ID: " + s.getId());
        lblName.setText("Name: " + s.getName());
        lblRoll.setText("Roll No: " + s.getRollNumber());
        lblClass.setText("Class: " + s.getStudentClass());
        lblEmail.setText("Email: " + s.getEmail());
        lblMaterialCount.setText("Total Materials: " + s.getMaterialCount());

        materialListModel.clear();
        materialContentArea.setText("");
        for (StudentMaterial m : s.getMaterials()) {
            materialListModel.addElement(m.toString());
        }
    }

    private void clearDetails() {
        lblId.setText("ID: ");
        lblName.setText("Name: ");
        lblRoll.setText("Roll No: ");
        lblClass.setText("Class: ");
        lblEmail.setText("Email: ");
        lblMaterialCount.setText("Total Materials: ");
        materialListModel.clear();
        materialContentArea.setText("");
    }

    private void performSearch() {
        String query = searchField.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            refreshTable(students);
            return;
        }
        List<Student> filtered = students.stream()
            .filter(s -> s.getName().toLowerCase().contains(query) ||
                         s.getRollNumber().toLowerCase().contains(query) ||
                         s.getStudentClass().toLowerCase().contains(query))
            .collect(Collectors.toList());
        refreshTable(filtered);
    }

    private void showAddStudentDialog() {
        JTextField nameField = new JTextField();
        JTextField rollField = new JTextField();
        JTextField classField = new JTextField();
        JTextField emailField = new JTextField();

        Object[] message = {
            "Name:", nameField,
            "Roll Number:", rollField,
            "Class:", classField,
            "Email:", emailField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Student", 
            JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (nameField.getText().trim().isEmpty() || rollField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Roll Number are required.");
                return;
            }
            Student s = new Student(
                nameField.getText().trim(),
                rollField.getText().trim(),
                classField.getText().trim(),
                emailField.getText().trim()
            );
            students.add(s);
            refreshTable(students);
        }
    }

    private void showAddMaterialDialog(Student student) {
        JTextField titleField = new JTextField();
        JTextField subjectField = new JTextField();
        JTextArea contentArea = new JTextArea(5, 20);
        contentArea.setLineWrap(true);

        Object[] message = {
            "Title:", titleField,
            "Subject:", subjectField,
            "Content:", new JScrollPane(contentArea)
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Material for " + student.getName(), 
            JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (titleField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title is required.");
                return;
            }
            student.addMaterial(new StudentMaterial(
                titleField.getText().trim(),
                subjectField.getText().trim(),
                contentArea.getText().trim()
            ));
            showStudentDetails(student);
            refreshTable(students); // update material count
        }
    }

    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new StudentDashboard().setVisible(true));
    }
}