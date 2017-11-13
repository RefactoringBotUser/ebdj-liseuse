package fr.qp1c.ebdj.liseuse.bdd.utils.db;

import fr.qp1c.ebdj.liseuse.bdd.configuration.Configuration;

public class Main {

    public static void main(String[] args) {
        System.out.println(Configuration.getInstance().getUrlCockpit());
        System.out.println(Configuration.getInstance().getNomCockpit());
    }
}
