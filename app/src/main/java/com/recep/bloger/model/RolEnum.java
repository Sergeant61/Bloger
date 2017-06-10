package com.recep.bloger.model;

/**
 * Created by recep on 10.06.2017.
 */

public enum RolEnum {

    ADMIN("admin", "Yönetici"), KULLANICI("kullanici", "Kullanıcı");

    String id;
    String value;

    RolEnum(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
