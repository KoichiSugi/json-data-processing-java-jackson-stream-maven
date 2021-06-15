package com.app.main;

import com.app.service.ServiceImpl;
import com.app.user.Row;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @Author Koichi Sugi
 */
class Main {
    public static final File ClientsRecords = new File("src/main/resources/MOCK_DATA.json");

    public static void main(String[] args) {
        String ClientsRecordPath = ClientsRecords.getAbsolutePath();
        HashMap<Integer, Float> groupPnL = new HashMap<>();
        HashMap<Integer, Float> clientTotalPnl = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        try {
            List<Row> rows = Arrays.asList(mapper.treeToValue(mapper.readTree(new File(ClientsRecordPath)).get("rows"), Row[].class));
            ServiceImpl idp = new ServiceImpl();
            HashMap<Integer, Float> individualPnL = idp.getIndividualPnL(rows);

            System.out.println(idp.getObjectsFromRow(rows, 96224));
            idp.serializeJson(rows, individualPnL);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
