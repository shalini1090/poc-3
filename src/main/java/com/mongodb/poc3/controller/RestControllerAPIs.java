package com.mongodb.poc3.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.poc3.creator.BuysheetCreator;
import com.mongodb.poc3.model.Buysheet;

@RestController
@RequestMapping("/api")
public class RestControllerAPIs {

	@Autowired
	GridFsOperations gridOperations;
	
	// this variable is used to store ImageId for other actions like: findOne or delete
	private String imageFileId = "";

	@GetMapping("/save")
	public String saveFiles() throws IOException {
		// Define metaData
		DBObject metaData = new BasicDBObject();
		metaData.put("organization", "JavaSampleApproach");
		
		/**
		 * 1. save an image file to MongoDB
		 */
		
		// Get input file
//		InputStream iamgeStream = new FileInputStream("D:\\JSA\\jsa-logo.png");
//		
//		metaData.put("type", "image");
//		
//		// Store file to MongoDB
//		imageFileId = gridOperations.store(iamgeStream, "jsa-logo.png", "image/png", metaData).getId().toString();
//		System.out.println("ImageFileId = " + imageFileId);

		/**
		 * 2. save object to MongoDB
		 */
		
		// change metaData
		metaData.put("type", "data");

		BuysheetCreator bc = new BuysheetCreator();
		Buysheet bs = bc.create();
		
		 ByteArrayOutputStream boas = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(boas);
		try {
			oos.writeObject(bs);
			oos.close();
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InputStream is = new ByteArrayInputStream(boas.toByteArray());
		
		// Store inputstream to MongoDb
		long start = System.currentTimeMillis();
		
		imageFileId =	gridOperations.store(is, "document", metaData).getId().toString();
		long end = System.currentTimeMillis();
		System.out.println("Time1:"+(end-start));
		
	
		//gridOperations.store(new FileInputStream("D:\\JSA\\text-2.txt"), "text-2.txt", "text/plain", metaData);

		return "Done saving in "+(end-start)+ " miliseconds";
	}
	
	@GetMapping("/retrieve/imagefile")
	public String retrieveImageFile() throws IOException, ClassNotFoundException{
		// read file from MongoDB
		long start = System.currentTimeMillis();
		GridFSDBFile imageFile = gridOperations.findOne(new Query(Criteria.where("_id").is(imageFileId)));
		long end = System.currentTimeMillis();
		
		System.out.println("Time1:"+(end-start));
		
		InputStream is = imageFile.getInputStream();
		
		ObjectInputStream in = new ObjectInputStream(is);
		Buysheet o=  (Buysheet)in.readObject();
	    
		System.out.println(o.getRows().parallelStream().collect(Collectors.toList()).size());
		//System.out.println(o.toString());
//	    BufferedReader   br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//	        
//	        br.lines().forEach(l->System.out.println("++++++++++   "+l));
		
		System.out.println("Image File Name:" + imageFile.getFilename());
		
		return "Done retrievig in "+(end-start)+ " miliseconds";
	}
	
	@GetMapping("/retrieve/textfiles")
	public String retrieveTextFiles(){
		/**
		 * get all data files then save to local disk
		 */
		
		// Retrieve all data files
		
		long start = System.currentTimeMillis();
		List<GridFSDBFile> textFiles = gridOperations.find(new Query(Criteria.where("metadata.type").is("data")));
		long end = System.currentTimeMillis();
		System.out.println("Time1:"+(end-start));
	
		// Save all back to local disk
		textFiles.forEach(file->{
			
			try {
				
				String fileName = file.getFilename();
				
				file.writeTo("C:\\Users\\shalini.kumari\\Downloads\\New folder\\"+ fileName);
				
				
				
				System.out.println("Text File Name: " + fileName);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		return "Done retrievig in "+(end-start)+ " miliseconds";
	}
	
	@GetMapping("/delete/image")
	public String deleteFile(){
		// delete image via id
		gridOperations.delete(new Query(Criteria.where("_id").is(imageFileId)));
		
		return "Done";
	}
}
