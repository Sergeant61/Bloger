package com.recep.bloger.entity;

import java.util.Date;

public class Mesaj {

	private int id;

	private String mesaj;

	private Date tarih;

	private Basliklar basliklars;

	private User user;

	public Mesaj(String mesaj, Date tarih, Basliklar basliklars, User user) {
		this.mesaj = mesaj;
		this.tarih = tarih;
		this.basliklars = basliklars;
		this.user = user;
	}

	public Mesaj(int id, String mesaj, Date tarih, Basliklar basliklars, User user) {
		this.id = id;
		this.mesaj = mesaj;
		this.tarih = tarih;
		this.basliklars = basliklars;
		this.user = user;
	}

	public Date getTarih() {
		return tarih;
	}

	public void setTarih(Date tarih) {
		this.tarih = tarih;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMesaj() {
		return mesaj;
	}

	public void setMesaj(String mesaj) {
		this.mesaj = mesaj;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Basliklar getBasliklars() {
		return basliklars;
	}

	public void setBasliklars(Basliklar basliklars) {
		this.basliklars = basliklars;
	}
	
	

}
