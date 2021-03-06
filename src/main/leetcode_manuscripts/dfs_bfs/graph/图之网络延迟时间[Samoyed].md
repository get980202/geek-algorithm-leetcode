## 图之网络延迟时间[Samoyed]

> 一道模板题，详见文末推荐阅读

![istockphoto-882484506-1024x1024](D:\Dev\SrcCode\geek-algorithm-leetcode\src\main\leetcode_manuscripts\dfs_bfs\graph\图之网络延迟时间.assets\istockphoto-882484506-1024x1024.jpg)

### 方法1:Dijkstra

```java
   Integer INF = Integer.MAX_VALUE;

        public int networkDelayTime(int[][] times, int N, int K) {
            Map<Integer, List<int[]>> graph = new HashMap<>();
            //初始化邻接表
            for (int[] time : times) {
                graph.computeIfAbsent(time[0], t -> new ArrayList<>()).add(new int[]{time[1], time[2]});
            }
            int[] dis = new int[N + 1];
            Arrays.fill(dis, INF);
            boolean[] vis = new boolean[N + 1];
            //多设置了0这个索引的冗余，距离设置为0
            dis[K] = 0;
            dis[0] = 0;
            //优先队列，按距离从小到大排列
            PriorityQueue<Integer> pq = new PriorityQueue<>(((o1, o2) -> dis[o1] - dis[o2]));
            pq.offer(K);
            while (!pq.isEmpty()) {
                //弹出
                Integer curr = pq.poll();
                //访问过不用再执行下面的逻辑
                if (vis[curr]) continue;
                //标记访问
                vis[curr] = true;
                //获取到当前节点的邻居节点
                List<int[]> neighbours = graph.getOrDefault(curr, new ArrayList<>());
                for (int[] neighbour : neighbours) {
                    int to = neighbour[0];
                    if (vis[to]) continue;
                    //如果当前dis[to]大，则更新之
                    dis[to] = Math.min(dis[to], dis[curr] + neighbour[1]);
                    pq.offer(to);
                }
            }
            for (int d : dis) System.out.print(d + " ");
            int max = 0;
            for (int d : dis) max = Math.max(max, d);
            return max == INF ? -1 : max;
        }
```

### 方法2:Bellman-Ford

```java
        Integer INF = Integer.MAX_VALUE;

        /**
         * @param edges 边
         * @param N     顶点数
         * @param K     其实顶点
         * @return
         */
        public int networkDelayTime(int[][] edges, int N, int K) {

            int[] dist = new int[N + 1];
            Arrays.fill(dist, INF);
            dist[K] = 0;
            for (int i = 1; i <= N; i++) {
                for (int j = 0; j < edges.length; j++) {
                   	//松弛
                    if (dist[edges[j][0]] != INF && dist[edges[j][1]] > dist[edges[j][0]] + edges[j][2]) {
                        dist[edges[j][1]] = dist[edges[j][0]] + edges[j][2];
                    }
                }
            }
            int max = 0;
            for (int i = 1; i <= N; ++i) max = Math.max(max, dist[i]);
            return max == INF ? -1 : max;
        }
```

### 方法3:SPFA

```java
        Integer INF = Integer.MAX_VALUE;

        public int networkDelayTime(int[][] edges, int N, int K) {
            List<Integer> queue = new ArrayList<>();
            long[] dist = new long[N + 1];
            boolean[] used = new boolean[N + 1];
            int[] nums = new int[N + 1];
            Arrays.fill(dist, INF);
            dist[K] = 0;
            used[K] = true;
            queue.add(K);
            while (!queue.isEmpty()) {
                int a = queue.get(0);
                queue.remove(0);
                for (int i = 0; i < edges.length; i++) {
                    int u = edges[i][0], v = edges[i][1], w = edges[i][2];
                    System.out.printf("u:%d,v:%d,w:%d\n", u, v, w);
                    if (a == u && dist[v] > dist[u] + w) {
                        dist[v] = dist[u] + w;
                        if (!used[v]) {
                            queue.add(v);
                            nums[v]++;
                            if (nums[v] >= N) continue;
                            used[v] = true;
                        }
                    }
                }
                used[a] =false;
            }
            long max = 0;
            for (int i = 1; i <= N; ++i) max = Math.max(max, dist[i]);
            return max == INF ? -1 : (int) max;
        }
```

### 方法4:Floyd

```java
        Integer INF = Integer.MAX_VALUE;

        public int networkDelayTime(int[][] edges, int N, int K) {
            int[][] dist = new int[N + 1][N + 1];
            for (int i = 1; i <= N; ++i) {
                for (int j = 1; j <= N; ++j) {
                    dist[i][j] = i == j ? 0 : INF;
                }
            }
            for (int[] edge : edges) {
                dist[edge[0]][edge[1]] = edge[2];
            }
            for (int a = 1; a <= N; ++a) {
                for (int i = 1; i <= N; ++i) {
                    for (int j = 1; j <= N; ++j) {
                        int tmp = (dist[i][a] == INF || dist[a][j] == INF) ? INF : dist[i][a] + dist[a][j];
                        if (dist[i][j] > tmp) {
                            dist[i][j] = tmp;
                        }
                    }
                }
            }
            int max = -1;
            //找K出发的点，K->i，最长的路径
            for (int i = 1; i <= N; ++i) {
                max = Math.max(max, dist[K][i]);
            }
            return max == INF ? -1 : max;
        }
```

### 推荐阅读

| #    | github.io地址                                                | 关键词                    |
| ---- | ------------------------------------------------------------ | ------------------------- |
| 1    | [一文掌握Dijkstra算法](https://wat1r.github.io/2020/09/23/dijkstra-algorithm-master/) | Graph,Shortest Path,Model |
| 2    | [一文掌握Bellman-Ford算法](https://wat1r.github.io/2020/09/22/bellman-ford-algorithm-master/) | Graph,Shortest Path,Model |
| 3    | [一文掌握SPFA算法](https://wat1r.github.io/2020/09/21/spfa-algorithm-master/) | Graph,Shortest Path,Model |
| 4    | [一文掌握Floyd算法](https://wat1r.github.io/2020/09/23/floyd-algorithm-master/) | Graph,Shortest Path,Model |

