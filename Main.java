import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Person> personnelList = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Welcome to my Personal Management Program\n");

        while (true) {
            printMenu();
            int selection = getSelection();
            System.out.println();
            
            switch (selection) {
                case 1:
                    addFaculty();
                    break;
                case 2:
                    addStudent();
                    break;
                case 3:
                    printTuitionInvoice();
                    break;
                case 4:
                    printFacultyInformation();
                    break;
                case 5:
                    addStaffMember();
                    break;
                case 6:
                    printStaffInformation();
                    break;
                case 7:
                    deletePerson();
                    break;
                case 8:
                    exitProgram();
                    break;
                default:
                    System.out.println("Invalid entry. Please try again.");
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("Choose one of the options:");
        System.out.println("1- Enter the information of a faculty");
        System.out.println("2- Enter the information of a student");
        System.out.println("3- Print tuition invoice for a student");
        System.out.println("4- Print faculty information");
        System.out.println("5- Enter the information of a staff member");
        System.out.println("6- Print the information of a staff member");
        System.out.println("7- Delete a person");
        System.out.println("8- Exit Program");
        System.out.print("Enter your selection: ");
    }

    private static int getSelection() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean isValidID(String id) {
        return id.matches("[A-Za-z]{2}\\d{4}");
    }

    private static boolean isValidRank(String rank) {
        return rank.equalsIgnoreCase("Professor") || rank.equalsIgnoreCase("Adjunct");
    }
    
    private static boolean isValidDepartment(String department) {
        return department.equalsIgnoreCase("Mathematics") || department.equalsIgnoreCase("English") || department.equalsIgnoreCase("Engineering");
    }

    private static void addFaculty() {
        System.out.println("Enter the faculty info:");
        System.out.print("Name of the faculty: ");
        String name = scanner.nextLine();

        while (name.trim().isEmpty()) {
            System.out.println("Invalid name.");
            System.out.print("Name of the faculty: ");
            name = scanner.nextLine();
        }

        String id, rank, department;

        while (true) {
            System.out.print("ID: ");
            id = scanner.nextLine();
            if (isValidID(id)) {
                break;
            } else {
                System.out.println("Invalid ID format. Must be LetterLetterDigitDigitDigitDigit");
            }
        }
        
        while (true) {
            System.out.print("Rank: ");
            rank = scanner.nextLine();
            if (isValidRank(rank)) {
                break;
            } else {
                System.out.println('"' + rank + '"' + " is invalid");
            }
        }

        while (true) {
            System.out.print("Department: ");
            department = scanner.nextLine();
            if (isValidDepartment(department)) {
                break;
            } else {
                System.out.println('"' + department + '"' + " is invalid");
            }
        }

        Faculty faculty = new Faculty(name, id, department, rank);
        personnelList.add(faculty);
        System.out.println("Faculty added!");
    }

    private static void addStudent() {
        System.out.println("Enter the student info:");
        System.out.print("Name of student: ");
        String name = scanner.nextLine();

        while (name.trim().isEmpty()) {
            System.out.println("Invalid name.");
            System.out.print("Name of student: ");
            name = scanner.nextLine();
        }

        String id;

        while (true) {
            System.out.print("ID: ");
            id = scanner.nextLine();
            if (isValidID(id)) {
                break;
            } else {
                System.out.println("Invalid ID format. Must be LetterLetterDigitDigitDigitDigit");
            }
        }

        double gpa = 0;
        boolean validGpa = false;

        while (!validGpa) {
            System.out.print("GPA: ");
            String gpaInput = scanner.nextLine();

            if (gpaInput.trim().isEmpty()) {
                System.out.println("Invalid GPA.");
            } else {
                try {
                    gpa = Double.parseDouble(gpaInput);
                    if (gpa >= 0.0 && gpa <= 4.0) {
                        validGpa = true;
                    } else {
                        System.out.println("Invalid GPA. GPA must be between 0.0 and 4.0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid GPA format. Please enter a valid number.");
                }
            }
        }

        int creditHours = 0;
        boolean validCreditHours = false;

        while (!validCreditHours) {
            System.out.print("Credit hours: ");
            String creditHoursInput = scanner.nextLine();
    
            if (creditHoursInput.trim().isEmpty()) {
                System.out.println("Invalid credit hours.");
            } else {
                try {
                    creditHours = Integer.parseInt(creditHoursInput);
                    if (creditHours >= 0) {
                        validCreditHours = true;
                    } else {
                        System.out.println("Invalid credit hours. Credit hours must be a non-negative value.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid credit hours format. Please enter a valid whole number.");
                }
            }
        }
    
        Student student = new Student(name, id, gpa, creditHours);
        personnelList.add(student);
        System.out.println("Student added!");
    }

    private static void printTuitionInvoice() {
        String id;

        while (true) {
            System.out.print("Enter the student's ID: ");
            id = scanner.nextLine();
            if (isValidID(id)) {
                break;
            } else {
                System.out.println("Invalid ID format. Must be LetterLetterDigitDigitDigitDigit");
            }
        }

        boolean studentFound = false;

        for (Person person : personnelList) {
            if (person instanceof Student && person.getId().equals(id)) {
                System.out.println("\nHere is the tuition invoice for " + ((Student) person).getFullName() + ":");
                ((Student) person).generateTuitionInvoice();
                studentFound = true;
                break;
            }
        }

        if (!studentFound) {
            System.out.println("No student matched!");
        }
    }

    private static void printFacultyInformation() {
        String id;

        while (true) {
            System.out.print("Enter the faculty's ID: ");
            id = scanner.nextLine();
            if (isValidID(id)) {
                break;
            } else {
                System.out.println("Invalid ID format. Must be LetterLetterDigitDigitDigitDigit");
            }
        }
        
        boolean facultyFound = false;

        for (Person person : personnelList) {
            if (person instanceof Faculty && person.getId().equals(id)) {
                person.print();
                facultyFound = true;
                break;
            }
        }

        if (!facultyFound) {
            System.out.println("No faculty member matched!");
        }
    }

    private static void addStaffMember() {
        System.out.print("Name of the staff member: ");
        String name = scanner.nextLine();

        while (name.trim().isEmpty()) {
            System.out.println("Invalid name.");
            System.out.print("Name of the staff member: ");
            name = scanner.nextLine();
        }

        String id, department, status;

        while (true) {
            System.out.print("Enter the ID: ");
            id = scanner.nextLine();
            if (isValidID(id)) {
                break;
            } else {
                System.out.println("Invalid ID format. Must be LetterLetterDigitDigitDigitDigit");
            }
        }

        while (true) {
            System.out.print("Department: ");
            department = scanner.nextLine();
            if (isValidDepartment(department)) {
                break;
            } else {
                System.out.println('"' + department + '"' + " is invalid");
            }
        }

        while (true) {
            System.out.print("Status, Enter P for Part Time, or Enter F for Full Time: ");
            status = scanner.nextLine();
            if (!status.trim().isEmpty() && (status.equalsIgnoreCase("P") || status.equalsIgnoreCase("F"))) {
                break;
            } else {
                System.out.println("Invalid status.");
            }
        }

        Staff staff = new Staff(name, id, department, status);
        personnelList.add(staff);
        System.out.println("Staff member added!");
    }

    private static void printStaffInformation() {
        String id;
        
        while (true) {
            System.out.print("Enter the Staff Member's ID: ");
            id = scanner.nextLine();
            if (isValidID(id)) {
                break;
            } else {
                System.out.println("Invalid ID format. Must be LetterLetterDigitDigitDigitDigit");
            }
        }

        boolean staffFound = false;

        for (Person person : personnelList) {
            if (person instanceof Staff && person.getId().equals(id)) {
                person.print();
                staffFound = true;
                break;
            }
        }

        if (!staffFound) {
            System.out.println("No staff member matched!");
        }
    }

    private static void deletePerson() {
        String id;

        while (true) {
            System.out.print("Enter the ID of the person to delete: ");
            id = scanner.nextLine();
            if (isValidID(id)) {
                break;
            } else {
                System.out.println("Invalid ID format. Must be LetterLetterDigitDigitDigitDigit");
            }
        }

        for (Iterator<Person> iterator = personnelList.iterator(); iterator.hasNext(); ) {
            Person person = iterator.next();
            if (person.getId().equals(id)) {
                iterator.remove();
                System.out.println("Person with ID " + id + " deleted.");
                return;
            }
        }

        System.out.println("No person with ID " + id + " exists.");
    }

    private static void exitProgram() { 
        String choice;

        while (true) {
            System.out.print("Would you like to create the report? (Y/N): ");
            choice = scanner.nextLine();
                        
            if (choice.equalsIgnoreCase("Y")) {
                createReport();
                System.out.println("Goodbye!");
                System.exit(0);
            } else if (choice.equalsIgnoreCase("N")) {
                System.out.println("Goodbye!");
                System.exit(0);
            } else System.out.println("Invalid choice.");
        }             
    }

    private static void createReport() {
        final int[] sortBy = {0};
        boolean validSortBy = false;
        String sortByInput;

        while (!validSortBy) {
            System.out.print("Would you like to sort your students by descending GPA or name (1 for GPA, 2 for name): ");
            sortByInput = scanner.nextLine();
    
            try {
                int choice = Integer.parseInt(sortByInput);
                if (choice == 1 || choice == 2) {
                    sortBy[0] = choice;
                    validSortBy = true;
                } else {
                    System.out.println("Invalid input.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }

        Collections.sort(personnelList, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                if (p1 instanceof Student && p2 instanceof Student) {
                    if (sortBy[0] == 1) {
                        return Double.compare(((Student) p2).getGpa(), ((Student) p1).getGpa());
                    } else {
                        return p2.getFullName().compareToIgnoreCase(p1.getFullName());
                    }
                }
                return 0;
            }
        });

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String reportDate = dateFormat.format(date);
        int facultyCount = 0, staffCount = 0, StudentCount = 0;

        try (PrintWriter writer = new PrintWriter(new FileWriter("report.txt"))) {
            writer.println("Report created on " + reportDate);
            writer.println("***********************");
            writer.println("Faculty Members");
            writer.println("-------------------------");

            for (Person person : personnelList) {
                if (person instanceof Faculty) {
                    facultyCount++;
                    Faculty faculty = (Faculty) person;
                    writer.println(facultyCount + ". " + faculty.getFullName().toUpperCase());
                    writer.println("ID: " + faculty.getId());
                    writer.println(faculty.getRank().toUpperCase() + ", " + faculty.getDepartment().toUpperCase());
                    writer.println();
                }
            }

            writer.println("Staff Members");
            writer.println("-------------------");

            for (Person person : personnelList) {
                if (person instanceof Staff) {
                    staffCount++;
                    Staff staff = (Staff) person;
                    writer.println(staffCount + ". " + staff.getFullName().toUpperCase());
                    writer.println("ID: " + staff.getId());
                    writer.println(staff.getDepartment().toUpperCase() + ", " + (staff.getStatus().equalsIgnoreCase("p") ? "Part Time" : "Full Time"));
                    writer.println();
                }
            }

            writer.println("Students (Sorted by " + (sortBy[0] == 1 ? "GPA" : "Name") + " in descending order)");
            writer.println("-----------");

            for (Person person : personnelList) {
                if (person instanceof Student) {
                    StudentCount++;
                    Student student = (Student) person;
                    writer.println(StudentCount + ". " + student.getFullName().toUpperCase());
                    writer.println("ID: " + student.getId());
                    writer.println("GPA: " + student.getGpa());
                    writer.println("Credit Hours: " + student.getCreditHours());
                    writer.println();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Report created and saved to your hard drive!");
    }
}
//_____________________

abstract class Person {
    private String fullName;
    private String id;

    public Person(String fullName, String id) {
        this.fullName = fullName;
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public abstract void print();
}
//______________________________________

abstract class Employee extends Person {
    private String department;

    public Employee(String fullName, String id, String department) {
        super(fullName, id);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
//____________________________

class Staff extends Employee {
    private String status;

    public Staff(String fullName, String id, String department, String status) {
        super(fullName, id, department);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void print() {

        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println(getFullName().toUpperCase() + "\t\t" + getId());
        System.out.println(getDepartment().toUpperCase() + " Department, " + (getStatus().equalsIgnoreCase("p") ? "Part Time" : "Full Time"));
        System.out.println("---------------------------------------------------------------------------------------");
    }

}
//______________________________

class Faculty extends Employee {
    private String rank;

    public Faculty(String fullName, String id, String department, String rank) {
        super(fullName, id, department);
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public void print() {
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println(getFullName().toUpperCase() + "\t\t" + getId());
        System.out.println(getDepartment().toUpperCase() + " Department, " + getRank().toUpperCase());
        System.out.println("---------------------------------------------------------------------------------------");
    }
}
//____________________________

class Student extends Person {
    private double gpa;
    private int creditHours;

    public Student(String fullName, String id, double gpa, int creditHours) {
        super(fullName, id);
        this.gpa = gpa;
        this.creditHours = creditHours;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public void generateTuitionInvoice() {
        double creditHourCost = 236.45;
        double administrativeFee = 52.0;
        double totalPayment = (creditHourCost * creditHours) + administrativeFee;
        double discount = 0;
         

        if (gpa >= 3.85) {
            discount = totalPayment * 0.25;
            totalPayment -= discount;
            System.out.println("25% discount applied.");
        }

        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println(getFullName().toUpperCase() + "\t\t" + getId());
        System.out.println("Credit Hours: " + creditHours + " ($" + creditHourCost + "/credit hour)");
        System.out.println("Fees: $" + String.format("%.0f", administrativeFee));
        System.out.println("\nTotal Payment (after discount): $" + String.format("%,.2f", totalPayment) +
                           "\t\t(" + (discount > 0 ? "$" + String.format("%.2f", discount) + " discount applied" : "$0 discount applied") + ")");
        System.out.println("---------------------------------------------------------------------------------------");
    }

    @Override
    public void print() {
        System.out.println("----------------------------------------");
        System.out.println("Name: " + getFullName().toUpperCase());
        System.out.println("ID: " + getId());
        System.out.println("GPA: " + getGpa());
        System.out.println("Credit Hours: " + getCreditHours());
        System.out.println("----------------------------------------");
    }
}

