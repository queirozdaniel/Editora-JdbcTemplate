package com.danielqueiroz.editora;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.danielqueiroz.editora.dao.AutorDAO;
import com.danielqueiroz.editora.dao.EditoraDAO;
import com.danielqueiroz.editora.dao.EnderecoDAO;
import com.danielqueiroz.editora.dao.LivroAutorDAO;
import com.danielqueiroz.editora.dao.LivroDAO;
import com.danielqueiroz.editora.model.Autor;
import com.danielqueiroz.editora.model.Editora;
import com.danielqueiroz.editora.model.Endereco;
import com.danielqueiroz.editora.model.Livro;
import com.danielqueiroz.editora.model.LivroAutor;

@SpringBootApplication
public class EditoraApplication implements CommandLineRunner {

	@Autowired
	private EditoraDAO editoraDAO;
	@Autowired
	private AutorDAO autorDAO;
	@Autowired
	private LivroDAO livroDAO;
	@Autowired
	private LivroAutorDAO livroAutorDAO;
	@Autowired
	private EnderecoDAO enderecoDAO;

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
		// findEditorasWithAutoresTest();

		// insertAutor();
		// findAllAutores();
		// findAutoresByEditora();
		// updateAutor();
		// deleteAutor();

		// insertLivro();
		// findLivroWithAutores();
		// findAutorWithLivros();
		// findLivroByEdicao();
		// findLivrosByPaginas();
		// updateLivro();
		// alterLivro();
		// findLivroByTituloAndEdicao();

		// procedureUppercaseTituloLivro();
		// procedureInfoLivro();
		// functionTotalDeLivrosPorAutor();
		// insertBatchEditoras();
		// updateBatchAutores();
		// deleteBatchAutores();
		// findAllEnderecos();

		System.out.println("\n\n<<<<< END RUNNER >>>>>");
	}

	private void findAllEnderecos() {

		List<Endereco> enderecos = enderecoDAO.findAll();
		enderecos.forEach(s -> {
			System.out.println(s);
		});
	}

	private void deleteBatchAutores() {
		List<Integer> ids_autores = Arrays.asList(9, 8);

		autorDAO.deleteBatch(ids_autores);
	}

	private void updateBatchAutores() {
		Autor a1 = autorDAO.finById(5);
		a1.setNome("Juliana Queiroz");

		Autor a2 = autorDAO.finById(4);
		a2.setNome("Cezar Santos Souza");

		List<Autor> autores = Arrays.asList(a1, a2);

		autorDAO.updateBatch(autores);

	}

	private void insertBatchEditoras() {

		Editora editora = new Editora("Editora Vida", "Porto Alegre", "vida@ed-gmail.com.br");
		Editora editora2 = new Editora("Editora Novos Costumes LTDA", "Sao Paulo", "novas@gmail.com.br");

		List<Editora> editoras = Arrays.asList(editora, editora2);

		// editoraDAO.saveBatch(editoras);
		editoraDAO.insertBatch(editoras);
	}

	private void functionTotalDeLivrosPorAutor() {
		String texto = livroDAO.callFunctionTotalLivrosByAutor(1);
		System.out.println(texto);
	}

	private void procedureInfoLivro() {

		List<String> infos = livroDAO.callProcedureInfoLivro(1);
		infos.forEach(System.out::println);

	}

	private void procedureUppercaseTituloLivro() {

		Map<String, Object> map = livroDAO.callProcedureUppercaseTitulo(1);

		for (Map.Entry<String, Object> kv : map.entrySet()) {
			System.out.println(kv.getKey() + " : " + kv.getValue());
		}
	}

	private void findLivroByTituloAndEdicao() {
		Livro livro = livroDAO.findByTituloAndEdicao("Aprenda JavaEE 8", 2);
		System.out.println(livro);
	}

	private void alterLivro() {
		Livro livro = livroDAO.findLivroWithAutores(1);
		System.out.println(livro);

		livro.setTitulo("Aprenda JavaEE 8");
		livro.setEdicao(2);
		livro.setPaginas(453);

		int ok = livroDAO.alter(livro);

		if (ok == 1) {
			System.out.println("Operação foi um sucesso!");

			livro = livroDAO.findLivroWithAutores(1);
			System.out.println(livro);
		} else {
			System.out.println("Deu merda");
		}
	}

	private void updateLivro() {

		Livro livro = livroDAO.findLivroWithAutores(1);
		System.out.println(livro);

		int ok = livroDAO.update(livro, " Aprenda Java EE", 1, 420);

		if (ok == 1) {
			System.out.println("Operação foi um sucesso!");

			livro = livroDAO.findLivroWithAutores(1);
			System.out.println(livro);
		} else {
			System.out.println("Deu merda");
		}

	}

	private void findLivrosByPaginas() {

		List<Livro> livros = livroDAO.findLivrosbyPaginas(100, 300);
		livros.forEach(System.out::println);

	}

	private void findLivroByEdicao() {

		List<Livro> livros = livroDAO.findByEdicao(1);
		livros.forEach(System.out::println);

	}

	private void findAutorWithLivros() {

		Autor autor = autorDAO.findAutorWithLivros(1);
		System.out.println(autor);

		autor.getLivros().forEach(System.out::println);

	}

	private void findLivroWithAutores() {

		Livro livro = livroDAO.findLivroWithAutores(1);

		System.out.println(livro);

		livro.getAutores().forEach(System.out::println);

	}

	private void insertLivro() {

		Livro jse = new Livro();
		jse.setTitulo("Empreendedorismo e tecnologia");
		jse.setPaginas(312);
		jse.setEdicao(1);

		String[] autores = { "Daniel Queiroz" };

		jse = livroDAO.save(jse);

		Integer idLivro = jse.getId();

		for (String string : autores) {
			Integer idAutor = autorDAO.getIdByNome(string);

			livroAutorDAO.save(new LivroAutor(idLivro, idAutor));
		}

	}

	private void findEditorasWithAutoresTest() {

		Editora editora = editoraDAO.findEditoraWithAutoresPaginados(3, 0, 2);

		System.out.printf("\n %s, %s, %s\n", editora.getRazaoSocial(), editora.getCidade(), editora.getEmail());

		editora.getAutores().forEach(System.out::println);

		System.out.println("---------------------------------");

		editora = editoraDAO.findEditoraWithAutoresPaginados(3, 1, 2);
		editora.getAutores().forEach(System.out::println);

		System.out.println("---------------------------------");

		editora = editoraDAO.findEditoraWithAutoresPaginados(3, 2, 2);
		editora.getAutores().forEach(System.out::println);

	}

	private void deleteAutor() {

		int i = autorDAO.delete(2);

		if (i == 1) {
			System.out.println("Autor deletado \n ");
		} else {
			System.out.println("Erro no delete");
		}

	}

	private void updateAutor() {
		Autor autor = autorDAO.finById(1);

		Editora editora = new Editora();
		editora.setId(3);
		autor.setEditora(editora);

		System.out.println(autor);

		int i = autorDAO.update(autor);

		if (i == 1) {
			Autor atualizado = autorDAO.finById(1);
			System.out.println("Autor atualizado\n " + atualizado);
		} else {
			System.out.println("Erro na atualização");
		}
	}

	private void findAutoresByEditora() {
		List<Autor> autores = autorDAO.findAutoresByEditora("Stm-Eletronics");
		autores.forEach(System.out::println);
	}

	private void findAllAutores() {
		List<Autor> autores = autorDAO.findAll();
		autores.forEach(System.out::println);
	}

	private void insertAutor() {
		Editora editora = editoraDAO.findById(3);

		Autor autor = new Autor("Marcos Castro", "marcos_mat@gmail.com", editora);
		Autor autor2 = new Autor("Josef Roth", "roth@gmail.com", editora);

		if (autor.getId() == null) {
			autor = autorDAO.save(autor);
			autor2 = autorDAO.save(autor2);
			System.out.println("Salvandor...");
		}

		if (autor.getId() != null) {
			System.out.println(autor);
		}

	}

	private void deleteEditora() {
		int i = editoraDAO.delete(2);

		if (i == 1) {
			System.out.println("Operação realizada com sucesso! \n ");
		} else {
			System.out.println("Erro na operação!");
		}
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
