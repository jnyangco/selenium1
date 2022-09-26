package temp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class ReadingProperties {

	public static void main(String[] args) {
		String path = System.getProperty("user.dir")+"//src//test//resources//project.properties";
		System.out.println(path);
		
		Properties prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(path);
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.println(prop.getProperty("url"));
		System.out.println(prop.getProperty("platform"));
		
	}

}
