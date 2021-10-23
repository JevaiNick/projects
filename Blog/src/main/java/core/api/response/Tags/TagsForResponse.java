package core.api.response.Tags;

public class TagsForResponse {
    public TagsForResponse(String name, String weight) {
        this.name = name;
        this.weight = weight;
    }

    String name;
    String weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
