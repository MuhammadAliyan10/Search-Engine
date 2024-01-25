package Frontend;

public class Ja {
    public static void Test(String email) {
        
     
        if (email.contains("@gmail.com")) {
            System.out.println("Email is valid");
        } else {
            System.out.println("Email is not valid");
        }

    }

    public static void main(String[] args) {

        String email = "aliyannadeem10@gmail.com";
        Test(email);

    }
}
