package com.maxel.cursomc;

import com.maxel.cursomc.domain.Categoria;
import com.maxel.cursomc.domain.Cidade;
import com.maxel.cursomc.domain.Estado;
import com.maxel.cursomc.domain.Produto;
import com.maxel.cursomc.repositories.CategoriaRepository;
import com.maxel.cursomc.repositories.CidadeRepository;
import com.maxel.cursomc.repositories.EstadoRepository;
import com.maxel.cursomc.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository CatRepo;
	@Autowired
	private ProdutoRepository ProdRepo;
	@Autowired
	private CidadeRepository Cidrepo;
	@Autowired
	private EstadoRepository EstRepo;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Escritório");
		Categoria cat2 = new Categoria(null, "Informática");

		Produto pro1 = new Produto(null, "Computador", 2000.00);
		Produto pro2 = new Produto(null, "Impressora", 800.00);
		Produto pro3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().add(pro2);
		cat2.getProdutos().addAll(Arrays.asList(pro1, pro2, pro3));

		pro1.getCategorias().add(cat2);
		pro2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		pro3.getCategorias().add(cat2);

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade cid1 = new Cidade(null, "Uberlândia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));

		CatRepo.saveAll(Arrays.asList(cat1, cat2));
		ProdRepo.saveAll(Arrays.asList(pro1, pro2, pro3));
		EstRepo.saveAll(Arrays.asList(est1, est2));
		Cidrepo.saveAll(Arrays.asList(cid1, cid2, cid3));
	}
}
