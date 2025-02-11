### 负载均衡接口文档

#### 1. `LoadBalancer` 接口
- **功能描述**：该接口定义了负载均衡器的基本行为，即从一组服务实例中选择一个实例。
- **接口方法**：
```java
/**
 * 从给定的服务实例列表中选择一个实例。
 * 
 * @param instances 服务实例列表，可能为空。
 * @return 选中的服务实例，如果列表为空则返回 null。
 */
Instance select(List<Instance> instances);
```
- **使用方法**：其他具体的负载均衡器类需要实现这个接口，并在 `select` 方法中实现具体的选择逻辑。

#### 2. `RoundRobinLoadBalancer` 类
- **功能描述**：实现了轮询负载均衡策略，按照顺序依次选择服务实例。
- **类属性**：
```java
private final AtomicInteger index = new AtomicInteger(0);
```
- `index`：用于记录当前选择的实例索引，使用 `AtomicInteger` 保证线程安全。
- **方法实现**：
```java
/**
 * 从给定的服务实例列表中使用轮询策略选择一个实例。
 * 
 * @param instances 服务实例列表，可能为空。
 * @return 选中的服务实例，如果列表为空则返回 null。
 */
@Override
public Instance select(List<Instance> instances) {
    if (instances == null || instances.isEmpty()) {
        return null;
    }
    int currentIndex = index.getAndIncrement() % instances.size();
    return instances.get(currentIndex);
}
```
- **使用方法**：创建 `RoundRobinLoadBalancer` 实例，然后将其作为参数传递给 `NacosLoadBalancingClient` 类，即可使用轮询策略进行负载均衡。
```java
LoadBalancer roundRobinLoadBalancer = new RoundRobinLoadBalancer();
NacosLoadBalancingClient client = new NacosLoadBalancingClient("your-service-name", roundRobinLoadBalancer);
Instance instance = client.getNextInstance();
```

#### 3. `RandomLoadBalancer` 类
- **功能描述**：实现了随机负载均衡策略，随机选择一个服务实例。
- **类属性**：
```java
private final Random random = new Random();
```
- `random`：用于生成随机数。
- **方法实现**：
```java
/**
 * 从给定的服务实例列表中使用随机策略选择一个实例。
 * 
 * @param instances 服务实例列表，可能为空。
 * @return 选中的服务实例，如果列表为空则返回 null。
 */
@Override
public Instance select(List<Instance> instances) {
    if (instances == null || instances.isEmpty()) {
        return null;
    }
    return instances.get(random.nextInt(instances.size()));
}
```
- **使用方法**：创建 `RandomLoadBalancer` 实例，然后将其作为参数传递给 `NacosLoadBalancingClient` 类，即可使用随机策略进行负载均衡。
```java
LoadBalancer randomLoadBalancer = new RandomLoadBalancer();
NacosLoadBalancingClient client = new NacosLoadBalancingClient("your-service-name", randomLoadBalancer);
Instance instance = client.getNextInstance();
```

#### 4. `WeightedRandomLoadBalancer` 类
- **功能描述**：实现了根据服务实例权重进行随机选择的负载均衡策略。
- **类属性**：
```java
private final Random random = new Random();
```
- `random`：用于生成随机数。
- **方法实现**：
```java
/**
 * 从给定的服务实例列表中使用加权随机策略选择一个实例。
 * 
 * @param instances 服务实例列表，可能为空。
 * @return 选中的服务实例，如果列表为空则返回 null。
 */
@Override
public Instance select(List<Instance> instances) {
    if (instances == null || instances.isEmpty()) {
        return null;
    }
    double totalWeight = 0;
    for (Instance instance : instances) {
        totalWeight += instance.getWeight();
    }
    double randomWeight = random.nextDouble() * totalWeight;
    double currentWeight = 0;
    for (Instance instance : instances) {
        currentWeight += instance.getWeight();
        if (currentWeight >= randomWeight) {
            return instance;
        }
    }
    return instances.get(0);
}
```
- **使用方法**：创建 `WeightedRandomLoadBalancer` 实例，然后将其作为参数传递给 `NacosLoadBalancingClient` 类，即可使用加权随机策略进行负载均衡。
```java
LoadBalancer weightedRandomLoadBalancer = new WeightedRandomLoadBalancer();
NacosLoadBalancingClient client = new NacosLoadBalancingClient("your-service-name", weightedRandomLoadBalancer);
Instance instance = client.getNextInstance();
```

#### 5. `NacosLoadBalancingClient` 类
- **功能描述**：负责从 Nacos 服务中获取服务实例，并处理实例的动态变化，同时使用指定的负载均衡策略选择实例。
- **类属性**：
```java
private static final String SERVER_ADDRESSES = "http://124.223.85.176:8848";
private static final String NAMESPACE = "public";
private final NamingService namingService;
private final List<Instance> instances = new ArrayList<>();
private final LoadBalancer loadBalancer;
```
- `SERVER_ADDRESSES`：Nacos 服务器地址。
- `NAMESPACE`：Nacos 命名空间。
- `namingService`：Nacos 命名服务实例。
- `instances`：存储当前可用的服务实例列表。
- `loadBalancer`：使用的负载均衡策略实例。
- **构造方法**：
```java
/**
 * 构造方法，初始化 Nacos 命名服务，并订阅指定服务的实例变化事件。
 * 
 * @param serviceName 要发现的服务名称。
 * @param loadBalancer 使用的负载均衡策略实例。
 * @throws NacosException 如果与 Nacos 服务通信出现异常。
 */
public NacosLoadBalancingClient(String serviceName, LoadBalancer loadBalancer) throws NacosException {
    this.loadBalancer = loadBalancer;
    Properties properties = new Properties();
    properties.put("serverAddr", SERVER_ADDRESSES);
    properties.put("namespace", NAMESPACE);
    namingService = NacosFactory.createNamingService(properties);
    namingService.subscribe(serviceName, new EventListener() {
        @Override
        public void onEvent(Event event) {
            if (event instanceof NamingEvent) {
                NamingEvent namingEvent = (NamingEvent) event;
                instances.clear();
                instances.addAll(namingEvent.getInstances());
                System.out.println("Service instances updated: " + instances);
            }
        }
    });
    instances.addAll(namingService.getAllInstances(serviceName));
}
```
- **公共方法**：
```java
/**
 * 使用指定的负载均衡策略选择下一个服务实例。
 * 
 * @return 选中的服务实例，如果没有可用实例则返回 null。
 */
public Instance getNextInstance() {
    return loadBalancer.select(instances);
}
```
- **使用方法**：创建 `NacosLoadBalancingClient` 实例，传入服务名称和负载均衡策略实例，然后调用 `getNextInstance` 方法获取下一个服务实例。
```java
try {
    LoadBalancer loadBalancer = new RoundRobinLoadBalancer();
    NacosLoadBalancingClient client = new NacosLoadBalancingClient("your-service-name", loadBalancer);
    Instance instance = client.getNextInstance();
    System.out.println("Selected instance: " + instance);
} catch (NacosException e) {
    e.printStackTrace();
}
```

#### 6. `Main` 类
- **功能描述**：演示如何使用不同的负载均衡策略从 Nacos 服务中选择服务实例。
- **使用方法**：运行 `Main` 类的 `main` 方法，即可看到使用轮询、随机和加权随机三种策略选择服务实例的结果。
```java
public static void main(String[] args) throws NacosException {
    String serviceName = "your-service-name";

    // 使用轮询策略
    LoadBalancer roundRobinLoadBalancer = new RoundRobinLoadBalancer();
    NacosLoadBalancingClient roundRobinClient = new NacosLoadBalancingClient(serviceName, roundRobinLoadBalancer);
    Instance roundRobinInstance = roundRobinClient.getNextInstance();
    System.out.println("Round Robin selected instance: " + roundRobinInstance);

    // 使用随机策略
    LoadBalancer randomLoadBalancer = new RandomLoadBalancer();
    NacosLoadBalancingClient randomClient = new NacosLoadBalancingClient(serviceName, randomLoadBalancer);
    Instance randomInstance = randomClient.getNextInstance();
    System.out.println("Random selected instance: " + randomInstance);

    // 使用加权随机策略
    LoadBalancer weightedRandomLoadBalancer = new WeightedRandomLoadBalancer();
    NacosLoadBalancingClient weightedRandomClient = new NacosLoadBalancingClient(serviceName, weightedRandomLoadBalancer);
    Instance weightedRandomInstance = weightedRandomClient.getNextInstance();
    System.out.println("Weighted Random selected instance: " + weightedRandomInstance);
}
```
