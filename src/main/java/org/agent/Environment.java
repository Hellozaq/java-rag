package org.agent;

// Environment 类，包含环境类型属性
public class Environment {
    private String environmentType; // 环境类型

    // 构造函数
    public Environment(String environmentType) {
        this.environmentType = environmentType;
    }

    // 智能体感知环境方法
    public void perceive() {
        System.out.println("智能体正在感知 " + environmentType + " 环境...");
    }

    // 智能体影响环境方法
    public void affect() {
        System.out.println("智能体正在影响 " + environmentType + " 环境...");
    }
}