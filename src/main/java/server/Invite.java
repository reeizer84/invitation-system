package server;

public class Invite {
    public String from;
    public String to;
    public boolean answered = false;
    public boolean accepted = false;

    public void setFrom(String from_in){
        from = from_in;
    }
    public void setTo (String to_in){
        to = to_in;
    }
}
