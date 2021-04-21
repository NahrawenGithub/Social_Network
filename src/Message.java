import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{

    private static final long serialVersionUID = 3577281671026193064L;
    protected Profile sender;
    protected Date date;
    protected String content;
    private int likes;

    public Message(Profile sender, Date date, String content)
    {
        this.sender = sender;
        this.date = date;
        this.content = content;
        this.likes=0;
    }


    public Profile getSender() {
        return sender;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date=date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content=content;
    }

    int getLikes()
    {
        return this.likes;
    }

    void setLikes() //A like is added to the Message
    {
        this.likes=this.likes + 1;
    }

    public String toString()
    {
        return "On "+this.getDate()+" From: "+this.getSender().getSurname()+" "+this.getSender().getName()+"\n\t"+this.getContent()+"\t"+"Likes:"+this.getLikes()+"\n";
    }

}
