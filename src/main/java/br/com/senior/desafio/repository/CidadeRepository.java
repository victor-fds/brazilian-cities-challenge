package br.com.senior.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.senior.desafio.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{
	List<Cidade> findByCapital(String capital);
	
	long deleteByIbgeId(String ibgeId);
	
	Cidade findByIbgeId(String ibgeId);
	
	/*---------- QUANTIDADE DISTINCT POR COLUNA ----------*/
	@Query(value = "SELECT COUNT(*) FROM (SELECT DISTINCT uf FROM cidade) AS Quantidade;", nativeQuery = true)
	String selectDistinctByUf();
	
	@Query(value = "SELECT COUNT(*) FROM (SELECT DISTINCT name FROM cidade) AS Quantidade;", nativeQuery = true)
	String selectDistinctByName();
	
	@Query(value = "SELECT COUNT(*) FROM (SELECT DISTINCT capital FROM cidade) AS Quantidade;", nativeQuery = true)
	String selectDistinctByCapital();
	
	@Query(value = "SELECT COUNT(*) FROM (SELECT DISTINCT lon FROM cidade) AS Quantidade;", nativeQuery = true)
	String selectDistinctByLon();
	
	@Query(value = "SELECT COUNT(*) FROM (SELECT DISTINCT lat FROM cidade) AS Quantidade;", nativeQuery = true)
	String selectDistinctByLat();
	
	@Query(value = "SELECT COUNT(*) FROM (SELECT DISTINCT no_accents FROM cidade) AS Quantidade;", nativeQuery = true)
	String selectDistinctByNoAccents();
	
	@Query(value = "SELECT COUNT(*) FROM (SELECT DISTINCT alternative_names FROM cidade) AS Quantidade;", nativeQuery = true)
	String selectDistinctByAlternativeNames();
	
	@Query(value = "SELECT COUNT(*) FROM (SELECT DISTINCT microregion FROM cidade) AS Quantidade;", nativeQuery = true)
	String selectDistinctByMicroregion();
	
	@Query(value = "SELECT COUNT(*) FROM (SELECT DISTINCT mesoregion FROM cidade) AS Quantidade;", nativeQuery = true)
	String selectDistinctByMesoregion();
	
	/*---------- CIDADES POR COLUNA + STRING ----------*/
	@Query(value = "SELECT * FROM cidade WHERE uf LIKE %:uf% ;", nativeQuery = true)
	List<Cidade> findObjectByUf(@Param("uf") String uf);
	
	@Query(value = "SELECT * FROM cidade WHERE name LIKE %:name% ;", nativeQuery = true)
	List<Cidade> findObjectByName(@Param("name") String name);
	
	@Query(value = "SELECT * FROM cidade WHERE capital LIKE %:capital% ;", nativeQuery = true)
	List<Cidade> findObjectByCapital(@Param("capital") String capital);

	@Query(value = "SELECT * FROM cidade WHERE lon LIKE %:lon% ;", nativeQuery = true)
	List<Cidade> findObjectByLon(@Param("lon") String lon);
	
	@Query(value = "SELECT * FROM cidade WHERE lat LIKE %:lat% ;", nativeQuery = true)
	List<Cidade> findObjectByLat(@Param("lat") String lat);
	
	@Query(value = "SELECT * FROM cidade WHERE no_accents LIKE %:no_accents% ;", nativeQuery = true)
	List<Cidade> findObjectByNoAccents(@Param("no_accents") String no_accents);
	
	@Query(value = "SELECT * FROM cidade WHERE alternative_names LIKE %:alternative_names% ;", nativeQuery = true)
	List<Cidade> findObjectByAlternativeNames(@Param("alternative_names") String alternative_names);
	
	@Query(value = "SELECT * FROM cidade WHERE microregion LIKE %:microregion% ;", nativeQuery = true)
	List<Cidade> findObjectByMicroregion(@Param("microregion") String microregion);
	
	@Query(value = "SELECT * FROM cidade WHERE mesoregion LIKE %:mesoregion% ;", nativeQuery = true)
	List<Cidade> findObjectByMesoregion(@Param("mesoregion") String mesoregion);
	
	
	
	@Query(value = "SELECT name FROM cidade WHERE UF = :uf ;", nativeQuery = true)
	List<String> findByUf(@Param("uf") String uf);
	
	@Query(value = "SELECT COUNT(*) FROM cidade WHERE 1;", nativeQuery = true)
	String findTotal();
 
	@Query(value = "SELECT * FROM (SELECT COUNT(*) AS quantidade, cidade.uf FROM cidade GROUP BY cidade.uf ORDER BY quantidade DESC LIMIT 1) AS MaiorQtd;", nativeQuery = true)
	Object findEstadoMaiorQtdCidades();
	 
	@Query(value = "SELECT * FROM (SELECT COUNT(*) AS quantidade, cidade.uf FROM cidade GROUP BY cidade.uf ORDER BY quantidade ASC LIMIT 1) AS MenorQtd;", nativeQuery = true)
	Object findEstadoMenorQtdCidades();
	 
	@Query(value = "SELECT * FROM (SELECT COUNT(*) AS quantidade, cidade.uf FROM cidade GROUP BY cidade.uf ORDER BY quantidade ASC) AS CidadesPorEstado;", nativeQuery = true)
	List<Object> findCidadesPorEstado();
}
