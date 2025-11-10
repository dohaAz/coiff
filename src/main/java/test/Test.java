package test;
import java.sql.Connection;
import dao.Factory;

public class Test {

	public static void main(String[] args) {
        try {
            Factory.getInstance();
            Connection connexion = Factory.getConnection();
            if (connexion != null) {
                System.out.println("Connexion réussie à la base de données !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
