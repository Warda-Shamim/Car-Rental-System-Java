import javax.swing.*;
import java.awt.*;
import java.sql.*;

// ===== INTERFACE =====
interface Dashboard {
    void showMenu();
}

// ===== ABSTRACT USER =====
abstract class User {
    protected int id;
    protected String username;

    public User(int id, String username){
        this.id = id;
        this.username = username;
    }
}

// ===== CUSTOMER =====
class Customer extends User implements Dashboard {
    public Customer(int id, String username){
        super(id, username);
    }

    public void showMenu(){
        new CustomerUI(id);
    }
}

// ===== OWNER =====
class Owner extends User implements Dashboard {
    public Owner(int id, String username){
        super(id, username);
    }

    public void showMenu(){
        new OwnerUI();
    }
}

// ===== DB CONNECTION =====
class DB {
    static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/carrentaldb",
            "root",
            "@@@12345"
        );
    }
}

// ===== AUTH SERVICE =====
class AuthService {
    static User login(String type, String username, String password){
        try(Connection con = DB.getConnection()){
            String query = type.equals("owner") ?
                    "SELECT * FROM Owner WHERE username=? AND password=?" :
                    "SELECT * FROM Customers WHERE username=? AND password=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int id = type.equals("owner") ? rs.getInt("owner_id") : rs.getInt("customer_id");
                return type.equals("owner") ?
                        new Owner(id, username) :
                        new Customer(id, username);
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Login Error: "+e.getMessage());
        }
        return null;
    }
}

// ===== BOOKING SERVICE =====
class BookingService {

    static void bookCar(int customerId, int carId){
        try(Connection con = DB.getConnection()){
            String q = "INSERT INTO Bookings(customer_id,car_id,start_date,end_date,total_amount) VALUES(?,?,CURDATE(),DATE_ADD(CURDATE(), INTERVAL 2 DAY),5000)";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setInt(1, customerId);
            ps.setInt(2, carId);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Car Booked Successfully!");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Booking Error: "+e.getMessage());
        }
    }

    static void cancelBooking(int bookingId, int customerId){
        try(Connection con = DB.getConnection()){
            String q = "UPDATE Bookings SET booking_status='Cancelled' WHERE booking_id=? AND customer_id=?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setInt(1, bookingId);
            ps.setInt(2, customerId);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Booking Cancelled!");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Cancel Error: "+e.getMessage());
        }
    }
}

// ===== MAIN UI =====
class MainUI extends JFrame {
    MainUI(){
        setTitle("Car Rental System");
        setSize(400,250);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("CAR RENTAL SYSTEM", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.BLUE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(200,220,240));

        JButton ownerBtn = createButton("Owner Login");
        JButton custBtn = createButton("Customer Login");

        panel.add(ownerBtn);
        panel.add(custBtn);

        add(title, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        ownerBtn.addActionListener(e-> new LoginUI("owner"));
        custBtn.addActionListener(e-> new LoginUI("customer"));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    JButton createButton(String text){
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(Color.WHITE);
        return btn;
    }
}

// ===== LOGIN UI (CENTERED + BORDERED) =====
class LoginUI extends JFrame {
    LoginUI(String type){
        setTitle(type + " Login");
        setSize(400,300);
        setLayout(new BorderLayout());

        JPanel box = new JPanel(new GridLayout(4,2,10,10));
        box.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY,2),
                "Login Panel",
                0,0,
                new Font("Arial", Font.BOLD,16)
        ));
        box.setBackground(Color.WHITE);

        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();

        JButton login = createButton("Login");
        JButton exit = createButton("Exit");

        box.add(new JLabel("Username:"));
        box.add(user);
        box.add(new JLabel("Password:"));
        box.add(pass);
        box.add(login);
        box.add(exit);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(new Color(200,220,240));
        wrapper.add(box);

        add(wrapper, BorderLayout.CENTER);

        login.addActionListener(e->{
            User u = AuthService.login(type, user.getText(), new String(pass.getPassword()));
            if(u != null){
                ((Dashboard)u).showMenu();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,"Invalid Login");
            }
        });

        exit.addActionListener(e-> System.exit(0));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    JButton createButton(String text){
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(Color.WHITE);
        return btn;
    }
}

// ===== OWNER UI =====
class OwnerUI extends JFrame {
    OwnerUI(){
        setTitle("Owner Dashboard");
        setSize(400,300);
        setLayout(new GridLayout(4,1,10,10));
        getContentPane().setBackground(new Color(200,220,240));

        JButton cars = createButton("View Cars");
        JButton customers = createButton("View Customers");
        JButton bookings = createButton("View Bookings");
        JButton payments = createButton("View Payments");

        add(cars); add(customers); add(bookings); add(payments);

        cars.addActionListener(e->show("SELECT * FROM Cars"));
        customers.addActionListener(e->show("SELECT * FROM Customers"));
        bookings.addActionListener(e->show("SELECT * FROM Bookings"));
        payments.addActionListener(e->show("SELECT * FROM Payments"));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    JButton createButton(String t){
        JButton b = new JButton(t);
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setBackground(Color.WHITE);
        return b;
    }

    void show(String q){
        try(Connection c = DB.getConnection();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(q)){

            String data="";
            ResultSetMetaData md = r.getMetaData();
            int cols = md.getColumnCount();

            while(r.next()){
                for(int i=1;i<=cols;i++){
                    data += md.getColumnName(i)+": "+r.getString(i)+"   ";
                }
                data += "\n\n";
            }

            JTextArea area = new JTextArea(data);
            JOptionPane.showMessageDialog(this,new JScrollPane(area));

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Error: "+e.getMessage());
        }
    }
}

// ===== CUSTOMER UI =====
class CustomerUI extends JFrame {
    int id;

    CustomerUI(int id){
        this.id=id;

        setTitle("Customer Dashboard");
        setSize(400,350);
        setLayout(new GridLayout(5,1,10,10));
        getContentPane().setBackground(new Color(200,220,240));

        JButton viewCars = createButton("View Cars");
        JButton book = createButton("Book Car");
        JButton cancel = createButton("Cancel Booking");
        JButton myBookings = createButton("My Bookings");
        JButton myPayments = createButton("My Payments");

        add(viewCars); add(book); add(cancel); add(myBookings); add(myPayments);

        viewCars.addActionListener(e->show("SELECT * FROM Cars"));
        myBookings.addActionListener(e->show("SELECT * FROM Bookings WHERE customer_id="+id));
        myPayments.addActionListener(e->show("SELECT * FROM Payments WHERE customer_id="+id));

        book.addActionListener(e->bookCar());
        cancel.addActionListener(e->cancelBooking());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    JButton createButton(String t){
        JButton b = new JButton(t);
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setBackground(Color.WHITE);
        return b;
    }

    void bookCar(){
        try(Connection c = DB.getConnection();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT car_id,brand,model FROM Cars")){

            String list="";
            while(r.next()){
                list += r.getInt(1)+" - "+r.getString(2)+" "+r.getString(3)+"\n";
            }

            String input = JOptionPane.showInputDialog("Available Cars:\n"+list+"\nEnter Car ID:");
            if(input!=null){
                BookingService.bookCar(id,Integer.parseInt(input));
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Error: "+e.getMessage());
        }
    }

    void cancelBooking(){
        String bid = JOptionPane.showInputDialog("Enter Booking ID:");
        try{
            BookingService.cancelBooking(Integer.parseInt(bid), id);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Invalid Input");
        }
    }

    void show(String q){
        try(Connection c = DB.getConnection();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(q)){

            String data="";
            ResultSetMetaData md = r.getMetaData();
            int cols = md.getColumnCount();

            while(r.next()){
                for(int i=1;i<=cols;i++){
                    data += md.getColumnName(i)+": "+r.getString(i)+"   ";
                }
                data += "\n\n";
            }

            JTextArea area = new JTextArea(data);
            JOptionPane.showMessageDialog(this,new JScrollPane(area));

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Error: "+e.getMessage());
        }
    }
}

// ===== MAIN CLASS =====
public class CarRental {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> new MainUI());
    }
}
