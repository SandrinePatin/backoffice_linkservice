package sample;

public class Ticket {
    private int id;
    private String date;

    public Ticket (int idTicket, String d) {
        id = idTicket;
        date = d;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }
}
