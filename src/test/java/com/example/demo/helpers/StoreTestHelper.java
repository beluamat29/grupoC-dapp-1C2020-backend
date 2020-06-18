package com.example.demo.helpers;

import com.example.demo.model.store.Store;
import com.example.demo.model.store.StoreCategory;

import java.util.List;
import java.util.stream.Collectors;

public class StoreTestHelper {

    public List<String> storeCategoriesToString(List<StoreCategory> categories) {
        return categories.stream().map(Enum::toString).collect(Collectors.toList());
    }

    public List<String> storeOpeningDaysToString(Store store) {
        return store.storeSchedule().days().stream().map(Enum::toString).collect(Collectors.toList());
    }


}
