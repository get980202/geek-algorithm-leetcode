package com.frankcooper.bank;

import java.util.*;

/**
 * @Date 2020/8/31
 * @Author Frank Cooper
 * @Description
 */
public class _332 {

    //收集结果返回
    List<String> resList = new ArrayList<>();

    public List<String> findItinerary(List<List<String>> tickets) {
        //生成graph
        Map<String, ArrayList<String>> graph = new HashMap<>();
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(to);
        }
        //每个机场指向的机场按字典序排序
        //如 JFK->[LGB,LGA] 变成 JFK->[LGA,LGB]
        for (ArrayList<String> values : graph.values()) {
            Collections.sort(values);
        }
        dfs("JFK", graph);
        return resList;

    }

    /**
     *dfs
     * @param candidate 当前去的机场
     * @param graph 整个机场的图
     */
    private void dfs(String candidate, Map<String, ArrayList<String>> graph) {
        ArrayList<String> nexts = graph.get(candidate);
        //当前机场如果还是有机场可以，一直去目标机场
        while (nexts != null && !nexts.isEmpty()) {
            //去过这个机场，就将其移除掉，因为排序了，每次取第一个
            String next = nexts.remove(0);
            dfs(next, graph);
        }
        System.out.println(candidate);
        //最先开始往resList加入的是哪些以当前机场为from，没有to可去的
        resList.add(0, candidate);
    }

}
