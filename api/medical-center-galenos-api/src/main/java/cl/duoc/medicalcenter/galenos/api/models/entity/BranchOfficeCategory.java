package cl.duoc.medicalcenter.galenos.api.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name="BRANCH_OFFICE_CATEGORY")
@Proxy(lazy = false)
public class BranchOfficeCategory implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="BRANCH_OFFICE_CAEGORY_ID")
	private long branchOfficeCategory;

    @JoinColumn(name = "BRANCH_OFFICE_ID", referencedColumnName = "BRANCH_OFFICE_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BranchOffice branchOfficeId;
    
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Category categoryId;

	public BranchOfficeCategory() {
		
	}

	public BranchOfficeCategory(long branchOfficeCategory) {
		this.branchOfficeCategory = branchOfficeCategory;
	}

	public long getBranchOfficeCategory() {
		return branchOfficeCategory;
	}

	public void setBranchOfficeCategory(long branchOfficeCategory) {
		this.branchOfficeCategory = branchOfficeCategory;
	}

	public BranchOffice getBranchOfficeId() {
		return branchOfficeId;
	}

	public void setBranchOfficeId(BranchOffice branchOfficeId) {
		this.branchOfficeId = branchOfficeId;
	}

	public Category getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Category categoryId) {
		this.categoryId = categoryId;
	}
}
