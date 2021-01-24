
package gr.netprog.service;
import gr.netprog.Books.Books;
import gr.netprog.database.BooksDao;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
//Το αρχικό path για να μπει στο Service
@Path("/bookservice")
public class BookService {
    BooksDao booksDao = new BooksDao();
    @GET 
    @Path("/booksxml")
    // Επιστρέφει xml 
    @Produces(MediaType.APPLICATION_XML)
    public List<Books> getBooksXml(){
        List<Books> mybooklist = new ArrayList();
        List<Books> finallist = new ArrayList();
        mybooklist= booksDao.getAllBooks();
        //Γίνεται έλεγχος για το αν το κάθε σύγγραμα έχει διαθέσιμο αριθμό συγγραμάτων για να μπει στην λίστα που θα επιστραφεί
        for (int i=0; i<mybooklist.size();i++){
             if (mybooklist.get(i).getNumOfbooks()>0){
                 finallist.add(mybooklist.get(i));
             }
                
        }
        return finallist;
    }
    
    @GET
    @Path("/booksjson")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Books> getBooksJson(){
        List<Books> mybooklist = new ArrayList();
        List<Books> finallist = new ArrayList();
         mybooklist= booksDao.getAllBooks();
          //Γίνεται έλεγχος για το αν το κάθε σύγγραμα έχει διαθέσιμο αριθμό συγγραμάτων για να μπει στην λίστα που θα επιστραφεί
        for (int i=0; i<mybooklist.size();i++){
             if (mybooklist.get(i).getNumOfbooks()>0){
                 finallist.add(mybooklist.get(i));
             }
        }
        
        return finallist;
    }
    
    @GET
    @Path("/choosexml/{bookid}")
    // Έχει παράμετρο το bookid που δίνεται απο το path που θα χτυπήσει με το GET 
    //Επιστρέφει xml 
    @Produces(MediaType.APPLICATION_XML)    
    public Books getOneBookXml(@PathParam("bookid")int id) {
        return booksDao.getBook(id);
    }
    
    @GET
    @Path("/choosejson/{bookid}")
    // Έχει παράμετρο το bookid που δίνεται απο το path που θα χτυπήσει με το GET 
    @Produces(MediaType.APPLICATION_JSON)
    // Επιστρέφει json
    public Books getΒοοκJson(@PathParam("bookid")int id) {
        return booksDao.getBook(id);
    }
    
    
    @POST
    @Path("/order")
    @Produces(MediaType.TEXT_HTML)
    //Επιστρέφει text_HTML
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    // Δέχεται παραμέτρους απο μία φόρμα
    public String MakeOrder(@FormParam("id") int id, @FormParam("number") int numOfbooks,@Context HttpServletResponse servletResponse){
        Books mybook = new Books();
        mybook = booksDao.getBook(id);
        // Τσεκάρεται αν ο αριθμός των διαθέσιμων βιβλίων με το συγκεκριμένο id που δόθηκε ως παράμετρος είναι μεγαλύτερος απο τον αριθμο που δόθηκε ως παράμετρος στην παραγγελία
        if (mybook.getNumOfbooks()>=numOfbooks){
            mybook.setNumOfbooks(mybook.getNumOfbooks()-numOfbooks);
            booksDao.updateBook(mybook);
            return "Succesfull Order";
        }
        // Αν είναι 0 ο αριθμός των διαθέσιμων βιβλίων
        else if ( mybook.getNumOfbooks() == 0) {
            return "Out of Stock";
        }
        // TO else καλύτπει την περίπτωση που ο αριθμός παραγγελίας είναι μεγαλύτερος απο τον διαθέσιμο αριθμό συγγραμάτων
        else {
            return "Order Error";
        }
    }
    
    @POST
    @Path("/addbook")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    //Παράγει Text_Html
    //Δέχεται φόρμα με παραμέτρους
    public String createUser(@FormParam("bookname") String bookname,
            @FormParam("author") String author,
            @FormParam("publisher") String publisher,
            @FormParam("number") int numberOfbooks,
            @Context HttpServletResponse servletResponse) {
        Books mybook = new Books();
        mybook.setBookname(bookname);
        mybook.setAuthor(author);
        mybook.setPublisher(publisher);
        mybook.setNumOfbooks(numberOfbooks);
        // Καταχωρεί νέα εγγραφή στην βάση με τα στοιχεία που δόθηκαν ώς παράμετροι
        int result = booksDao.addBook(mybook);
        // Το result==1 όταν πέτυχε η εγγραφή στην βάση
        if (result == 1) {
            return "Insert completed";
        }
        return "Insert Error";
        
    }
    
    
     @DELETE    
    @Path("/deletebook/{id}")
     //Διαγράφει συγκεκριμένο βιβλίο απο την βάση δέχεται παράμετρο μέσω του path το id
    @Produces(MediaType.TEXT_HTML)
     // Παράγει Text_HTML
    public String DeleteBook(@PathParam("id") int id ) {
        Books mybook = new Books();
        int result= booksDao.deleteΒοοκ(id);
        //Διαγραφή βιβλίου απο την βάση
        //Αν επιτύχει η διαγραφή επιστρέφει το result θα έχει 1
        if (result == 1) {
            return "Delete completed";
        }
        return "Delete Error";
    }
    
    @PUT
    @Path("/updatebooks")
    // Αλλαγή στοιχείων μια εγγραφής στην βάση 
    // Δέχεται παραμέτρους απο μία φορμα
    //Επιστρέφει TEXT_HTML
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String updateBook(@FormParam("id")int id,
            @FormParam("bookname") String bookname,
            @FormParam("author") String author,
            @FormParam("publisher") String publisher,
            @FormParam("number") int numberOfbooks,
            @Context HttpServletResponse servletResponse) {
        Books mybook = new Books();
        mybook= booksDao.getBook(id);
        // Τσεκάρει για όλα τα στοιχεία αν έχουν μέσα null την περίτπωση δηλαδή πως δεν έδωσε μια παράμετρο από την φόρμα και αν έχει null κρατάει την τιμή που είχε πριν
        if (bookname!=null){
            mybook.setBookname(bookname);
        }
        if (author!=null){
            mybook.setAuthor(author);
        }
        if(publisher!=null){
            mybook.setPublisher(publisher);
        }
        
            mybook.setNumOfbooks(numberOfbooks);
        
        
        int result = booksDao.updateBook(mybook);
        // TO result θα γίνει 1 αν επιτύχει το update
        if (result == 1) {
            return " Update Completed ";
        }
        return "Update Failed";
    }
        
   
}       
