package com.xinwei.process.entity;

public class TaskDefKeyName {
    private String taskDefKey;

    private String name;

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	@Override
	public String toString() {
		return "TaskDefKeyName [taskDefKey=" + taskDefKey + ", name=" + name
				+ "]";
	}
    
}