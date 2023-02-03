package com.casestudy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.model.BookingModel;
import com.casestudy.repository.BookingRepository;


@RestController
@CrossOrigin(origins="*")
@RequestMapping("/booking")
public class BookingController {
	
	@Autowired
	private BookingRepository bookingrepo;
	
	//Adding Logger 
	Logger logger = LoggerFactory.getLogger(BookingController.class);
	
	
	
	//Rest API to add Booking details by Id & fare
	@PostMapping("/bookticket/{userId}/{fare}")
	public int bookticket(@PathVariable String userId, @PathVariable int fare, @RequestBody BookingModel book)
	{
		book.setUserId(userId);
		List<BookingModel> ticketslist = bookingrepo.findByUserId(book.getUserId());
		
		//logger implementation
        logger.info("[bookticket/id/fare] info message added");
        logger.debug("[bookticket/id/fare] debug message added");
		
		book.setFare(fare*book.getTotalseats());
		int totalseats=0;
		for(int i=0;i<ticketslist.size();i++)
		{
			totalseats += ticketslist.get(i).getTotalseats();
		}
		if(totalseats+book.getTotalseats()<=6)
		{
			bookingrepo.save(book);
			return book.getTotalseats();
		}
		else {
			return 0;
		}
	}
	
	
	//Rest API to get all Booking details
	@GetMapping("/getallorders")
	public List<BookingModel> getAllOrders(){
		
		//logger implementation
        logger.info("[getallorders] info message added");
        logger.debug("[getallorders] debug message added");
        
		return bookingrepo.findAll();
	}
	
	
	//Rest API to get Booking by Id
	@GetMapping("/getorder/{userId}")
	public List<BookingModel> getorder(@PathVariable String userId){
		
		//logger implementation
        logger.info("[getorder/id] info message added");
        logger.debug("[getorder/id] debug message added");
        
		return bookingrepo.findByUserId(userId);
	}
	
	
	//Rest API to get Booking bt pnrId
	@GetMapping("/getorderpnr/{pnrId}")
	public BookingModel getorderpnr(@PathVariable String pnrId){
		
		//logger implementation
        logger.info("[getorderpnr/pnrId] info message added");
        logger.debug("[getorderpnr/pnrId] debug message added");
        
		return bookingrepo.findByPnrId(pnrId);
	}
	
	
	//Rest API to delete Booking details by Id
	@DeleteMapping("/cancelticket/{pnrId}")
	public String cancelticket(@PathVariable String pnrId){
		bookingrepo.deleteById(pnrId);
		
		//logger implementation
        logger.info("[cancelticket/pnrId] info message added");
        logger.debug("[cancelticket/pnrId] debug message added");
        
		return "Train Ticket with "+pnrId+" cancelled Succesfully";
	}
	
}
