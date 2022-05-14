import org.testng.annotations.Test;
import requests.GetRequest;
import requests.PostRequest;

public class TMDB {
    @Test
    public void getToken(){
        GetRequest getRequest = new GetRequest();
        getRequest.generateToken();
    }

    @Test
    public void validateToken(){
        PostRequest postRequest = new PostRequest();
        postRequest.validateToken();
    }

    @Test
    public void createSession(){
        PostRequest postRequest = new PostRequest();
        postRequest.createSession();
    }

    @Test
    public void createList(){
        PostRequest postRequest = new PostRequest();
        postRequest.createList("Comedy", "Comedy movie list", "en");
    }

    @Test
    public void detailsList(){
        PostRequest postRequest = new PostRequest();
        String list_id = postRequest.createList("Romance", "Romance movie list", "en");
        GetRequest getRequest = new GetRequest();
        getRequest.detailsList(list_id);
    }

    @Test
    public void addMovie(){
        PostRequest postRequest = new PostRequest();
        String list_id = postRequest.createList("Action", "Action movie list", "en");
        postRequest.addMovie("675353", list_id);
    }

    @Test
    public void clearList(){
        PostRequest postRequest = new PostRequest();
        String list_id = postRequest.createList("thriller", "Thriller movie list", "en");
        postRequest.addMovie("675353", list_id);
        postRequest.clearList(list_id);
    }
}
