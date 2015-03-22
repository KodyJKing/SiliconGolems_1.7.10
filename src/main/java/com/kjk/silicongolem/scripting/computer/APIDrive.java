package com.kjk.silicongolem.scripting.computer;

import java.io.IOException;

import com.kjk.silicongolem.scripting.API;

public class APIDrive extends API{
	
	ComponentDrive drive;
	
	public APIDrive(ComponentDrive drive){
		super(drive.computer);
		this.drive = drive;
	}
	
	public void appendFile(String name, String text) {
		drive.appendFile(name, text);
		try {
			drive.sendAPPEND(name, text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readFile(String name) {
		return drive.readFile(name);
	}
 
	public void clearFile(String name) {
		if(drive.clearFile(name)){
			try {
				drive.sendCLEAR(name);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
