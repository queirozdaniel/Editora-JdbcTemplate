package com.danielqueiroz.editora;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.danielqueiroz.editora.dao.AutorDAO;
import com.danielqueiroz.editora.dao.EditoraDAO;
import com.danielqueiroz.editora.model.Autor;
import com.danielqueiroz.editora.model.Editora;

@SpringBootApplication
public class EditoraApplication implements CommandLineRunner {

	@Autowired
	private EditoraDAO editoraDAO;
	@Autowired
	private AutorDAO autorDAO;

	public static void main(String[] args) {
		SpringApplication.run(EditoraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("<<<<< RUNNER >>>>>\n\n");

		// insertEditora();
		// findAllEditoras();
		// findByIdEditoras();
		// findByCidadesEditoras();
		// findByRazaoSocialEditora();
		// countEditoras();
		// findEmailByIdEditora();
		// findCidadeAndEmailByIdEditora();
		// updateEditora();
		// deleteEditora();
		// insertAutor();

		findAllAutores();

		System.out.println("\n\n<<<<< END RUNNER >>>>>");
	}

	private void findAllAutores() {
		List<Autor> autores = autorDAO.findAll();
		autores.forEach(System.out::println);
	}

	private void insertAutor() {
		Editora editora = editoraDAO.findById(2);

		Autor autor = new Autor("Luciana da Silva", "luciana@gmail.com", editora);

		if (autor.getId() == null) {
			autor = autorDAO.save(autor);
			System.out.println("Salvandor...");
		}

		if (autor.getId() != null) {
			System.out.println(autor);
		}

	}

	private void deleteEditora() {
		int i = editoraDAO.delete(4);

		List<Editora> editoras = editoraDAO.findAll();
		editoras.forEach(System.out::println);
	}

	private void updateEditora() {
		Editora editora = editoraDAO.findById(2);
		System.out.println("Editora: " + editora);

		int i = editoraDAO.update(editora, "Nova Editora LTDA", "Londrina", "nova@ed.com");
		System.out.println("<<<<Resultado " + i + ">>>>");

		Editora editoraNova = editoraDAO.findById(2);
		System.out.println("Editora: " + editoraNova);

	}

	private void findCidadeAndEmailByIdEditora() {
		List<String> lista = editoraDAO.findCidadeAndEmailById(3);
		lista.forEach(System.out::println);

		Editora editora = editoraDAO.findCidadeAndEmailForId(3);
		System.out.println("Editora: " + editora);

		List<Editora> editoras = editoraDAO.findCidadesAndEmails();
		editoras.forEach(System.out::println);

	}

	private void findEmailByIdEditora() {

		List<String> emails = editoraDAO.findEmails();
		String email = editoraDAO.findEmailById(2);
		System.out.println("Email: " + email);
		emails.forEach(System.out::println);

	}

	private void countEditoras() {
		int count = editoraDAO.count();
		System.out.println("Count: " + count);
	}

	private void findByRazaoSocialEditora() {
		List<Editora> editorasEncontradas = editoraDAO.findByRazaoSocial("Editora");
		editorasEncontradas.forEach(System.out::println);

	}

	private void findByCidadesEditoras() {

		List<Editora> editorasEncontradas = editoraDAO.findByCidade("Londrina", "Obidos");
		editorasEncontradas.forEach(System.out::println);

	}

	private void findByIdEditoras() {
		Editora editora = editoraDAO.findById(1);
		System.out.println("Editora : " + editora);
	}

	private void findAllEditoras() {

		List<Editora> editoras = editoraDAO.findAll();
		editoras.forEach(System.out::println);

	}

	private void insertEditora() {
		Editora editora = new Editora("Editora Winandy", "Londrina", "gail-edi@gmail.com");

		int resultado = editoraDAO.insert(editora);

		// int resultado = editoraDAO.save(editora);

		// editora = editoraDAO.add(editora);
		// editora = editoraDAO.add(editora2);

		System.out.println("Resultado: " + resultado);
	}

}
