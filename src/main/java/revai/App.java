package revai;

public class App {

    public static void main(String[] args){
        String token = "02WdOCSYsffXJk_zFMUjrsPvNDxf7lHQdHaExvU1N3rbuC15Gn2n2c1naP0B--zb7JiY_B0E0kwUWQmQuXAmCiDRH4o1k";
        String version = "v1";
        ApiClient client = new ApiClient(token, version);
        try{
            System.out.println(client.getAccount().displayInfo());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
