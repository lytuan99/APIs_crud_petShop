package com.Sapo.pet.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.Sapo.pet.model.request.PetModelRequest;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "pets")
public class Pet extends BaseEntity {

	/**
	 * 
	 */
	
	
	@Column(name = "name", length = 45)
	
	private String name;
	@Column(name = "tags", length = 255)
	private String tags;
	@Column(name = "content", columnDefinition = "text")
	private String content;
	@Column(name = "created_on")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "modified_on")
	private Date modifiedOn;
	@Column(name = "status")
	private boolean status;
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")    // mặc định đã là lazy fetching rồi
//	private List<Variant> variants;
	
	public void setPet(Pet pet,PetModelRequest petModelReq, Date createD, Date modifiedD) {
		pet.setName(petModelReq.getName());
		pet.setTags(petModelReq.getTags());
		pet.setContent(petModelReq.getContent());
		pet.setCreatedOn(createD);
		pet.setModifiedOn(modifiedD);
		pet.setStatus(true);
	}
	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
}
