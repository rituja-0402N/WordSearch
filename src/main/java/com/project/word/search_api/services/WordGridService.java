package com.project.word.search_api.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service
//@Scope("prototype")
public class WordGridService {
	
	private int GRID_SIZE;
    private char[][] contents;
    
    private class Coordinate{
    	int x;
    	int y;
    	public Coordinate(int x , int y) {
    		this.x = x;
    		this.y = y;
    	}
    }
    
    private enum Direction{
    	HORIZONTAL,
    	VERTICAL,
    	DIAGONAL,
    	HORIZONTAL_INVERSE,
    	VERTICAL_INVERSE,
    	DIAGONAL_INVERSE
    }
    
   
    public void displayGrid(char[][] contents) {
    	int GRID_SIZE = contents[0].length;
    	for(int i = 0 ; i < GRID_SIZE; i++){
            for(int j = 0; j <GRID_SIZE; j++){
                System.out.print(contents[i][j] + " ");
            }
            System.out.println(" ");
        }
    }
    public char[][] generateGrid(int GRID_SIZE, List<String> words) {
         List<Coordinate> coordinates = new ArrayList<>();
        
	    char[][] contents = new char[GRID_SIZE][GRID_SIZE];
	    for(int i = 0 ; i < GRID_SIZE; i++){
	        for(int j = 0; j <GRID_SIZE; j++){
	        	coordinates.add(new Coordinate(i,j));
	          contents[i][j] = '_';
	        }
	        System.out.println(" ");
	    }
    	 
        for(String word : words){	
        	for(Coordinate coordinate: coordinates) {
        		int x = coordinate.x;
        		int y = coordinate.y;
        		Direction selectedDirection = getDirectionForFit(contents, word,coordinate);
	            if(selectedDirection != null) {
	            	switch(selectedDirection) {
	            	case HORIZONTAL:
	            		for(char c :word.toCharArray()) {
	    		            contents[x][y++]= c;
	    		            }
	    		            break;
	            	case VERTICAL:
	            		for(char c :word.toCharArray()) {
	    		            contents[x++][y]= c;
	    		            }
	    		            break;
	            	case DIAGONAL:
	            		for(char c :word.toCharArray()) {
	    		            contents[x++][y++]= c;
	    		            }
	    		            break;
	            	case HORIZONTAL_INVERSE:
	            		for(char c :word.toCharArray()) {
	    		            contents[x][y--]= c;
	    		            }
	    		            break;
	            	case VERTICAL_INVERSE:
	            		for(char c :word.toCharArray()) {
	    		            contents[x--][y]= c;
	    		            }
	    		            break;
	            	case DIAGONAL_INVERSE:
	            		for(char c :word.toCharArray()) {
	    		            contents[x--][y--]= c;
	    		            }
	    		            break;
	            	}
	            	break;
		            
	        	}
        	
        	}
        }
        randomFillGrid(contents);
        return contents;
    }
    public Direction getDirectionForFit(char[][] contents, String word, Coordinate coordinate) {
    	List<Direction> directions = Arrays.asList(Direction.values());
    	Collections.shuffle(directions);
    	for(Direction direction: directions) {
    		if(doesFit(contents,word,coordinate,direction)) {
    			return direction;
    		}
    	}
    	return null;
    }
    private boolean doesFit(char[][] contents,String word, Coordinate coordinate, Direction direction) {
    	int length = word.length();
    	switch(direction) {
    	
    	case HORIZONTAL: 
    		if(coordinate.y + length > GRID_SIZE)return false;
    		for(int i = 0 ; i< length; i++) {
    			if(contents[coordinate.x][coordinate.y + i] != '_') return false;
    		}
    		break;
    	
    	case VERTICAL :
    		if(coordinate.x + word.length() > GRID_SIZE)return false;
    		for(int i = 0 ; i< length; i++) {
    			if(contents[coordinate.x + i][coordinate.y ] != '_') return false;
    		}
    		break;
    	case DIAGONAL:
    		if(coordinate.x + word.length() > GRID_SIZE||coordinate.y + word.length() > GRID_SIZE)return false;
    		for(int i = 0 ; i< length; i++) {
    			if(contents[coordinate.x + i][coordinate.y + i] != '_') return false;
    		}
    		break;
    	
	    case HORIZONTAL_INVERSE: 
			if(coordinate.y < length )return false;
			for(int i = 0 ; i< length; i++) {
				if(contents[coordinate.x][coordinate.y - i] != '_') return false;
			}
			break;
		
		case VERTICAL_INVERSE :
			if(coordinate.x <length )return false;
			for(int i = 0 ; i< length; i++) {
				if(contents[coordinate.x - i][coordinate.y ] != '_') return false;
			}
			break;
		case DIAGONAL_INVERSE:
			if(coordinate.x < length||coordinate.y < length)return false;
			for(int i = 0 ; i< length; i++) {
				if(contents[coordinate.x - i][coordinate.y - i] != '_') return false;
			}
			break;
		}
	
    
    	return true;
    }
   
    private void randomFillGrid(char[][] contents) {
    	int GRID_SIZE = contents[0].length;
    	String allCharac = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    	
    	for(int i = 0; i < GRID_SIZE; i++) {
    		for(int j = 0; j < GRID_SIZE; j++) {
    			if(contents[i][j] == '_') {
    				int randomIndex = ThreadLocalRandom.current().nextInt(0,allCharac.length()); 
    				contents[i][j]= allCharac.charAt(randomIndex);
    			}
    		}
    	}
    }
    
}
