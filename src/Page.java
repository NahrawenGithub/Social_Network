import java.util.ArrayList;
import java.util.Scanner;

public class Page extends PageGroup {

    protected String Name;
    protected int Likes;
    protected Wall pageWall;
    protected Profile profile;
    protected ArrayList<Profile> likersList;
    protected  int idPage;
    public static int id;

    public int getIdPage() {
        return idPage;
    }
    public Page(String genre,Profile Creator, String Name , int idPage) {
        super(genre,Creator);
        this.Name = Name;
        this.idPage = idPage;
        id++;
        this.Likes = 0;
        this.pageWall = new Wall();
        this.profile = new Profile();
        this.likersList = new ArrayList<Profile>();

    }

    public String toString()
    {
        return "This page is named : " + this.Name +" and it's created by: " + this.Creator.name +
                "  genre: "+this.genre +"  likes: "+ this.Likes + "\n";
    }

    public void displayWall() {
        System.out.println(this.getPageWall().toString());
        this.getPageWall().displayChoicesGC();
        Scanner sc = new Scanner(System.in);
        int choice=sc.nextInt();
        switch (choice){
            case 0:
                break;
            case 1:
                this.profile.showMessagesToLike(this.getPageWall());
                getCreator().likePost(this.getPageWall());
                break;
            default:
                System.err.println("You gave a wrong argument.\n");
        }
    }

    int getLikes()
    {
        return this.Likes;
    }

    void setLikes() //A like is added to the page
    {
        this.Likes=this.Likes + 1;
    }

    void addLiker(Profile profile){
        this.likersList.add(profile);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Wall getPageWall() {
        return pageWall;
    }

    public void setPageWall(Wall pageWall) {
        this.pageWall = pageWall;
    }
}
