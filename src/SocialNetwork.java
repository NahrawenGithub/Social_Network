import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class SocialNetwork implements Serializable
{
    private static final long serialVersionUID = 902665912334739241L;
    protected HashMap<String, Profile> registeredUsers;
    protected ArrayList<Page> registeredPages;
    protected ArrayList<Group> registeredGroups;
    protected Profile currentUser;

    public SocialNetwork() {
        this.registeredUsers = new HashMap<String, Profile>();
        this.registeredPages = new ArrayList<Page>();
        this.registeredGroups = new ArrayList<Group>();
        this.currentUser = null;
    }

    public SocialNetwork(HashMap<String, Profile> list) {
        this.registeredUsers = list;
    }

    public void DisplayRegisteredUsers()
    {
        if (!registeredUsers.isEmpty()) {
            int i = 0;
            for (Profile p : registeredUsers.values())
            {
                System.out.println(p);
            }
        }
        else
            System.out.println("No session recorded.");
    }

    public void friendOfFriend(Profile p, int depth, HashSet<Profile> alreadySeen, String tab)
    {
        if(depth<0)
            return;
        else {
            int i=0;
            for(Profile friend : p.getFriendsList())
            {
                System.out.println(tab+i+++") "+friend.getName()+" "+friend.getSurname());
                if(!alreadySeen.contains(friend))
                {
                    alreadySeen.add(friend);
                    friendOfFriend(friend, depth-1, alreadySeen, tab+"\t");
                }
            }
        }
    }

    public int getUsersNumbers()
    {
        return this.registeredUsers.size();
    }

    public void createAccount()
    {
        // ask the user for all the informations
        Scanner sc = new Scanner(System.in);
        System.out.println("To create a new account, enter the following informations:");
        System.out.println("Name: ");
        String name = sc.nextLine();
        System.out.println("Surname: ");
        String surname = sc.nextLine();
        System.out.println("Email: ");
        String email = sc.nextLine();
        System.out.println("Password: ");
        String password = sc.nextLine();
        System.out.println("Date of Birth (mm-dd-yyyy): ");
        String birth_date = sc.nextLine();
        System.out.println("City: ");
        String city = sc.nextLine();
        Profile newProfile = new Profile(name, surname, password, birth_date,city, email, Profile.id = registeredUsers.size() );

        // check if the person is already on the network
        if (registeredUsers.get(email) == null)
        {
            registeredUsers.put(email, newProfile);
            System.out.println("Account created with success!");
        }
        else
            System.out.println("The user is already registered");
    }

    public void createPage(Profile user)
    {
        // ask the user for all the informations
        Scanner sc = new Scanner(System.in);
        System.out.println("To create a new page, enter the following informations:");
        System.out.println("Name: ");
        String pageName = sc.nextLine();
        System.out.println("genre: ");
        String genre = sc.nextLine();

        // check if the page is already in your pages list
        Page page = new Page(genre,user, pageName , Page.id);
        if (user.pages.contains(page))
        {
            System.out.println("The Page already exists in your pages list");
        }
        else {
            registeredPages.add(page);
            user.pages.add(page);
            System.out.println("Page created with success!");
        }

    }

    public void createGroup(Profile user)
    {
        // ask the user for all the informations
        Scanner sc = new Scanner(System.in);
        System.out.println("To create a new group, enter the following informations:");
        System.out.println("Name: ");
        String groupName = sc.nextLine();
        System.out.println("genre: ");
        String genre = sc.nextLine();

        // check if the page is already on your groups list
        Group group = new Group(genre,user, groupName , Group.id);
        if (user.groups.contains(group))
        {
            System.out.println("The Group already exists in your groups list");
        }
        else
            {
            registeredGroups.add(group);
            user.groups.add(group);
            group.getMembersList().add(user);
            System.out.println("Group created with success!");
            }
    }

    public void Login(String email_in, String password_in) {
        Profile tmp = registeredUsers.get(email_in);
        if (tmp == null)
            System.out.println("Incorrect email.");
        else if (!tmp.getPassword().equals(password_in))
            System.out.println("Incorrect password.");
        else {
            System.out.println("User identified.");
            System.out.println("Welcome, " + tmp.getSurname() + "!");
            currentUser = tmp;
        }
    }

    public Profile getCurrentUser()
    {
        return currentUser;
    }

    public HashMap<String, Profile> getRegisteredUsers() {
        return registeredUsers;
    }

    public ArrayList<Page> getRegisteredPages() {
        return registeredPages;
    }

    public ArrayList<Group> getRegisteredGroups() {
        return registeredGroups;
    }

    public void serialiser() throws IOException {
        String nomFic = "facebook.bin";
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomFic));
        try {
            oos.writeObject(this);
        } catch (IOException ioe) {
            System.err.println("FATAL ERROR -- "+ ioe.toString());
        }
        oos.close();
    }

    public SocialNetwork deserialiser() throws IOException
    {
        String nomFic = "facebook.bin";
        SocialNetwork tmp = null;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomFic));
        try
        {
            tmp = (SocialNetwork) ois.readObject();
        } catch (IOException ioe)
        {
            System.err.println("ERROR -- " + ioe.toString());
        } catch (ClassNotFoundException cnfe)
        {
            System.err
                    .println("ERROR 'Unknown class' -- " + cnfe.toString());
        }
        ois.close();
        return tmp;
    }

    public void setCurrentUser(Profile currentUser) {
        this.currentUser = currentUser;
    }


    public Set<Profile> suggererAmi(Profile membre) {
       // reuperation de mes amis, mes pages liker
        ArrayList<Profile> mesAmies = membre.getFriendsList();
        ArrayList<Page> mesPageLiker = membre.getLikedPages();
        //recupération de tout les membres
        int occu = 0;
        Set<Profile> aSuggerer = new HashSet<Profile>();

        for (Profile m : registeredUsers.values() ) {
            if (!(m.equals(membre)) && !(mesAmies.contains(m))){
                     for (int j = 0; j < mesPageLiker.size(); j++) {
                             for (int j2 = 0 ; j2 < m.getLikedPages().size() ; j2 ++ )
                             {
                                 if (mesPageLiker.get(j).getIdPage() == m.getLikedPages().get(j2).getIdPage())
                                 {
                                     occu++;
                                     break;
                                 }
                            }
                    }
                        if (occu != 0 ) {
                            //accepter ce membre
                            aSuggerer.add(m);
                            occu = 0;
                        }
                    }
        }
        return aSuggerer;
}
    public static ArrayList<Profile> calculateShortestPathFromCurrentUser(ArrayList<Profile> registeredUsers, Profile currentUser  )
    {
        int[] distance = new  int[registeredUsers.size()];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[currentUser.getIdUser()] = 0;
        Set<Profile> settledNodes = new HashSet<>();
        Set<Profile> unsettledNodes = new HashSet<>();

        unsettledNodes.add(currentUser);
        while (unsettledNodes.size() != 0)
        {
            Profile currentNode = getLowestDistanceNode(unsettledNodes, distance);
            unsettledNodes.remove(currentNode);
            for (Profile adjacencyPair: currentNode.getFriendsList())
            {
                Profile adjacentNode = adjacencyPair;
                Integer edgeWeight = 1;
                if (!settledNodes.contains(adjacentNode))
                {
                    CalculateMinimumDistance(adjacentNode, edgeWeight, currentNode, distance);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return registeredUsers;
    }
    private static Profile getLowestDistanceNode(Set < Profile > unsettledNodes,int[] distance) {
        Profile lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for ( Profile p : unsettledNodes)
        {
            int nodeDistance = distance[p.getIdUser()];
            if (nodeDistance < lowestDistance)
            {
                lowestDistance = nodeDistance;
                lowestDistanceNode = p;
            }
        }
        return lowestDistanceNode;
    }

    private static void CalculateMinimumDistance(Profile evaluationNode, Integer edgeWeigh, Profile sourceNode, int[] distance) {
        Integer sourceDistance = distance[sourceNode.getIdUser()];
        if (sourceDistance + edgeWeigh < distance[evaluationNode.getIdUser()])
        {
            distance[evaluationNode.getIdUser()] = sourceDistance + edgeWeigh;
            if (sourceNode.getShortestPath() == null)
            {
                sourceNode.setShortestPath(new LinkedList<Profile>());
            }
            LinkedList<Profile> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }
}

