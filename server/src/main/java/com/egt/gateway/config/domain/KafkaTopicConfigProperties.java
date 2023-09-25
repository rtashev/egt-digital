package com.egt.gateway.config.domain;

public class KafkaTopicConfigProperties
{
    private String name;
    private int partitions;
    private int replicas;
    private String retentionMs;

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPartitions()
    {
        return this.partitions;
    }

    public void setPartitions(int partitions)
    {
        this.partitions = partitions;
    }

    public int getReplicas()
    {
        return this.replicas;
    }

    public void setReplicas(int replicas)
    {
        this.replicas = replicas;
    }

    public String getRetentionMs()
    {
        return this.retentionMs;
    }

    public void setRetentionMs(String retentionMs)
    {
        this.retentionMs = retentionMs;
    }

}
