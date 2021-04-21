import java.io.Serializable;
import java.util.Date;

public class PageGroup implements Serializable {
    private static final long serialVersionUID = 3646937831314970316L;
    protected Date date;
    protected String genre;
    protected Profile Creator;

    public PageGroup(String genre, Profile Creator) {
        this.date = new Date();
        this.genre = genre;
        this.Creator = Creator;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


    public Profile getCreator() {
        return Creator;
    }

    public void setCreator(Profile creator) {
        Creator = creator;
    }
}
