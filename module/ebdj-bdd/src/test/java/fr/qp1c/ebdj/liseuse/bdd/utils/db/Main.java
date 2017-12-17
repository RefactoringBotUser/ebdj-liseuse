package fr.qp1c.ebdj.liseuse.bdd.utils.db;

import fr.qp1c.ebdj.liseuse.commun.configuration.Configuration;

public class Main {

    public static void main(String[] args) {
        System.out.println(Configuration.getInstance().getCockpitUrl());
        System.out.println(Configuration.getInstance().getCockpitBdjNom());
        System.out.println(Configuration.getInstance().getCockpitBdjCle());
    }
}
