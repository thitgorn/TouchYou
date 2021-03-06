package com.example.kongpon_macbook.touchyou;

/**
 * Created by kongpon-macbook on 5/13/2017 AD.
 */

public class Host {
    private String name;
    private String address;

    public Host(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }


    public String getAddress() {
        return address;
    }

    public String toString() {
        if (address == null) return name;
        if (name == null) return address;
        return String.format("%s (%s)", address, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        Host h = (Host) obj;
        return h.name.equals(this.name) && h.address.equals(this.address);
    }

}
