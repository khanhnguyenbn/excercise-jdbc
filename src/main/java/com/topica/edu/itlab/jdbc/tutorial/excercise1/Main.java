package com.topica.edu.itlab.jdbc.tutorial.excercise1;

import java.util.List;

import com.topica.edu.itlab.jdbc.tutorial.entity.ClassEntity;

public class Main {
	
	public static void main(String[] args) {
		
		LazyLoader lazyLoader = new LazyLoader();
		
		List<ClassEntity> classList = lazyLoader.load();
		
		lazyLoader.read(classList);
		
		
		
		
	}

}
