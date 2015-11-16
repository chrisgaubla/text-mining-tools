/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icdtextminer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 *
 * @author chrisgaubla
 */
public class CommonWordFilter {

    private final TreeSet<String> commonWords;

    public CommonWordFilter() {
        commonWords = new TreeSet<>();
        String[] commonWordsArray = {"of", "unspecified", "with", "and", "for", "or", "Other", "in", "other", "Unspecified", "to", "at", "by"};
        commonWords.addAll(Arrays.asList(commonWordsArray));
    }

    public ArrayList<String> getFilteredStringList(String input) {

        String[] inputArray = input.split(" ");
        ArrayList<String> outputList = new ArrayList<>();

        for (String word : inputArray) {
            if (!commonWords.contains(word)) {
                outputList.add(word);
            }
        }
        return outputList;
    }
}
