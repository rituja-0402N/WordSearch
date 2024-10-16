package com.project.word.search_api.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.word.search_api.services.WordGridService;

@RestController("/")
public class WordSearchController {
	
	@Autowired
	WordGridService wordGridService;
	
	@GetMapping("/wordgrid")
	public String createWordGrid(@RequestParam int gridSize, @RequestParam String words) {
		List<String> word = Arrays.asList(words.split(","));
	
		char[][] grid = wordGridService.generateGrid(gridSize,word);
		String gridToString ="";
		gridSize = grid[0].length;
    	for(int i = 0 ; i < gridSize; i++){
            for(int j = 0; j <gridSize; j++){
            	gridToString += grid[i][j] + " ";
            }
            gridToString += "\r\n";
        }
		return gridToString;
		
	}

}
