package org.agent;

// MASExample 类，用于演示多智能体系统的使用
public class MASExample {
    public static void main(String[] args) {
        // 创建 Agent 实例
        Agent agent = new Agent("分析员", new String[]{"数据分析", "逻辑推理"}, "知识图谱模型");
        agent.learn();
        agent.plan();
        agent.reason();
        agent.decide();
        agent.addAbility("机器学习");
        String llmResponse = agent.callLLM();
        System.out.println("大模型回复: " + llmResponse);

        // 创建 Environment 实例
        Environment environment = new Environment("复杂环境");
        environment.perceive();
        environment.affect();

        // 创建 Interaction 实例
        Interaction interaction = new Interaction("合作");
        interaction.interact();

        // 创建 HierarchicalOrganization 实例
        HierarchicalOrganization hierarchicalOrganization = new HierarchicalOrganization();
        hierarchicalOrganization.organizeAgents();

        // 创建 EmergentOrganization 实例
        EmergentOrganization emergentOrganization = new EmergentOrganization();
        emergentOrganization.organizeAgents();
    }
}