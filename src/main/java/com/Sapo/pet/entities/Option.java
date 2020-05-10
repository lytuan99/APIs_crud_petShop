package com.Sapo.pet.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "options")
public class Option extends BaseEntity implements Serializable{

	@Column(name = "name", length = 45)
	private String name;
	
	@Column(name = "value", length = 45)
	private String value;
	@Column(name = "position", length = 4)
	private Integer position;
	
	@Column(name = "created_on")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	
	@Column(name = "modified_on")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date modifiedOn;
	
	@Column(name = "status")
	private boolean status;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "id_variant")
//	private Variant variant;
	@Column(name = "id_variant")
	private Integer idVariant;

//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "option")
//	private List<OptionValue> optionValues;

	
	public void  setOptionDefault(Option option, Date date, Integer idVariant) {
		option.setName("Default"); 
		option.setValue("Default Title");
		option.setPosition(1); 
		option.setCreatedOn(date); 
		option.setModifiedOn(date); 
		option.setStatus(true); 
		option.setIdVariant(idVariant); 
	}

	public void  setOption(Option option, String name, String value, Integer position, Date date,Integer idVariant  ) {
		option.setName(name); 
		option.setValue(value);
		option.setPosition(position); 
		option.setCreatedOn(date); 
		option.setModifiedOn(date); 
		option.setStatus(true); 
		option.setIdVariant(idVariant); 
	
	}
	public Integer getPosition() {
		return position;
	}

	public Integer getIdVariant() {
		return idVariant;
	}

	public void setIdVariant(Integer idVariant) {
		this.idVariant = idVariant;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}



}
