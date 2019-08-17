package pablo.myexample.memorizeverses;

public class CardItem {

    private String verse, location;

    public CardItem(){}

    public CardItem(String verse, String location){
        this.verse = verse;
        this.location = location;
    }

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
