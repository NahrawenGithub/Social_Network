import java.io.Serializable;
import java.util.ArrayList;

public class Wall implements Serializable {

    private static final long serialVersionUID = -4531720166176709393L;
    protected ArrayList<Message> messageList;
    private ArrayList<Integer> likesToPost;

    public Wall() {
        this.messageList=new ArrayList<Message>();
        likesToPost = new ArrayList<Integer>();
    }

    public Wall(ArrayList<Message> list) {
        this.messageList=list;
    }

    public String toString() {
        String toReturn="";
        if(messageList.isEmpty()) {
            toReturn+="no messages in the wall";
        }else
            for(int i=0; i<messageList.size(); i++) {
                toReturn += "On " + messageList.get(i).getDate() + " From: " + messageList.get(i).getSender().getSurname() + " " +
                        messageList.get(i).getSender().getName() + "\n\t" + messageList.get(i).getContent() +
                        "   Likes:" + messageList.get(i).getLikes() + System.lineSeparator() + "\n";
            }
        return toReturn;
    }


    public void displayChoices (){
        System.out.println("0) exit");
        if(messageList.isEmpty()) {
            System.out.println("1) Post a Message.");
        } else{
            System.out.println("1) Post a Message.");
            System.out.println("2) Like a Post.");
        }
    }

    public void displayChoicesGC (){
        System.out.println("0) exit");
        if(!messageList.isEmpty())
            System.out.println("1) Like a Post.");
    }


    public ArrayList<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(ArrayList<Message> messageList) {
        this.messageList = messageList;
    }

    ArrayList<Integer> getLikes()
    {
        return likesToPost;
    }

    Integer getAppropriateMessageLikes(int i)
    {
        return likesToPost.get(i);
    }

    public void addMessage(Message m){
        messageList.add(m);
    }
}


































