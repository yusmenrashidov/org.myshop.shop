package org.org.myshop.shop.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.myshop.shop.model.Vendor;

@Entity
@Table(name = "vendor")
@NamedQueries({
	@NamedQuery(name="vendor.read", query="SELECT vendor FROM VendorEntity vendor")	
})
public class VendorEntity {

	
	@Id
	private String id = "";
	private String name = "";
	
	public VendorEntity() {
		super();
	}
	
	public VendorEntity(Vendor vendor) {
		this.id = vendor.getId();
		this.name = vendor.getName();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Vendor toVendor() {
		Vendor vendor = new Vendor();
		
		vendor.setId(getId());
		vendor.setName(getName());
		
		return vendor;
	}
}
