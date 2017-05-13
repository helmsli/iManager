package com.xinwei.process.entity;

import java.util.List;

public class FlowProcessDatakeyList {
   private List<FlowProcessDatakey> lists = new java.util.ArrayList<FlowProcessDatakey>();
   
   public synchronized void add(FlowProcessDatakey flowProcessDatakey)
   {
	   lists.add(flowProcessDatakey);
   }
}
