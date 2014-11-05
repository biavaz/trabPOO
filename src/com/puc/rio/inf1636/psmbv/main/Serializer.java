package com.puc.rio.inf1636.psmbv.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * Serializer Class
 * 
 * This class handles serialized files. It is generic, so anything given here 
 * can be serialized and deserialized. (If the given object implements Serializable 
 * of course)
 * 
 * @author Patrick Sava
 * @since March 20th, 2014
 * 
 * @version 1.0
 * 
 * @see Serializable
 * @see FileOutputStream
 * @see FileInputStream
 * 
 */
public class Serializer {
	/**
	 * File writer
	 */
	private FileOutputStream fos;
	
	/**
	 * File reader
	 */
	private FileInputStream fis;
	
	/**
	 * The file itself
	 */
	private File fileSession;
	
	/**
	 * File Session name
	 */
	private String sessionFileName;
	
	
	/**
	 * Base constructor
	 * @param fileName -> name of the file that will be saved or retrieved
	 */
	public Serializer(String fileName){
		
		this.sessionFileName = fileName+ ".ser";
	    
    	fileSession = new File(sessionFileName);
    	if(!fileSession.exists())
			try {
				fileSession.createNewFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
	}

	/**
	 * Opens the FileOutputStream for the file
	 */
	private void openWrite(){
		try {
			fos = new FileOutputStream(fileSession);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Opens the FileInputStream for the file
	 */
	private void openRead() {
		try {
			fis = new FileInputStream(fileSession);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Closes the FileOutputStream for the file
	 */
	private void closeWrite(){
		try {
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Closes the FileInputStream for the file
	 */
	private void closeRead(){
		try {
			fis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Serializes an Serializable Object and saves it as a file.ser
	 * @param object -> Any object that can be serialized
	 */
	public void writeObjectOnSession(Serializable object){
		openWrite();
		try {
			ObjectOutputStream objectSerializer = new ObjectOutputStream(fos);
			objectSerializer.writeObject(object);
			objectSerializer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		closeWrite();
	}
	
	/**
	 * Reads a file and creates a new object based on the serialized data
	 * @return Serializable object
	 */
	public Serializable getObjectOnSession(){
		Object obj = null;
		openRead();
		try{
			ObjectInputStream objectReader = new ObjectInputStream(fis);
			obj = objectReader.readObject();
			objectReader.close();
		} catch(IOException ex) {
			ex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		closeRead();
		return (Serializable)obj;
	}

	/**
	 * Clears the data inside the serialized file.
	 */
	public void deleteObjectOnSession(){
		openWrite();
		closeWrite();
	}
}
