# Student-Dashboard
# 📚 Student Dashboard

A simple **Java Swing** desktop application to manage students and their study materials.  
Search students, view details, and add learning materials through a clean GUI.

---

## Features

-  **Add Students** — register with name, roll number, class, and email
- **Delete Students** — remove a student with a confirmation dialog
-  **Search** — filter students by name, roll number, or class (press Enter)
-  **Study Materials** — add materials (title, subject, content) to any student
-  **Material Counter** — each student's material count shown in the table
-  **Refresh** — reset table and clear the details view
-  **Details Panel** — view selected student's full info and material content
-  **Modern UI** — clean split-pane layout with styled headers and cards


## 🗂 Project Structure

| File | Description |
| `StudentDashboard.java` | Main JFrame — GUI, event handling, search, dialogs |
| `Student.java` | Student model — ID (auto-generated), name, roll no, class, email, materials list |
| `StudentMaterial.java` | Material model — title, subject, content, date added |

>  The project references a `StudentMaterial` class (used by `Student.addMaterial()` and the dashboard). Make sure `StudentMaterial.java` exists in the same folder.

---

## How to Run

### 1. Compile
```bash
javac StudentDashboard.java Student.java StudentMaterial.java

