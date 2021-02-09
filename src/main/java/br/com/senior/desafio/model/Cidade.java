package br.com.senior.desafio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Cidade {

	@ApiModelProperty(value = "Identificação primary key do MySQL")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ApiModelProperty(value = "Código do ibge")
	@Column(name="ibge_id", nullable = false)
	private String ibgeId;
	
	@ApiModelProperty(value = "Estado")
	@Column(nullable = false)
	private String uf;
	
	@ApiModelProperty(value = "Nome da cidade")
	@Column(nullable = false)
	private String name;
	
	@ApiModelProperty(value = "Se a cidade é uma capital (boolean não-convertido)")
	@Column
	private String capital;
	
	@ApiModelProperty(value = "Longitude (double)")
	@Column(nullable = false)
	private String lon;
	
	@ApiModelProperty(value = "Latitude (double)")
	@Column(nullable = false)
	private String lat;
	
	@ApiModelProperty(value = "Nome sem acentos")
	@Column(name="no_accents")
	private String noAccents;
	
	@ApiModelProperty(value = "Nome alternativo")
	@Column(name="alternative_names")
	private String alternativeNames;
	
	@ApiModelProperty(value = "Nome da microregião")
	@Column(nullable = false)
	private String microregion;
	
	@ApiModelProperty(value = "Nome da macroregião")
	@Column(nullable = false)
	private String mesoregion;
	

	public Cidade(String ibgeId, String uf, String name, String capital, String lon, String lat, String noAccents,
			String alternativeNames, String microregion, String mesoregion) {
		super();
		this.ibgeId = ibgeId;
		this.uf = uf;
		this.name = name;
		this.capital = capital;
		this.lon = lon;
		this.lat = lat;
		this.noAccents = noAccents;
		this.alternativeNames = alternativeNames;
		this.microregion = microregion;
		this.mesoregion = mesoregion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIbgeId() {
		return ibgeId;
	}

	public void setIbgeId(String ibgeId) {
		this.ibgeId = ibgeId;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getNoAccents() {
		return noAccents;
	}

	public void setNoAccents(String noAccents) {
		this.noAccents = noAccents;
	}

	public String getAlternativeNames() {
		return alternativeNames;
	}

	public void setAlternativeNames(String alternativeNames) {
		this.alternativeNames = alternativeNames;
	}

	public String getMicroregion() {
		return microregion;
	}

	public void setMicroregion(String microregion) {
		this.microregion = microregion;
	}

	public String getMesoregion() {
		return mesoregion;
	}

	public void setMesoregion(String mesoregion) {
		this.mesoregion = mesoregion;
	}
	
	
}
