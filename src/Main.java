import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class Main {

    public static  void main(String[] args) {

        String email_in, password_in;
        int choice,choice2,choice3,choice4,choice5,choice6,choice7, choice9;
        Scanner sc = new Scanner(System.in);
        SocialNetwork facebook = new SocialNetwork();
        try {
            facebook = facebook.deserialiser();
        } catch (IOException e1) {
            System.err.println("Loading error.");
        }
        System.out.println("Welcome to our App. There are currently "+facebook.getUsersNumbers()+" user(s)");
        do{
            System.out.println("What do you want to do?");
            System.out.println("1 Create account");
            System.out.println("2 Login");
            System.out.println("3 see the list of accounts");
            System.out.println("4 Exit");
            try{
                choice = Integer.parseInt(sc.nextLine());
            }catch(Exception e){
                choice=-1;
            }
            switch(choice) {
                case(1):
                    facebook.createAccount();
                    break;
                case(2):
                    System.out.println("Please enter your email and your password:");
                    System.out.println("Email? ");
                    email_in = sc.nextLine();
                    System.out.println("Password?");
                    password_in = sc.nextLine();
                    facebook.Login(email_in,password_in);
                    if(facebook.getCurrentUser()!=null){
                        do{
                            System.out.println("1 Post a message on your wall");
                            System.out.println("2 Post a message on a friend's wall");
                            System.out.println("3 See your informations");
                            System.out.println("4 See a friend's profile");
                            System.out.println("5 Send an invitation");
                            System.out.println("6 Cancel a sent invitation");
                            System.out.println("7 See a friend's wall");//
                            System.out.println("8 See the friends of your friend");//
                            System.out.println("9 Friends suggestions");
                            System.out.println("10 Create Page or Group");
                            System.out.println("11 Manage Pages/Groups");
                            System.out.println("12 see Pages/Groups");
                            System.out.println("13 search");

                            System.out.println("14 Log out");

                            try{
                                choice2 = Integer.parseInt(sc.nextLine());
                            }catch(Exception e){
                                choice2=-1;
                            }
                            switch(choice2) {
                                case 1:
                                    facebook.getCurrentUser().writeMessage(facebook.getCurrentUser());
                                    break;
                                case 2:
                                    facebook.getCurrentUser().writeMessageToFriend();
                                    break;
                                case 3:
                                    System.out.println("1 See your personal information");
                                    System.out.println("2 See the list of your friends");
                                    System.out.println("3 View your invitation list");
                                    System.out.println("4 See your wall");
                                    System.out.println("5 See the list of liked pages");
                                    System.out.println("6 See the list of joined Groups");

                                    try{
                                        choice3 = Integer.parseInt(sc.nextLine());
                                    }catch(Exception e){
                                        choice3=-1;
                                    }
                                    switch(choice3) {
                                        case 1:
                                            facebook.getCurrentUser().displayProfile();
                                            break;
                                        case 2:
                                            facebook.getCurrentUser().displayFriends();
                                            break;
                                        case 3:
                                            facebook.getCurrentUser().acceptInvitation();
                                            break;
                                        case 4:
                                            facebook.getCurrentUser().displayWall();
                                            break;
                                        case 5:
                                            ArrayList<Page> likedPages=facebook.getCurrentUser().getLikedPages();
                                            if (likedPages.isEmpty()){
                                                System.out.println("No pages found.");
                                            }
                                            else
                                                facebook.getCurrentUser().displayLikedPages(facebook.getCurrentUser().getLikedPages());
                                            break;
                                        case 6:
                                            ArrayList<Group> joinedGroups=facebook.getCurrentUser().getJoinedGroups();
                                            if (joinedGroups.isEmpty()){
                                                System.out.println("No groups found.");
                                            }
                                            else
                                                facebook.getCurrentUser().displayJoinedGroups(facebook.getCurrentUser().getJoinedGroups());
                                            break;
                                    }
                                    break;
                                case 4:
                                    facebook.getCurrentUser().displayFriendProfile();
                                    break;
                                case 5:
                                    System.out.println("Enter the email for this invitation");
                                    Profile p=facebook.getRegisteredUsers().get(sc.nextLine());
                                    if(p!=null){
                                        facebook.getCurrentUser().sendInvitation(p);
                                    }else
                                        System.out.println("Doesn't exist");
                                    break;
                                case 6:
                                    facebook.getCurrentUser().cancelInvitation();
                                    break;
                                case 7:
                                    facebook.getCurrentUser().displayFriendWall();
                                    break;
                                case 8:
                                    System.out.println("Enter max depth:");
                                    try{
                                        choice4 = Integer.parseInt(sc.nextLine());
                                    }catch(Exception e){
                                        choice4=-1;
                                    }
                                    facebook.friendOfFriend(facebook.getCurrentUser(), choice4, new HashSet<Profile>(), "");
                                    break;
                                case 9:
                                    System.out.println("1 suggestions par nbre des amis");
                                    System.out.println("2 suggestions par nbre des pages en communes");
                                    try{
                                        choice9 = Integer.parseInt(sc.nextLine());
                                    }catch(Exception e){
                                        choice9=-1;
                                    }
                                    switch (choice9)
                                    {
                                        case 1:
                                            p = facebook.getCurrentUser();
                                            for(Profile initShortestPath : facebook.registeredUsers.values().stream().collect(
                                                    Collectors.toCollection(ArrayList::new)))
                                            {
                                                initShortestPath.setShortestPath(new LinkedList<>());
                                            }

                                            ArrayList<Profile> suggererAmibyamis = facebook.calculateShortestPathFromCurrentUser( facebook.registeredUsers.values().stream().collect(
                                                    Collectors.toCollection(ArrayList::new)),p );
//                                    System.out.println(suggererAmi);
                                            for(Profile pro: suggererAmibyamis)
                                            {
                                                if ( pro.getShortestPath() != null && pro.getShortestPath().size() > 1)
                                                {
                                                    System.out.println("Profile:"+pro);
                                                    System.out.println("ShortestPath"+pro.getShortestPath());
                                                }
                                            }  break;
                                        case 2:
                                            p = facebook.getCurrentUser();
                                           Set<Profile> suggestion= facebook.suggererAmi(p);
                                            System.out.println(suggestion);
                                            break;
                                        default:
                                            break;
                                    }
                                    break;


                                case 10:
                                    System.out.println("1 Create Page");
                                    System.out.println("2 Create Group");
                                    try{
                                        choice5 = Integer.parseInt(sc.nextLine());
                                    }catch(Exception e){
                                        choice5=-1;
                                    }
                                    switch (choice5)
                                    {
                                        case 1:
                                            facebook.createPage(facebook.getCurrentUser());
                                            break;
                                        case 2:
                                            facebook.createGroup(facebook.getCurrentUser());
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case 11:
                                    System.out.println("1 manage Pages");
                                    System.out.println("2 manage Groups");
                                    try{
                                        choice6 = Integer.parseInt(sc.nextLine());
                                    }catch(Exception e){
                                        choice6=-1;
                                    }
                                    switch (choice6)
                                    {
                                        case 1:
                                            ArrayList<Page> pages=facebook.getCurrentUser().getPages();
                                            facebook.getCurrentUser().displayManageablePages(pages);
                                            break;
                                        case 2:
                                            ArrayList<Group> groups=facebook.getCurrentUser().getGroups();
                                            facebook.getCurrentUser().displayManageableGroups(groups);
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case 12:
                                    System.out.println("1 See Pages");
                                    System.out.println("2 See Groups");
                                    try{
                                        choice7 = Integer.parseInt(sc.nextLine());
                                    }catch(Exception e){
                                        choice7=-1;
                                    }
                                    switch (choice7)
                                    {
                                        case 1:
                                            ArrayList<Page> registeredPages=facebook.getRegisteredPages();
                                            facebook.getCurrentUser().displayPagesToLike(registeredPages);
                                            break;
                                        case 2:
                                            ArrayList<Group> registeredGroups=facebook.getRegisteredGroups();
                                            facebook.getCurrentUser().displayGroupsToJoin(registeredGroups);
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case 13:
                                    List<Profile> searched = facebook.getCurrentUser().getFriendSearching(facebook);
                                    System.out.println(searched);
                                default:
                                    break;

                            }
                        }while(choice2!=14);
                        facebook.setCurrentUser(null);
                    }
                    break;
                case(3):
                    facebook.DisplayRegisteredUsers();
                    break;
                default:
                    break;
            }
        }
        while(choice!=4);
        try {
            facebook.serialiser();
        } catch (IOException e) {
            System.err.println("Error when saving");
        }
    }
}
