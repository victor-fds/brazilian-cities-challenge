package br.com.senior.desafio.controller;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.senior.desafio.model.Cidade;
import br.com.senior.desafio.repository.CidadeRepository;
import io.swagger.annotations.ApiOperation;

@RestController
public class CidadeController {
	
	private double calcDistancias(String lat, String lon, String latD, String lonD) {
		double latRad = Math.toRadians(Double.parseDouble(lat));
		double lonRad = Math.toRadians(Double.parseDouble(lon));
		
		
		double latRadD = Math.toRadians(Double.parseDouble(latD));
		double lonRadD = Math.toRadians(Double.parseDouble(lonD));

		
		double delta = lonRadD - lonRad;
		
		return Math.acos(Math.cos(latRadD) * Math.cos(latRadD)
		* Math.cos(delta) + Math.sin(latRad)
		* Math.sin(latRadD))
		* (6371);
	}
	
	@Autowired
	private CidadeRepository cidadeRepo;
	
	@ApiOperation(value = "Executa a instalação do cidades_db.csv para o banco de dados MySQL, é necessário configurar o caminho do arquivo .csv na classe CidadeController.java")
	@RequestMapping(value = "/install", method = RequestMethod.GET, produces="text/plain")
	public String iniciarImportacao() {
		try {
			Scanner in = new Scanner(new FileReader("/cidades_db.csv"));
			
			if(in.hasNextLine())
				in.nextLine(); // ignora a linha do cabeçalho
			
			while(in.hasNextLine()) {
			    String line = in.nextLine();
			    String[] csvSplit = line.split(",");
			    
			    Cidade cidade = new Cidade(csvSplit[0], csvSplit[1], csvSplit[2], csvSplit[3], csvSplit[4], csvSplit[5], csvSplit[6], csvSplit[7], csvSplit[8], csvSplit[9]);
			    cidadeRepo.save(cidade);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Importação terminada";
	}
	
	@ApiOperation(value = "Retorna todas as cidades que são capitais")
	@RequestMapping(value = "/cidades/capitais", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<Cidade>> retornaCidades(){
		List<Cidade> result = cidadeRepo.findByCapital("true");
		
		result.sort(Comparator.comparing(Cidade::getName));
		
		return new ResponseEntity<List<Cidade>>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retorna o nome dos estados com a maior qtd e menor de cidades")
	@RequestMapping(value = "/cidades/quantidade", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<Object>> retornaQuantidade(){
		List<Object> result = new ArrayList<Object>();
		result.add(cidadeRepo.findEstadoMaiorQtdCidades());
		result.add(cidadeRepo.findEstadoMenorQtdCidades());
		
		return new ResponseEntity<List<Object>>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retorna a quantidade total de cidades no banco de dados")
	@RequestMapping(value = "/cidades/quantidade/total", method = RequestMethod.GET, produces="text/plain")
	public ResponseEntity<String> retornaTotal(){
		try {	
			String total = cidadeRepo.findTotal();
			
			return new ResponseEntity<String>("Mensagem: Quantidade de registros total é #" +total, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>("Mensagem: Nenhum registro encontrado" + e.toString(), HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Retorna a quantidade total de cidades, separada por estado")
	@RequestMapping(value = "/cidades/quantidade/estado", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<Object>> retornaQuantidadePorEstado(){
		List<Object> result = cidadeRepo.findCidadesPorEstado();
		
		return new ResponseEntity<List<Object>>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retorna a quantidade de resultados diferentes na coluna selecionada")
	@RequestMapping(value = "/cidades/quantidade/{coluna}", method = RequestMethod.GET, produces="text/plain")
	public ResponseEntity<String> retornaQuantidadePorColuna(@PathVariable(value = "coluna") String coluna){
		String quantidade = "0";
		try {			
			switch(coluna) {
				case "uf":
					quantidade = cidadeRepo.selectDistinctByUf(); break;
				case "name":
					quantidade = cidadeRepo.selectDistinctByName(); break;
				case "capital":
					quantidade = cidadeRepo.selectDistinctByCapital(); break;
				case "alternative_names":
					quantidade = cidadeRepo.selectDistinctByAlternativeNames(); break;
				case "lat":
					quantidade = cidadeRepo.selectDistinctByLat(); break;
				case "lon":
					quantidade = cidadeRepo.selectDistinctByLon(); break;
				case "mesoregion":
					quantidade = cidadeRepo.selectDistinctByMesoregion(); break;
				case "microregion":
					quantidade = cidadeRepo.selectDistinctByMicroregion(); break;
				case "no_accents":
					quantidade = cidadeRepo.selectDistinctByNoAccents(); break;
			}
			
			return new ResponseEntity<String>("Mensagem: Quantidade de registros encontrada para "+coluna+" foi #" +quantidade, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>("Mensagem: Nenhum registro encontrado" + e.toString(), HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Retorna todos os objetos que contenham o valor informado na coluna selecionada")
	@RequestMapping(value = "/cidades/coluna/{coluna}/{valor}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<Cidade>> retornaQuantidadeColunaValor(@PathVariable(value = "coluna") String coluna, @PathVariable(value = "valor") String valor){
		List<Cidade> cidade = new ArrayList<Cidade>();
		try {			
			switch(coluna) {
				case "uf":
					cidade = cidadeRepo.findObjectByUf(valor); break;
				case "name":
					cidade = cidadeRepo.findObjectByName(valor); break;
				case "capital":
					cidade = cidadeRepo.findObjectByCapital(valor); break;
				case "alternative_names":
					cidade = cidadeRepo.findObjectByAlternativeNames(valor); break;
				case "lat":
					cidade = cidadeRepo.findObjectByLat(valor); break;
				case "lon":
					cidade = cidadeRepo.findObjectByLon(valor); break;
				case "mesoregion":
					cidade = cidadeRepo.findObjectByMesoregion(valor); break;
				case "microregion":
					cidade = cidadeRepo.findObjectByMicroregion(valor); break;
				case "no_accents":
					cidade = cidadeRepo.findObjectByNoAccents(valor); break;
			}
			
			return new ResponseEntity<List<Cidade>>(cidade, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<List<Cidade>>(cidade, HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Retorna a cidade com o ibgeId informado")
	@RequestMapping(value = "/cidades/ibgeId/{ibgeId}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<Cidade> retornaDadosCidade(@PathVariable(value = "ibgeId") String ibgeId){
		Cidade result = cidadeRepo.findByIbgeId(ibgeId);
		
		return new ResponseEntity<Cidade>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retorna uma lista com o nome das cidades do estado selecionado")
	@RequestMapping(value = "/cidades/uf/{uf}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<String>> retornaCidadesEstado(@PathVariable(value = "uf") String uf){
		List<String> result = cidadeRepo.findByUf(uf);
		return new ResponseEntity<List<String>>(result, HttpStatus.OK);
	}	
	
	@ApiOperation(value = "Insere uma nova cidade no banco de dados")
	@RequestMapping(value = "/cidades/inserir", method = RequestMethod.POST, produces="text/plain")
	public ResponseEntity<String> inserirNovaCidade(@RequestBody Cidade cidade){
		try {
			cidadeRepo.save(cidade);
			return new ResponseEntity<String>("Mensagem: Cadastrada com sucesso no banco de dados!", HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<String>("Mensagem: Erro ao cadastrar, preencha todos os campos!", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		}
	}
	
	@ApiOperation(value = "Deleta a cidade com o ibgeId mencionado")
	@RequestMapping(value = "/cidades/deletar/{ibgeId}", method = RequestMethod.DELETE, produces="text/plain")
	public ResponseEntity<String> deletarCidade(@PathVariable(value = "ibgeId") String ibgeId){
		try {
			Cidade cidadeDeletar = cidadeRepo.findByIbgeId(ibgeId);
			
			if(cidadeDeletar != null)
				cidadeRepo.delete(cidadeDeletar);
		
			return new ResponseEntity<String>("Mensagem: Cidade com IbgeID: "+ibgeId+" deletada do banco de dados!", HttpStatus.OK);
			
		} catch(Exception e) {
			return new ResponseEntity<String>("Mensagem: Erro ao deletar, verifique se o ibgeID está correto!", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		}
	}
	
	@ApiOperation(value = "Retorna as cidades com a maior distância calculada (alto custo computacional, necessário realizar cache ou melhorar o alg)")
	@RequestMapping(value = "/cidades/maisDistantes", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<Cidade>> distanciaEntreCidades(){
		List<Cidade> cidadesResult = new ArrayList<Cidade>();
		
		try {
			List<Cidade> cidades = cidadeRepo.findAll();
			Cidade inicial = null;
			Cidade destino = null;
			double distAnt =-1;
			
			for(Cidade inic : cidades) {
				for(Cidade dest: cidades) {
					if(!inic.equals(destino)) {
						double dist = calcDistancias(inic.getLat(), inic.getLon(), dest.getLat(), dest.getLon());
						
						if(dist > distAnt) {
							distAnt = dist;
							inicial = inic;
							destino = dest;
						}
					
					}
				}
			}
			
			cidadesResult.add(inicial);
			cidadesResult.add(destino);
			
			return new ResponseEntity<List<Cidade>>(cidadesResult, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<List<Cidade>>(cidadesResult, HttpStatus.NOT_FOUND);
		}
	}
	
}
