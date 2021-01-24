
package gr.netprog.Books;

import javax.xml.bind.annotation.XmlRootElement;
// Kάνει το αντικέιμενο books μετατρέψιμο σε xml
@XmlRootElement(name = "books")
public class Books{
    int id;
    String bookname;
    String author;
    String publisher; 
    int numOfbooks;

    public Books(int id, String bookname, String author, String publisher, int numOfbooks) {
        this.id = id;
        this.bookname = bookname;
        this.author = author;
        this.publisher = publisher;
        this.numOfbooks = numOfbooks;
    }



    public Books() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setNumOfbooks(int numOfbooks) {
        this.numOfbooks = numOfbooks;
    }

    public int getId() {
        return id;
    }

    public String getBookname() {
        return bookname;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getNumOfbooks() {
        return numOfbooks;
    }
    
            
    
}
