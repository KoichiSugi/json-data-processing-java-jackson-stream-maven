package com.app.service;

import com.app.user.Row;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Koichi Sugi
 */
public interface Service {
    HashMap<Integer, Float> getIndividualPnL(List<Row> rows);
}
