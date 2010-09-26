package ar.com.dailyMarket.model;

public class HourlyBand {
	
	private Long id;
	private String name;
	private Integer initBand;
	private Integer endBand;
	private String description;
	private String detail;
	
	public Long getId() {
		return id;
	}	
	public void setId(Long id) {
		this.id = id;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getInitBand() {
		return initBand;
	}
	public void setInitBand(Integer initBand) {
		this.initBand = initBand;
	}
	public Integer getEndBand() {
		return endBand;
	}
	public void setEndBand(Integer endBand) {
		this.endBand = endBand;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDetail() {
		return name + " " + getInitBand().toString() + "-" + getEndBand().toString();
	}
}
