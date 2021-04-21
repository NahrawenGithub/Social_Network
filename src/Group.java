import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class Group extends PageGroup {

    protected String Name;
    protected Wall groupWall;
    protected LinkedHashSet<Invitation> invitationListSet;
    protected ArrayList<Invitation> invitationList;
    protected ArrayList<Profile> MembersList;
    protected Profile profile;

    protected  int idGroup;
    public static int id;

    public Group (String genre, Profile Creator, String Name , int idGroup) {
        super(genre, Creator);
        this.idGroup = idGroup;
        this.Name = Name;
        this.groupWall = new Wall();
        this.date = new Date();
        this.invitationList = new ArrayList<Invitation>();
        this.invitationListSet=new LinkedHashSet<Invitation>();
        this.MembersList = new ArrayList<Profile>();
        this.profile = new Profile();
        id++;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public String toString()
    {
        return "This group is named : " + this.Name + " and it's created by: " + this.Creator.name +
                " Number of Members: "+this.MembersList.size() +"\n";
    }

    public void displayMembers() {
        for (int i = 0; i < this.MembersList.size(); i++)
            System.out.println(i + ") " + this.MembersList.get(i).getSurname()
                    + " " + this.MembersList.get(i).getName());
    }

    public void acceptInvitation() {
        Scanner sc = new Scanner(System.in);
        while (!this.getInvitationList().isEmpty()) {
            Invitation i = this.invitationList.get(0);
            System.out.println(i.getSender().getSurname() + " "
                    + i.getSender().getName()
                    + " wants to join this Group.");
            System.out.println("a  accept    r  refuse");
            char answer = sc.nextLine().charAt(0);
            if (answer == 'a') {
                if(!this.MembersList.contains(i.sender)){
                    this.getMembersList().add(i.getSender());
                    i.getSender().getJoinedGroups().add(this);
                    this.getInvitationList().remove(i);
                    i.getSender().getInvitationSendedList().remove(i);
                    System.out.println(i.sender.getName() + " " + i.sender.getSurname()  + "was added to your members list");
                }else
                    System.out.println("already one of your members");
            } else if (answer == 'r') {
                this.getInvitationList().remove(i);
                i.getSender().getInvitationSendedList().remove(i);
            } else {
                System.out.println("Character unvalid. Try again.");
            }
        }
        System.out.println("You have no new member requests.\n");
    }

    public void displayWall() {
        System.out.println(this.getGroupWall().toString());
        this.getGroupWall().displayChoicesGC();
        Scanner sc = new Scanner(System.in);
        int choice=sc.nextInt();
        switch (choice){
            case 0:
                break;
            case 1:
                this.profile.showMessagesToLike(this.getGroupWall());
                getCreator().likePost(this.getGroupWall());
                break;
            default:
                System.err.println("You gave a wrong argument.\n");
        }
    }

    public ArrayList<Invitation> getInvitationList() {
        return this.invitationList;
    }

    public LinkedHashSet<Invitation> getInvitationListSet() {
        return invitationListSet;
    }

    public void setInvitationList(ArrayList<Invitation> invitationList) {
        this.invitationList = invitationList;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Wall getGroupWall() {
        return groupWall;
    }

    public void setGroupWall(Wall GroupWall) {
        this.groupWall = groupWall;
    }

    public ArrayList<Profile> getMembersList() {
        return MembersList;
    }

    public void setMembersList(ArrayList<Profile> membersList) {
        MembersList = membersList;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

}
