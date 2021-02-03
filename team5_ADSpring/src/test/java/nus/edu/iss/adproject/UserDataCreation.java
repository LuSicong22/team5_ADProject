package nus.edu.iss.adproject;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import nus.edu.iss.adproject.model.RoleType;
import nus.edu.iss.adproject.model.User;
import nus.edu.iss.adproject.repository.UserRepository;


@SpringBootTest
public class UserDataCreation {
	
	@Autowired
	UserRepository urepo;
	
	@Test
	public void adduser() {
		urepo.save(new User("attraction","attraction",RoleType.ATTRACTIONMANAGER));
		urepo.save(new User("admin","admin",RoleType.HOTELMANAGER));
		urepo.save(new User("hotel","hotel",RoleType.HOTELMANAGER));
		urepo.save(new User("admin3","admin",RoleType.ATTRACTIONMANAGER));
		
		
//		ArrayList<User> user =new ArrayList<User>();
//		user =(ArrayList<User>) urepo.findAll();
//		for (Iterator<User> iterator = user.iterator(); iterator.hasNext();) {
//			User user2 = (User) iterator.next();
//			System.out.println(user2.toString());
//		}
	}

	
}
