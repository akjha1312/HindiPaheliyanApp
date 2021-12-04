package com.aj.pehliyan;

public class Categories {
    private static String[] menuItem = {"सरल पहेलियां", "बाल पहेलियां","मजेदार पहेलियां", "कठिन पहेलियां",
            "दिमाग घूमाने वाली पहेलियां", "गणित पहेलियां","समय की पहेलियां","फ़ूड पहेलियां","पशु पहेलियां"};
    private static int[] jsonFilePath = {R.raw.easy_paheliya, R.raw.baal_pahliya, R.raw.majendar_paheliya,
            R.raw.hard_paheliya, R.raw.mind_ghumana_paheliya, R.raw.math_paheliya, R.raw.time_paheliya,
            R.raw.food_paheliya, R.raw.animal_paheliya};

    public static String[] getMenuItem() {
        return menuItem;
    }

    public static int[] getJsonFilePath() {
        return jsonFilePath;
    }
}
