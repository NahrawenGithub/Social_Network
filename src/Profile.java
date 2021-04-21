import java.io.*;
import java.net.BindException;
import java.util.*;

public class Profile implements Serializable {

    private static final long serialVersionUID = 3646937831314970326L;
    protected String name, surname, password, birth_date, email;
    protected String place;
    protected  int idUser;
    public static int id;
    protected Wall personalWall= new Wall();
    protected LinkedHashSet<Invitation> invitationListSet,invitationSendedListSet = new LinkedHashSet<Invitation>();
    protected ArrayList<Invitation> invitationList, invitationSendedList = new ArrayList<Invitation>();
    protected ArrayList<Profile> friendsList = new ArrayList<Profile>();
    protected ArrayList<Page> pages = new ArrayList<Page>();
    protected ArrayList<Group> groups = new ArrayList<Group>();
    private ArrayList<Message> likedPosts=new ArrayList<Message>(); //All liked posts for the user will exist here
    private ArrayList<Page> likedPages=new ArrayList<Page>(); //All liked pages for the user will exist here
    private ArrayList<Group> joinedGroups=new ArrayList<Group>(); //All joined groups for the user will exist here
    private List<Profile> shortestPath = new LinkedList<>();

    public List<Profile> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Profile> shortestPath) {
        this.shortestPath = shortestPath;
    }


    public Profile() {}

    public Profile(String name, String surname, String password,
                   String birth_date, String place, String email,int idUser)
    {        this.idUser = idUser;

        this.name = name;
        this.surname = surname;
        this.password = password;
        this.birth_date = birth_date;
        this.place = place;
        this.email = email;
        this.personalWall = new Wall();
        this.invitationList = new ArrayList<Invitation>();
        this.invitationSendedList = new ArrayList<Invitation>();
        this.invitationListSet=new LinkedHashSet<Invitation>();
        this.invitationSendedListSet=new LinkedHashSet<Invitation>();
        this.friendsList = new ArrayList<Profile>();
        this.pages = new ArrayList<Page>();
        this.groups = new ArrayList<Group>();
        this.likedPosts=new ArrayList<Message>();
        this.likedPages = new ArrayList<Page>();
        this.joinedGroups = new ArrayList<Group>();
        this.shortestPath = new LinkedList<Profile>();
        id++;

    }

    public String toString()
    {
        return "id :" + this.idUser +"\nname: " + this.name + "\nsurname: " + this.surname
                + "\nbirthdate: " + this.birth_date.toString() + "\nplace: "
                + this.place.toString() + "\nemail: " + this.email + "\n";
    }

    public void displayProfile()
    {
        System.out.println(this);
    }

    public void showMessagesToLike(Wall wall) //Method that will print all posts in the wall to be liked
    {
        System.out.println("Select one the above posts to like:");
        int i;
        for(i=0;i<wall.getMessageList().size();i++)
        {
            System.out.println(i+"."+wall.getMessageList().get(i).toString());
        }
    }

    public void showPages(ArrayList<Page> pages)
    {
        int i;
        for(i=0;i<pages.size();i++)
        {
            System.out.println(i+"."+pages.get(i).toString());
        }
    }

    public void displayLikedPages(ArrayList<Page> pages) {
        int choice1, choice2,choice3;
        Scanner sc = new Scanner(System.in);
        if (pages.isEmpty())
        {
            System.out.println("No pages liked.");
        }
        else
            {
            this.showPages(pages);
            System.out.println("1 select page");
            System.out.println("2 exit");
            choice3 = Integer.parseInt(sc.nextLine());
            switch (choice3){
                case 1:
                    System.out.println("enter page's number");
                    choice1 = Integer.parseInt(sc.nextLine());
                    Page page=pages.get(choice1);
                    System.out.println("1 show page's wall");
                    System.out.println("2 Exit");
                    try
                    {
                        choice2 = Integer.parseInt(sc.nextLine());
                    }
                    catch(Exception e)
                    {
                        choice2=-1;
                    }
                    if (choice2==1)
                    {
                        System.out.println(page.getPageWall().toString());
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void displayPagesToLike(ArrayList<Page> pages) {
        int choice1, choice2;
        Scanner sc = new Scanner(System.in);
        if (pages.isEmpty())
        {
            System.out.println("No pages found.");
        }
        else
            {
            this.showPages(pages);
            System.out.println("Select one the above pages:");
            choice1 = Integer.parseInt(sc.nextLine());
            Page page=pages.get(choice1);
            do {
                System.out.println("1 show page's wall");
                System.out.println("2 like page");
                System.out.println("3 Exit");
                try{
                    choice2 = Integer.parseInt(sc.nextLine());
                }catch(Exception e){
                    choice2=-1;
                }
                if (choice2==1){
                    System.out.println(page.getPageWall().toString());
                }
                else if(choice2==2)
                    this.likePage(pages.get(choice1));
            } while(choice2!=3);
        }
    }

    void likePage(Page page)
    {
        int i=0;
        boolean pageNotFound=true;
        try
        {
            while(pageNotFound&&i<this.likedPages.size())
            {
                if(page==this.likedPages.get(i))
                {
                    pageNotFound=false;
                    System.out.println("You have already liked this page.\n");
                }
                ++i;
            }
            if(pageNotFound)
            {
                this.likedPages.add(page); //The liked Message A is added to the posts that the user liked for future reference
                page.setLikes(); //Increase by 1 the likes of this post
                page.addLiker(this); // add profile to pages likers list
                System.out.println(this.getName()+" liked page "+page.getName());
            }
        }
        catch(NumberFormatException e)
        {
            System.err.println("You typed something that is not number.\n");
        }
        catch(IndexOutOfBoundsException e)
        {
            System.err.println("You typed a non compatible number.\n");
        }
    }

    public void displayManageablePages(ArrayList<Page> pages) {
        int choice1, choice2;
        Scanner sc = new Scanner(System.in);
        if (pages.isEmpty())
        {
            System.out.println("No pages found.");
        }
        else {
            this.showPages(pages);
            System.out.println("Select one the above pages to manage:");
            choice1 = Integer.parseInt(sc.nextLine());
            do {
                System.out.println("1 Post a message");
                System.out.println("2 See the Page's wall");
                System.out.println("3 Exit");
                try{
                    choice2 = Integer.parseInt(sc.nextLine());
                }catch(Exception e){
                    choice2=-1;
                }
                if (choice2==1)
                    this.writeMessage(pages.get(choice1));
                else if (choice2==2)
                    pages.get(choice1).displayWall();
            } while(choice2!=3);
        }
    }

    public void showGroups(ArrayList<Group> groups)
    {
        int i;
        for(i=0;i<groups.size();i++)
        {
            System.out.println(i+"."+groups.get(i).toString());
        }
    }

    public void displayJoinedGroups(ArrayList<Group> groups) {
        int choice1, choice2,choice3;
        Scanner sc = new Scanner(System.in);
        if (groups.isEmpty())
        {
            System.out.println("No groups joined.");
        }
        else {
            this.showGroups(groups);
            System.out.println("1 select group");
            System.out.println("2 exit");
            choice3 = Integer.parseInt(sc.nextLine());
            switch (choice3){
                case 1:
                    System.out.println("enter groups's number");
                    choice1 = Integer.parseInt(sc.nextLine());
                    Group group=groups.get(choice1);
                    System.out.println("1 show group's wall");
                    System.out.println("2 Exit");
                    try
                    {
                        choice2 = Integer.parseInt(sc.nextLine());
                    }
                    catch(Exception e)
                    {
                        choice2=-1;
                    }
                    if (choice2==1){
                        System.out.println(group.getGroupWall().toString());
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void displayGroupsToJoin(ArrayList<Group> groups) {
        int choice1, choice2;
        Scanner sc = new Scanner(System.in);
        if (groups.isEmpty())
        {
            System.out.println("No groups found.");
        }
        else {
            this.showGroups(groups);
            System.out.println("Select one the above groups:");
            choice1 = Integer.parseInt(sc.nextLine());
            Group group=groups.get(choice1);
            do {
                System.out.println("1 show group's wall");
                System.out.println("2 join group");
                System.out.println("3 Exit");
                try{
                    choice2 = Integer.parseInt(sc.nextLine());
                }catch(Exception e){
                    choice2=-1;
                }
                if (choice2==1){
                    System.out.println(group.getGroupWall().toString());
                }
                else if(choice2==2)
                    this.sendInvitation(groups.get(choice1));
            } while(choice2!=3);
        }
    }

    public void displayManageableGroups(ArrayList<Group> groups) {
        int choice1, choice2;
        Scanner sc = new Scanner(System.in);
        if (groups.isEmpty())
        {
            System.out.println("No groups found.");
        }
        else {
            this.showGroups(groups);
            System.out.println("Select one the above groups to manage :");
            choice1 = Integer.parseInt(sc.nextLine());
            do {
                System.out.println("1 Post a message");
                System.out.println("2 See the group's wall");
                System.out.println("3 Show Requests");
                System.out.println("4 Exit");
                try{
                    choice2 = Integer.parseInt(sc.nextLine());
                }catch(Exception e){
                    choice2=-1;
                }
                if (choice2==1)
                    this.writeMessage(this.getGroups().get(choice1));
                else if (choice2==2)
                    groups.get(choice1).displayWall();
                else if (choice2==3)
                    groups.get(choice1).acceptInvitation();
            } while(choice2!=4);
        }
    }

    void likePost(Wall theWall)
    {
        int i=0;
        boolean messageNotFound=true;
        System.out.print("Like a Post by typing the appropriate number: ");
        Scanner sc = new Scanner(System.in);
        int choice=sc.nextInt();
        try
        {
            Message a = theWall.getMessageList().get(choice);
            while(messageNotFound&&i<likedPosts.size())
            {
                if(a==this.likedPosts.get(i))
                {
                    messageNotFound=false;
                    System.out.println("You have already liked this post.\n");
                }
                ++i;
            }
            if(messageNotFound)
            {
                this.likedPosts.add(a); //The liked Message A is added to the posts that the user liked for future reference
                a.setLikes(); //Increase by 1 the likes of this post
                System.out.println(this.getName()+" liked post "+a.toString());
            }
        }
        catch(NumberFormatException e)
        {
            System.err.println("You typed something that is not number.\n");
        }
        catch(IndexOutOfBoundsException e)
        {
            System.err.println("You typed a non compatible number.\n");
        }
    }
    public void writeMessage(Profile profile) {
        Scanner sc = new Scanner(System.in);
        Date d = new Date();
        System.out.println("Your message : ");
        Message m = new Message(this, d, sc.nextLine());
        profile.getPersonalWall().getMessageList().add(m);
        System.out.println("Message posted.\n");
    }

    public void writeMessage(Page page) {
        Scanner sc = new Scanner(System.in);
        Date d = new Date();
        System.out.println("Your message : ");
        Message m = new Message(this, d, sc.nextLine());
        page.getPageWall().getMessageList().add(m);
        System.out.println("Message posted.\n");
    }

    public void writeMessage(Group group) {
        Scanner sc = new Scanner(System.in);
        Date d = new Date();
        System.out.println("Your message : ");
        Message m = new Message(this, d, sc.nextLine());
        group.getGroupWall().getMessageList().add(m);
        System.out.println("Message posted.\n");
    }

    public void writeMessageToFriend() {
        Scanner sc = new Scanner(System.in);
        int choice;
        if (!this.friendsList.isEmpty())
        {
            displayFriends();
            System.out.println("Choose a friend: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                choice = -1;
            }
            if (choice > -1 && choice < this.friendsList.size()) {
                Date d = new Date();
                System.out.println("Your message: ");
                Message m = new Message(this, d, sc.nextLine());
                this.friendsList.get(choice).getPersonalWall().getMessageList()
                        .add(m);
                System.out.println("Message sent.\n");
            } else
                System.out.println("Invalid input.\n");
        } else
            System.out.println("You have no friends.\n");
    }

    public void displayFriends() {
        if (!this.friendsList.isEmpty())
        {
            System.out.println("friends:");
            for (int i = 0; i < this.friendsList.size(); i++)
            {
                System.out.println(i + ") " + this.friendsList.get(i).getSurname()
                        + " " + this.friendsList.get(i).getName());
            }
        } else
            System.out.println("You have no friends.\n");
    }

    public void displayFriendProfile() {
        Scanner sc = new Scanner(System.in);
        int choice;
        if (!this.friendsList.isEmpty())

        {
            displayFriends();
            System.out.println("Choose a friend: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                choice = -1;
            }
            if (choice > -1 && choice < this.friendsList.size()) {
                System.out.println(this.friendsList.get(choice));
            } else
                System.out.println("Invalid input.");
        } else
            System.out.println("You have no friend.");
    }

    public void displayWall() {
        System.out.println(this.getPersonalWall().toString());
        this.getPersonalWall().displayChoices();
        Scanner sc = new Scanner(System.in);
        int choice=sc.nextInt();
        switch (choice){
            case 0:
                break;
            case 1:
                this.writeMessage(this);
                break;
            case 2:
                this.showMessagesToLike(this.getPersonalWall());
                this.likePost(this.getPersonalWall());
                break;
            default:
                System.err.println("You gave a wrong argument.\n");
        }
    }

    public void displayFriendWall() {
        Scanner sc = new Scanner(System.in);
        int choice;
        if (!this.friendsList.isEmpty())
        {
            displayFriends();
            System.out.println("Choose a friend: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                choice = -1;
            }
            if (choice > -1 && choice < this.friendsList.size())
            {
                Profile friendprofile=this.friendsList.get(choice);
                friendprofile.displayProfile();
                System.out.println(friendprofile.getPersonalWall().toString());
                friendprofile.getPersonalWall().displayChoices();
                int choice1=sc.nextInt();
                switch (choice1){
                    case 0:
                        break;
                    case 1:
                        this.writeMessage(friendprofile);
                        break;
                    case 2:
                        friendprofile.showMessagesToLike(friendprofile.getPersonalWall());
                        this.likePost(friendprofile.getPersonalWall());
                        break;
                    default:
                        System.err.println("You gave a wrong argument.\n");
                }
            } else
                System.out.println("Invalid input.\n");
        } else
            System.out.println("You have no friend.\n");
    }

    public void sendInvitation(Profile p) {
        if(!this.friendsList.contains(p)){
            if(this.invitationSendedListSet.add(new Invitation(this, p)) && p.getInvitationListSet().add(new Invitation(this, p)) )
            {
                this.invitationSendedList.add(new Invitation(this, p));
                p.getInvitationList().add(new Invitation(this, p));
                System.out.println("An invitation was sent");
            }
            else
                System.out.println("An invitation was already sent");
        }else
            System.out.println("Already your friend");
    }

    public void sendInvitation(Group g) {
        if(!this.friendsList.contains(g)){
            if(this.invitationSendedListSet.add(new Invitation(this, g.profile)) && g.getInvitationListSet().add(new Invitation(this, g.profile)) )
            {
                this.invitationSendedList.add(new Invitation(this, g.profile));
                g.getInvitationList().add(new Invitation(this, g.profile));
                System.out.println("An invitation was sent");
            }
            else
                System.out.println("An invitation was already sent");
        }else
            System.out.println("Already joined");
    }

    public void acceptInvitation() {
        Scanner sc = new Scanner(System.in);
        while (!this.getInvitationList().isEmpty()) {
            Invitation i = this.invitationList.get(0);
            System.out.println(i.getSender().getSurname() + " "
                    + i.getSender().getName()
                    + " wants to add you on Facebook.");
            System.out.println("a  accept    r  refuse");
            char answer = sc.nextLine().charAt(0);
            if (answer == 'a') {
                if(!this.friendsList.contains(i.sender)){
                    this.getInvitationList().remove(i);
                    i.getSender().getInvitationSendedList().remove(i);
                    this.getFriendsList().add(i.getSender());
                    i.getSender().getFriendsList().add(i.getReceiver());
                    System.out.println(i.sender.getName() + " " + i.sender.getSurname() + " was added to your friend list\n");
                }else
                {
                    this.getInvitationList().remove(i);
                    i.getSender().getInvitationSendedList().remove(i);
                    System.out.println("already one of your friends.\n");
                }
            } else if (answer == 'r') {
                this.getInvitationList().remove(i);
                i.getSender().getInvitationSendedList().remove(i);
                System.out.println(i.sender + " invitation was rejected.\n");
            } else {
                System.out.println("Character unvalid. Try again.\n");
            }
        }
        System.out.println("You have no invitations.\n");
    }

    public void cancelInvitation()
    {
        Scanner sc = new Scanner(System.in);
        int choice;
        if (!this.getInvitationSendedList().isEmpty()) {
            int k = 0;
            for (Invitation i : this.invitationSendedList)
                System.out.println(k + ") " + i);
            System.out.println("Choose an invitation: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                choice = -1;
            }
            if (choice > -1 && choice < this.invitationSendedList.size()) {
                Invitation i = this.invitationSendedList.get(choice);
                i.getReceiver().getInvitationList().remove(i);
                this.invitationSendedList.remove(i);
                System.out.println("Invitation removed.");
            } else
                System.out.println("Invalid input.");
        } else
            System.out.println("There are no invitation.");
    }
    public List<Profile> getFriendSearching(SocialNetwork socialNetwork) {
        System.out.println("entrer nom d'utilisateur cherché");
        Scanner sc = new Scanner(System.in);
        String NomUserSearched = sc.nextLine();



        boolean visited[] = new boolean[socialNetwork.getUsersNumbers()];
        List<Profile> userFound = new ArrayList<>();
        Arrays.fill(visited,false);
        backtrack1(socialNetwork.getRegisteredUsers().values(),NomUserSearched,visited,userFound,false,socialNetwork.getUsersNumbers(),0);
        return userFound;

    }


    public static void backtrack1(Collection<Profile> users,String name,boolean[] visited,List<Profile> userFound,boolean q,int nbrTotal,int index) {

        for (Profile p : users) {
            if (q) break;
            if (!visited[p.getIdUser()])
            {
                visited[p.getIdUser()] = true;
                if (nbrTotal != index)
                {
                    if (p.getName().equals(name))
                    {
                        userFound.add(p);
                    } else {
                        backtrack1(p.getFriendsList(), name, visited, userFound, q, nbrTotal, index++);
                        if (!q)
                        {
                            visited[p.getIdUser()] = false;
                        }
                        }

                } else {
                    q = true;
                }
            }

        }
    }

    public List<ArrayList<Profile>> friendOfFriend( ) {
        ArrayList<ArrayList<Profile>> friendoffriend = new ArrayList<>();
        for (Profile friend : this.getFriendsList())
        {
            friendoffriend.add(friend.getFriendsList());
        }
        return friendoffriend;
    }

    public void serialiser(String path) throws IOException
    {
        String nomFic = path + this.email + ".bin";
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
                nomFic));
        try {
            oos.writeObject(this);
        } catch (IOException ioe) {
            System.err.println("FATAL ERROR -- " + ioe.toString());
        }
        oos.close();
    }
    public Profile deserialiser(String email) throws IOException
    {
        String nomFic = email+".bin";
        Profile tmp = null;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
                nomFic));
        try {
            tmp = (Profile) ois.readObject();
        } catch (IOException ioe) {
            System.err.println("FATAL ERROR -- " + ioe.toString());
        } catch (ClassNotFoundException cnfe) {
            System.err.println("ERROR 'Unknown class' -- " + cnfe.toString());
        }
        ois.close();
        return tmp;
    }

    public int getIdUser() { return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthdate() {
        return birth_date;
    }

    public void setBirthdate(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Profile> getFriendsList() {
        return this.friendsList;
    }

    public void setFriendsList(ArrayList<Profile> friendsList) {
        this.friendsList = friendsList;
    }

    public Wall getPersonalWall() {
        return personalWall;
    }

    public void setPersonalWall(Wall personalWall) {
        this.personalWall = personalWall;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public ArrayList<Invitation> getInvitationList() {
        return invitationList;
    }

    public void setInvitationList(ArrayList<Invitation> invitationList) {
        this.invitationList = invitationList;
    }

    public ArrayList<Invitation> getInvitationSendedList() {
        return invitationSendedList;
    }

    public ArrayList<Page> getPages() {
        return pages;
    }

    public void setLikedPages(ArrayList<Page> likedPages) {
        this.likedPages = likedPages;
    }

    public ArrayList<Message> getLikedPosts() {
        return likedPosts;
    }

    public void setInvitationSendedList(ArrayList<Invitation> invitationSendedList) {
        this.invitationSendedList = invitationSendedList;
    }

    public LinkedHashSet<Invitation> getInvitationListSet() {
        return invitationListSet;
    }

    public LinkedHashSet<Invitation> getInvitationSendedListSet() {
        return invitationSendedListSet;
    }

    public void setLikedPosts(ArrayList<Message> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public void setPages(ArrayList<Page> pages) {
        this.pages = pages;
    }

    public ArrayList<Page> getLikedPages() {
        return likedPages;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public ArrayList<Group> getJoinedGroups() {
        return joinedGroups;
    }

    public void setJoinedGroups(ArrayList<Group> joinedGroups) {
        this.joinedGroups = joinedGroups;
    }
}
