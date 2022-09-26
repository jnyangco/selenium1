package temp;

public class TestCar {

	public static void main(String[] args) {
		
		//Inheritance (extends)
		MarutiCar m = new MarutiCar();//child class - inherits from the parent
		m.start();
		m.refuel();
		m.musicSystem();
		
		Car c = new Car();//parent class
		c.start();
		c.refuel();
	}

}
