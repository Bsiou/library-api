
package gr.netprog.database;
import gr.netprog.Books.Books;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BooksDao {
    public static Connection getConnection() {
        Connection con = null;
        
        try {
            // Χρήση mariadb driver
            Class.forName("org.mariadb.jdbc.Driver");
            // Connection με την βάση δεδομένων με τον χρήση Anastasiou
             con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/books", "Anastasiou", "secret");       
        } catch (ClassNotFoundException | SQLException ex) {           
        }
        return con;
        
    }
        // Σε όλες τις εντολές που έχουν να κάνουν με την βάση δεδομένων γίνεται prepare statement για να εξασφαλιστεί η ασφάλεια και να μην προκύψει SQL INJECTION
    
    
        // Επιστροφή λίστα με αντικέιμενα που τα στοιχεία τους υπάρχουν στην βάση δεδομένων με χρήση Select
        public List<Books> getAllBooks() {
             List<Books> BookList = new ArrayList();
            Connection con = BooksDao.getConnection();
        try {
           
            
            PreparedStatement ps = con.prepareStatement("SELECT * FROM mybooks");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Books mybook = new Books();
                mybook.setId(rs.getInt(1));
                mybook.setBookname(rs.getString(2));
                mybook.setAuthor(rs.getString(3));
                mybook.setPublisher(rs.getString(4));
                mybook.setNumOfbooks(rs.getInt(5));
                BookList.add(mybook);  
            }
            con.close();
        } catch (SQLException ex) {
            
        }
        return BookList; 
        
         
    }
        // Επιστροφή ενός αντικειμένουν books που τα στοιχεία του υπάρχουν στην βάση δεδομένων Select με where
        public Books getBook(int id) {
        Books mybook = new Books();
        Connection con = BooksDao.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM mybooks WHERE id=?");
            ps.setInt(1,id);  
            ResultSet rs = ps.executeQuery();
            //Τα αποτελέσματα του select μπαίνουν σε αντικείμενα books για όσες εγγραφές επιστράφηκαν απο το select
            if(rs.next()){  
                mybook.setId(rs.getInt(1));  
                mybook.setBookname(rs.getString(2));  
                mybook.setAuthor(rs.getString(3));  
                mybook.setPublisher(rs.getString(4));
                mybook.setNumOfbooks(rs.getInt(5));
            }
            con.close();
        } catch (SQLException ex) {}
        return mybook;
    }
     
        // Προσθήκη μια εγγραφής συγγράματος στην βάση δεδομένων με είσοδο ένα αντικείμενο , εκτέλεση  insert
        public int addBook(Books mybook) {
         // Το status θα επιστραφεί και αν είναι 0 σημαίνει οτι δεν επέτυχε το insert ενώ αν είναι 1 σημαίνει ότι πέτυχε
        int status = 0;
        Connection con = BooksDao.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO mybooks(bookname, author, publisher,numberofbooks) VALUES (?, ?, ?, ?)");
            ps.setString(1,mybook.getBookname());  
            ps.setString(2,mybook.getAuthor());  
            ps.setString(3,mybook.getPublisher());   
            ps.setInt(4,mybook.getNumOfbooks());   
            
            status=ps.executeUpdate();   // Επιστρέφει 1 αν πετύχει      
            con.close();
        } catch (SQLException ex) {}
        return status;        
    }
        
        // Eίσοδος ενός αντικειμένου και αλλαγή σtoιχείων μια εγγαφής στην βάση δεδομένων με τα αντίστοιχα στοιχεία του αντικειμένου , εκτέλεση update
        public int updateBook(Books mybook) {
        // Το status θα επιστραφεί και αν είναι 0 σημαίνει οτι δεν επέτυχε το update ενώ αν είναι 1 σημαίνει ότι πέτυχε
        int status = 0;
        Connection con = BooksDao.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE mybooks SET bookname=?,author=?, publisher=?, numberofbooks=? WHERE id=?");
            ps.setString(1, mybook.getBookname());  
            ps.setString(2, mybook.getAuthor());  
            ps.setString(3, mybook.getPublisher()); 
            ps.setInt(4,mybook.getNumOfbooks());
            ps.setInt(5, mybook.getId());
            status=ps.executeUpdate();     // Επιστρέφει 1 αν πετύχει         
            con.close();
        } catch (SQLException ex) {}
        return status;
    }
        
        //Είσοδος ενός ακεραίου που ειναι το id δηλαδή το κύριο κλειδί του πίνακα my books διαγραφή αυτής της εγγραφή δηλαδή εκτλέλεση delete
        public int deleteΒοοκ(int id) {
        // ΤΟ status θα επιστραφεί και αν ειναι 0 σημαίνει οτι δεν επέτυχε η διαγγραφή ενώ αν ειναι 1 πέτυχε
        int status = 0;
        Connection con = BooksDao.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM mybooks WHERE id=?");
            ps.setInt(1, id);
            status=ps.executeUpdate();     // Επιστρέφει 1 αν πετύχει        
            con.close();            
        } catch (SQLException ex) {}
        return status;
    }
        
        
     
        
        
        
        
        
    
}
