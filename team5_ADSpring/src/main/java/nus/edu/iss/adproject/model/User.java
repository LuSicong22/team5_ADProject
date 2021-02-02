package nus.edu.iss.adproject.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import nus.edu.iss.adproject.NonEntityModel.UserForm;



@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String userName;
	private String password;
	private RoleType role;
	private String email;
	private long points; 
	
	@OneToMany(mappedBy="user")
	private List<Cart> carts;
	
	public User() { }
	public User(String name, String password, RoleType role, String email, long points) {
		this.userName = name;
		this.password = password;
		this.role = role;
		this.email = email;
		this.points = points;
		this.carts = new ArrayList<Cart>();
	}
	
	public User(String name, String password, RoleType role, String email) {
		this.userName = name;
		this.password = password;
		this.role = role;
		this.email = email;
		this.carts = new ArrayList<Cart>();
	}
	
	public User(String userName, String password, RoleType role) {
		super();
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.carts = new ArrayList<Cart>();
	}
	
	public User(UserForm userForm) {
		this.id=userForm.getId();
		this.userName=userForm.getUserName();
		this.password=userForm.getPassword();
		this.role=userForm.getRole();
		this.carts = new ArrayList<Cart>();
	}
	
	
	public long getId() {
		return id;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public RoleType getRole() {
		return role;
	}
	public void setRole(RoleType role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getPoints() {
		return points;
	}
	public void setPoints(long points) {
		this.points = points;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", role=" + role + ", email="
				+ email + ", points=" + points + "]";
	}
	
	
}
