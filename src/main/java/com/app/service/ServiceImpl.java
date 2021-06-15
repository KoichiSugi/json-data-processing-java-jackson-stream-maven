package com.app.service;

import com.app.user.Row;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * @Author Koichi Sugi
 * Modified 15/06/2021
 */
public class ServiceImpl implements Service {
    //Get sorted individualPnL using Stream
    @Override
    public HashMap<Integer, Float> getIndividualPnL(List<Row> rows) {

        HashMap<Integer, Float> individualPnL = new HashMap<Integer, Float>();
        //sum up all individual PnL
        for (int i = 0; i < rows.size(); i++) {
            if (!individualPnL.containsKey(rows.get(i).getUserId())) {
                //if a key does not exist, newly create a pair
                individualPnL.put(rows.get(i).getUserId(), rows.get(i).getProfit() + rows.get(i).getSwaps() + rows.get(i).getCommission());
            } else {
                //if a key exists, sum up
                individualPnL.put(rows.get(i).getUserId(), individualPnL.get(rows.get(i).getUserId()) + rows.get(i).getProfit() + rows.get(i).getSwaps() + rows.get(i).getCommission());
            }
        }
        //print the map in descending order
        individualPnL
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(e -> System.out.println("User ID: " + e.getKey() + " PnL" + e.getValue()));

        return individualPnL;


    }

    @Override
    public void serializeJson(List<Row> rows, HashMap<Integer, Float> individualPnL) {

        for (int i = 0; i < rows.size(); i++) {
            rows.get(i).setPnL(rows.get(i).getCommission() + rows.get(i).getProfit() + rows.get(i).getSwaps());
        }
        try {
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT
            );
            mapper.writeValue(new File("src/main/Profit_loss_summary.json"), rows);

            HashMap<Integer, Float> newMap = new HashMap<>();
            newMap = individualPnL
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                    LinkedHashMap::new));


            System.out.println("-------------");
            System.out.println(newMap);

            mapper.writeValue(new File("src/main/descendingIndividualPnL.json"), newMap);
            System.out.println("");
            System.out.println("--JSON file has been generated in the resources folder--");
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Row> getObjectsFromRow(List<Row> rows, int searchTerm) {
        return rows.stream().filter(p -> p.getUserId() == searchTerm).collect(Collectors.toList());
    }
}
