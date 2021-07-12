package ir.lucifer.approject;

public class Product {
    private static int n = 1;
    public int ID;
    public String category = "-";
    public String subject;
    public String description;
    public String price;
    public Boolean isStar;
    public String photoLink;
    public String sellerID;
    public String buyerID = "";
    public String sellerToken;
    public String buyerToken;

    public Product() {
    }
    public Product(String subject , String price)
    {
        this.subject = subject;
        this.price = price;
    }
    public Product(boolean lol)
    {

    }

}
