package com.topica.edu.itlab.jdbc.tutorial.excercise1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.topica.edu.itlab.jdbc.tutorial.annotation.Column;
import com.topica.edu.itlab.jdbc.tutorial.annotation.OneToMany;
import com.topica.edu.itlab.jdbc.tutorial.annotation.Table;
import com.topica.edu.itlab.jdbc.tutorial.entity.ClassEntity;
import com.topica.edu.itlab.jdbc.tutorial.entity.StudentEntity;

public class LazyLoader {
	
	/*
	 * load list of ClassEntity
	 * 
	 * @return list of ClassEntity without load student list property
	 */
	public List<ClassEntity> load() {

		List<ClassEntity> classList = new ArrayList<ClassEntity>();
		
		String tableName = null;
		String idField = null;
		String nameField = null;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = DBConnection.getConnection(DBConnection.URL, DBConnection.USER_NAME,
				DBConnection.PASSWORD);
		try {
			tableName = ClassEntity.class.getDeclaredAnnotation(Table.class).name();
			idField = ClassEntity.class.getDeclaredField("id").getAnnotation(Column.class).name();
			nameField = ClassEntity.class.getDeclaredField("name").getAnnotation(Column.class).name();

			String sql = "select * from " + tableName;
			
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ClassEntity classEntity = new ClassEntity();
				classEntity.setId(Long.valueOf(resultSet.getInt(idField)));
				classEntity.setName(resultSet.getString(nameField));

				classList.add(classEntity);
			}
			
			return classList;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.close(resultSet, preparedStatement, connection);
		}

		return null;
	}

	/*
	 * read and display list ClassEntity
	 * 
	 * @param classList list of ClassEntity
	 */
	public void read(List<ClassEntity> classList) {

		for (ClassEntity classEntity : classList) {
			if (classEntity.getListStudent() == null) {
				classEntity.setListStudent(getListStudent(classEntity.getId()));

				System.out.println("########");
				for (StudentEntity studentEntity : classEntity.getListStudent()) {
					System.out.println(studentEntity.getId() + " " + studentEntity.getName());
				}
			}
		}

	}

	
	/*
	 * load student list when ClassEntity is read
	 * 
	 * @param classId the class id of ClassEntity
	 * @return list of student of a class entity
	 */
	private List<StudentEntity> getListStudent(Long classId) {
		// TODO Auto-generated method stub

		List<StudentEntity> studentList = new ArrayList<StudentEntity>();

		// DB field
		String tableName = null;
		String idField = null;
		String nameField = null;

		Connection connection = DBConnection.getConnection(DBConnection.URL, DBConnection.USER_NAME,
				DBConnection.PASSWORD);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			
			// get DB field of each attribute
			tableName = ClassEntity.class.getDeclaredField("listStudent").getAnnotation(OneToMany.class).mappedBy();
			idField = StudentEntity.class.getDeclaredField("id").getAnnotation(Column.class).name();
			nameField = StudentEntity.class.getDeclaredField("name").getAnnotation(Column.class).name();
			
			String sql = "SELECT S." + idField + ", S." + nameField + " FROM " + tableName
					+ " AS S RIGHT JOIN class AS C ON S.class_id = C.id " + "WHERE class_id = " + classId;

			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				StudentEntity studentEntity = new StudentEntity();

				studentEntity.setId(Long.valueOf(resultSet.getInt(idField)));
				studentEntity.setName(resultSet.getString(nameField));

				studentList.add(studentEntity);

			}
			
			return studentList;
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.close(resultSet, preparedStatement, connection);
		}
		return studentList;

	}

}
