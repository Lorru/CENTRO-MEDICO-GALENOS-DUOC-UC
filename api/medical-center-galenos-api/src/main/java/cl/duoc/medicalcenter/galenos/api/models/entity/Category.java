package cl.duoc.medicalcenter.galenos.api.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="CATEGORY")
@Proxy(lazy = false)
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="CATEGORY_ID")
	private long categoryId;
	
	@Column(name="CATEGORY_DESCRIPTION")
	private String categoryDescription;
	
	@Column(name="CATEGORY_STATUS")
	private char categoryStatus;
	
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoryId", fetch = FetchType.LAZY)
    private List<Personal> personalList;
	
	public Category() {
		
	}

	public Category(long categoryId) {
		this.categoryId = categoryId;
	}

	public Category(long categoryId, String categoryDescription, char categoryStatus) {
		this.categoryId = categoryId;
		this.categoryDescription = categoryDescription;
		this.categoryStatus = categoryStatus;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public char getCategoryStatus() {
		return categoryStatus;
	}

	public void setCategoryStatus(char categoryStatus) {
		this.categoryStatus = categoryStatus;
	}

	public List<Personal> getPersonalList() {
		return personalList;
	}

	public void setPersonalList(List<Personal> personalList) {
		this.personalList = personalList;
	}
	
}
