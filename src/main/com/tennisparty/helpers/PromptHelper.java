package com.tennisparty.helpers;

import java.util.Scanner;

/**
 * Helper pour les interactions utilisateur dans la console
 */
public class PromptHelper {

    /**
     * Méthode générique permettant d'interagir avec l'utilisateur via des chaines de charactères
     * @param message input de l'utilisateur
     * @return
     */
    @SuppressWarnings("resource")
    public static String readText(String message) {
        String entry = "";
        do {
            System.out.print(message);
            entry = new Scanner(System.in).nextLine().toUpperCase();
        } while (entry.isEmpty());

        return entry;
    }
}
