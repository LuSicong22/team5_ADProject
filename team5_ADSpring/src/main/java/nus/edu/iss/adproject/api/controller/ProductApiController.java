package nus.edu.iss.adproject.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nus.edu.iss.adproject.model.Attraction;
import nus.edu.iss.adproject.model.Booking;
import nus.edu.iss.adproject.model.BookingDetails;
import nus.edu.iss.adproject.model.Hotel;
import nus.edu.iss.adproject.model.Product;
import nus.edu.iss.adproject.model.User;
import nus.edu.iss.adproject.nonEntityModel.ProductType;
import nus.edu.iss.adproject.nonEntityModel.ProductWrapper;
import nus.edu.iss.adproject.service.AttractionService;
import nus.edu.iss.adproject.service.BookingService;
import nus.edu.iss.adproject.service.HotelService;
import nus.edu.iss.adproject.service.ProductService;
import nus.edu.iss.adproject.service.RoomTypeService;
import nus.edu.iss.adproject.service.SessionService;
import nus.edu.iss.adproject.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api/product")
public class ProductApiController {
	
	@Autowired
	private ProductService pservice;
	
	@Autowired
	private BookingService bookService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionService session_svc;
	
	@Autowired
	private AttractionService aservice;
	
	@Autowired
	private HotelService hotelservice;
	
	@Autowired
	private RoomTypeService roomService;
	
	// i think need to split into 2 parts ?? cannot call
	@PostMapping("/")
	public ResponseEntity<ProductWrapper> listProductForm(Model model, @Param("keyword") String keyword, HttpSession session) {
		List<Product> listproducts = pservice.listAllSearchAttractions(keyword);
		List<Product> listhotels = pservice.listAllSearchHotels(keyword);
		listproducts.addAll(listhotels);
		model.addAttribute("product", listproducts);
		model.addAttribute("keyword", keyword); 
		if (session_svc.isNotLoggedIn(session) == false) {
			User user = (User) session.getAttribute("user");
			Set<Product> userRecommendation = retrieveRecommendationByUser(userService.findById(user.getId()));
			model.addAttribute("userRecommendation", userRecommendation);
		}
		model.addAttribute("hotSellers",retrieveHotSellingProducts());
		
		return new ResponseEntity<ProductWrapper>(new ProductWrapper(listproducts) ,HttpStatus.OK);
	}
	
//	http://localhost:8080/api/product/product/detail/5
	@GetMapping("/product/detail/{id}")
	public <T> ResponseEntity<T> viewProductDetail(Model model, @PathVariable("id")Long id) {
		Product product = pservice.findProductById(id);
		model.addAttribute("product", product);
		if(product.getType().equals(ProductType.ATTRACTION)) {
			Attraction attraction = aservice.findAttractionByProductId(id);
			//model.addAttribute("attraction", attraction);
			return new ResponseEntity<T> ( (T)attraction,HttpStatus.OK);
		}else {
			Hotel hotel = hotelservice.findHotelByProductId(id);
			model.addAttribute("hotel", hotel);
			model.addAttribute("roomType", roomService.findRoomTypesByHotelId(hotel.getId()));
			return new ResponseEntity<T> ((T) hotel,HttpStatus.OK);
		}
	}
	
	
	public Set<Product> retrieveRecommendationByUser(User user){
		//retrieve 5 latest bookings of the user
		List<Booking>bookings = bookService.findLatestBookingsByUser(user);
		int i = 0;
		Map<Product, Integer> map = new HashMap<>();
		//Only get the latest 5 items bought
		for (Booking booking: bookings) {
			for (BookingDetails bk: booking.getBookingDetails()) {
				if (map.get(bk.getProduct())==null) {
					map.put(bk.getProduct(), 1);
					i += 1;
				}
				if (i == 5) {
					break;
				}
			}
			if (i == 5) {
				break;
			}
		}
		return map.keySet();
	}
	
	public List<Product> retrieveHotSellingProducts(){
		//Retrieve top 5 hot selling items for the past 1 month (Appear in the most bookings)
		List<Booking> bookings = bookService.findPastOneMonthBookings();
		//Loop through every booking details and count items by product id
		Map<Product, Integer> map = new HashMap<>();
		for (Booking booking: bookings) {
			System.out.println(booking.getBookingDate());
			for (BookingDetails bk: booking.getBookingDetails()) {
				if (map.get(bk.getProduct())==null) {
					map.put(bk.getProduct(), 1);
				}else {
					map.put(bk.getProduct(), map.get(bk.getProduct())+1);
				}
			}
		}
		//get the top 5 largest integer in the map
		List<Product> hotSellers = new ArrayList<Product>();
		int i = 0;
		while (map.isEmpty()==false && i < 5)
		{
		    Map.Entry<Product, Integer> maxEntry = null;

			for (Map.Entry<Product, Integer> entry : map.entrySet())
			{
			    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
			    {
			        maxEntry = entry;
			    }
			}
			hotSellers.add(maxEntry.getKey());
			map.remove(maxEntry.getKey());
			i+=1;
		}
		return hotSellers;
	}


}
