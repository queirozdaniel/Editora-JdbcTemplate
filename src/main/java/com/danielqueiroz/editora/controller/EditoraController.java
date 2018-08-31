package com.danielqueiroz.editora.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.editora.dao.EditoraDAO;
import com.danielqueiroz.editora.model.Editora;

@RestController
public class EditoraController {

	@Autowired
	private EditoraDAO editoraDAO;
	
	@RequestMapping("/")
	public String hello() {
		return "Hello World!";
	}
	
	@RequestMapping("/editoras")
	public List<Editora> findEditoras(){
		return editoraDAO.findAll();
	}
	
}
