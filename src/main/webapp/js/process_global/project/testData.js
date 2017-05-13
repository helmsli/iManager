var projectData={
    "result": "0",
    "responseInfo": {
        "projectDetailInfo": {
            "project": {
                "projectId": 1000000,
                "projectName": "yxg1",
                "projectManagerId": 4,
                "projectManagerName": "projectManagerA",
                "categoryId": 1,
                "subcategory": "[{\"value\":\"互联网\",\"id\":\"202\"}]",
                "cycleType": "[{\"value\":\"3年\",\"id\":\"201\"}]",
                "mainCurrentState": "{\"state\":\"009\",\"stateName\":\"项目经理提交中期评审报告\",\"taskId\":\"992530\"}",
                "mainPreviousState": "{\"state\":\"008\",\"stateName\":\"管理人员指定中期评审专家\",\"taskId\":\"992528\"}",
                "changeCurrentState": null,
                "changePreviousState": null,
                "changeDataId": null,
                "reportCurrentState": "{\"state\":\"019\",\"stateName\":\"提交周期性报告\",\"taskId\":\"947522\"}",
                "changeProcessInstanceId": null,
                "projectProcessInstanceId": "990001",
                "projectExtInfo": "[{\"taskDescribe\":\"yxg1\",\"taskbackground\":\"yxg1\",\"taskgoal\":\"yxg1\",\"taskDanger\":\"yxg1\",\"taskcreate\":\"yxg1\",\"taskplan\":\"[{\\\"planTime\\\":\\\"1\\\",\\\"planContext\\\":\\\"1\\\",\\\"planOutPUT\\\":\\\"1\\\",\\\"planOutWord\\\":\\\"1\\\"},{\\\"planTime\\\":\\\"1\\\",\\\"planContext\\\":\\\"1\\\",\\\"planOutPUT\\\":\\\"1\\\",\\\"planOutWord\\\":\\\"1\\\"}]\"}]",
                "projectTaskDetail": "[{\"postName\":\"1\",\"requireName\":\"1\"},{\"postName\":\"1\",\"requireName\":\"1\"}]",
                "selfAppraise": null,
                "departLeaderAppraise": null,
                "projectCosts": "[{\"payName\":\"1\",\"payPrice\":\"1\",\"payNumber\":\"1\",\"paySbutotal\":\"1\",\"payRemarks\":\"1\"},{\"payName\":\"1\",\"payPrice\":\"1\",\"payNumber\":\"1\",\"paySbutotal\":\"1\",\"payRemarks\":\"1\"}]",
                "startTime": "2017-02-16 20:59:15",
                "completeTime": "2018-02-16 20:59:15",
                "projectApplyTime": "2017-02-16 21:00:03",
                "telno": "123456",
                "email": "123456@126.com",
                "projectMilestone": "项目里程碑",
                "state": "start"
            },
            "beginStage": {
                "expertReview": [
                    {
                        "recordId": 1000000,
                        "projectId": 1000000,
                        "stage": "begin",
                        "time": "2017-02-16 21:13:49",
                        "expertList": "[{\"postName\":\"33\"}]",
                        "room": null,
                        "expertScore": "[{\"postName\":\"33\",\"expertScore\":5},{\"postName\":\"22\",\"expertScore\":4}]",
                        "viewMaterials": [
                            {
                                "annexId": 1000001,
                                "annexName": "2017-02-16-21-37-36_",
                                "originalFilename": "",
                                "typeId": 1,
                                "projectId": 1000000,
                                "path": "http://localhost:9090/process/uploadDir/2017-02-16-21-37-36_",
                                "userId": "4",
                                "uploadTime": "2017-02-16 21:37:36"
                            }
                        ],
                        "viewReports": null
                    }
                ],
                "committeeApproval": [
                    {
                        "recordId": 1000000,
                        "projectId": 1000000,
                        "stage": "begin",
                        "time": "2017-02-16 21:14:23",
                        "conclusion": "同意",
                        "comments": "",
                        "userId": 6
                    }
                ],
                "departleaderApproval": [
                    {
                        "recordId": 1000000,
                        "projectId": 1000000,
                        "stage": "begin",
                        "time": "2017-02-16 21:15:05",
                        "conclusion": "同意",
                        "comments": "我同意了",
                        "userId": 7
                    }
                ]
            },
            "middleStage": {
                "expertReview": [],
                "committeeApproval": [],
                "departleaderApproval": []
            },
            "lastStage": {
                "expertReview": [],
                "committeeApproval": [],
                "departleaderApproval": []
            },
            "projectAnnexLists": []
        }
    }
}





var ProjectFlowInfoData={
	    "result": "0",
	    "responseInfo": {
	    	"lists":[
	    	       {
	    	       "projectId": 1000000,
	                "projectName": "yxg1",
	                "taskType":"isFile",
	                "projectManagerId": 4,
	                "projectManagerName": "projectManagerA",
	                "categoryId": 1,
	                "service_type":"pm_sanjibm_qingqiu",
	                "result":"{\"result\":\"0\",\"comment\":\"\"\}",
                    "data1":"{\"originalFilename\":\"经理报告文件.txt\",\"path\":\"http://localhost:9090/process/uploadDir/2017-02-16-21-37-36_\"\}",
	                "createTime":"2017-02-23 15:03:27",
                    "data2":{}
	    	       },
	    	       {
		    	       "projectId": 1000000,
		                "projectName": "yxg1",
		                "taskType":"isApprove",
		                "projectManagerId": 4,
		                "projectManagerName": "projectManagerA",
		                "service_type":"pm_xiangmugly_chushen",
		                "categoryId": 1,
		                "result":"{\"result\":\"0\",\"comment\":\"我爱天安门\"\}",
	                    "data1":"{\"originalFilename\":\"经理报告文件.txt\",\"path\":\"http://localhost:9090/process/uploadDir/2017-02-16-21-37-36_\"\}",
	                     "data2":{},
	                     "createTime":"2015-02-23 15:03:27"
		    	       }
	             ]
	}
}
