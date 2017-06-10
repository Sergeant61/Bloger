package com.recep.bloger.model;

public class BasliklarReturn {

	private int id;

	private String baslik;

	private String kullaniciAdi;
	
	public BasliklarReturn(){}

	public BasliklarReturn(int id, String baslik, String kullaniciAdi) {
		super();
		this.id = id;
		this.baslik = baslik;
		this.kullaniciAdi = kullaniciAdi;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBaslik() {
		return baslik;
	}

	public void setBaslik(String baslik) {
		this.baslik = baslik;
	}

	public String getKullaniciAdi() {
		return kullaniciAdi;
	}

	public void setKullaniciAdi(String kullaniciAdi) {
		this.kullaniciAdi = kullaniciAdi;
	}

}
