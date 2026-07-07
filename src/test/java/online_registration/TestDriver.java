package online_registration;

public class TestDriver {
	public static void main(String[] args) {
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("Driver loaded!");
		} catch (Exception e) {
		e.printStackTrace();
		}
	}
}

