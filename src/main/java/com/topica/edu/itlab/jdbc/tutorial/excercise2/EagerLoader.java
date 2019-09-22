package com.topica.edu.itlab.jdbc.tutorial.excercise2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.topica.edu.itlab.jdbc.tutorial.entity.ClassEntity;
import com.topica.edu.itlab.jdbc.tutorial.entity.StudentEntity;
import com.topica.edu.itlab.jdbc.tutorial.excercise1.DBConnection;

public class EagerLoader {
	
	/*
	 * get list of ClassEntity
	 * 
	 * @return list of ClassEntity
	 */
	public List<ClassEntity> getAllClass() {

		Connection connection = DBConnection.getConnection(DBConnection.URL, DBConnection.USER_NAME,
				DBConnection.PASSWORD);
		
		// list of ClassEntity to return
		List<ClassEntity> classList = new ArrayList<ClassEntity>();
		
		// set of classId
		Set<Long> classIdSet = new HashSet<Long>();

		StringBuilder builder = new StringBuilder();
		builder.append("Select c.id as class_id, c.name as class_name,s.id as student_id, s.name as student_name ")
				.append("from class c, student s ").append("where c.id = s.class_id");
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
				preparedStatement = connection.prepareStatement(builder.toString());
				resultSet = preparedStatement.executeQuery();
				List<StudentEntity> studentList = null;
				ClassEntity classEntity = null;
				while (resultSet.next()) {
					Long classId = (long) resultSet.getInt("class_id");
					String className = resultSet.getString("class_name");
					Long studentId = (long) resultSet.getInt("student_id");
					String studentName = resultSet.getString("student_name");
					
					StudentEntity studentEntity = new StudentEntity();
					studentEntity.setId(studentId);
					studentEntity.setName(studentName);
					
					
					
					if(classIdSet.contains(classId)) {
						
						for (ClassEntity entity : classList) {
							if(entity.getId() == classId) {
								entity.getListStudent().add(studentEntity);
							}
						}
						
						
					} else {
						
						classIdSet.add(classId);
						
						classEntity = new ClassEntity();
						classEntity.setId(classId);
						classEntity.setName(className);
						
						studentList = new ArrayList<StudentEntity>();
						studentList.add(studentEntity);
						
						classEntity.setListStudent(studentList);
						classList.add(classEntity);
					}
					
				}
				
				return classList;

			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			DBConnection.close(resultSet, preparedStatement, connection);

		}

		return classList;

	}

	
}
