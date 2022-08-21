package com.otec.Tokcoin.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repo {
    
    
    public List<Map<String,Object>> list() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap();
        Map<String, Object> map2 = new HashMap();
        Map<String, Object> map3 = new HashMap();
        Map<String, Object> map4 = new HashMap();

        map1.put("groupName","groupName");
        map1.put("miner_stake",300);
        map1.put("odd",3.5);
        map1.put("loss",3.5);
        map1.put("profit",3.5);
        map1.put("liquidity",3.5);
        map1.put("liquidator_size",5);
        map1.put("members_ids", Arrays.asList("yter5267389458763456","hgsrt672893456787654"));


        map2.put("groupName","groupName");
        map2.put("miner_stake",300);
        map2.put("odd",3.5);
        map2.put("loss",3.5);
        map2.put("profit",3.5);
        map2.put("liquidity",3.5);
        map2.put("liquidator_size",5);
        map2.put("members_ids", Arrays.asList("yter5267389458763456","hgsrt672893456787654"));
        map3.put("User",map1);
        map4.put("User",map2);
        list.add(map3);
        list.add(map4);
        return list;
    }

            
}
