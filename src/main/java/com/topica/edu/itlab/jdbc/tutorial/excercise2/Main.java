package com.topica.edu.itlab.jdbc.tutorial.excercise2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.topica.edu.itlab.jdbc.tutorial.entity.ClassEntity;
import com.topica.edu.itlab.jdbc.tutorial.entity.StudentEntity;
import com.topica.edu.itlab.jdbc.tutorial.excercise1.DBConnection;

public class Main {
	
	public static void main(String[] args) {
		
		EagerLoader eagerLoader = new EagerLoader();
		List<ClassEntity> classList = eagerLoader.getAllClass();
		
		for (ClassEntity classEntity : classList) {
			System.out.println("####");
			System.out.println("Class id: " + classEntity.getId() + ", Class name: " + classEntity.getName() );
			
			System.out.println("List student of class: ");
			for (StudentEntity studentEntity : classEntity.getListStudent()) {
				System.out.println(studentEntity.getId() + " ," + studentEntity.getName());
			}
		}
		
	}
}
