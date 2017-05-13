var menuData = {
	"result": "0",
	"responseInfo": {
		"module": [
		{
			"id": 1,
			"name": "MENU_HOME",
			"url": "#",
			"priority": 1,
			"imgurl": "fa-home",
			"key": "um:view",
			"type": "0",
			"parent_id":"root",
			"children": [{
				"id": 2,
				"name": "MENU_DASHBOARD",
				"url": "${base}/manager/goto_home.do",
				"sn": "Stat",
				"priority": 1,
				"parent_id": 1,
				"imgurl": "#",
				"key": "um:view",
				"type": "0",
				"children":[]
			},
			{
				"id": 3,
				"name": "MENU_PRODUCT_LIST",
				"url": "${base}/manager/goto_product_list.do",
				"sn": "Stat",
				"priority": 1,
				"parent_id": 1,
				"imgurl": "#",
				"key": "um:view",
				"type": "0",
				"children":[]
			}]
		},
		{
			"id":4,
			"name":"MEMU_FORMS",
			"url":"#",
			"priority": 1,
			"imgurl": "fa-pencil-square-o",
			"key": "um:view",
			"type": "0",
			"parent_id":"root",
			"children":[
			   {
			   	"id":5,
				"name":"MEMU_FORMS_1",
				"url":"${base}/manager/goto_form.do",
				"priority": 1,
				"imgurl": "#",
				"key": "um:view",
				"type": "0",
				"parent_id":"4",
				"children":[]
			   },
			   {
			   	"id":6,
				"name":"MEMU_FORMS_2",
				"url":"${base}/manager/goto_form_2.do",
				"priority": 1,
				"imgurl": "#",
				"key": "um:view",
				"type": "0",
				"parent_id":"4",
				"children":[]
			   }
			]
		},
		{
			"id":7,
			"name":"MENU_TABLES",
			"url":"#",
			"priority": 1,
			"imgurl": "fa-table",
			"key": "um:view",
			"type": "0",
			"parent_id":"root",
			"children":[
			    {
			    	"id":8,
					"name":"MENU_TABLES_1",
					"url":"${base}/manager/goto_table.do",
					"priority": 1,
					"imgurl": "#",
					"key": "um:view",
					"type": "0",
					"parent_id":"7",
					"children":[]
			    },
			    {
			    	"id":9,
					"name":"MENU_TABLES_2",
					"url":"${base}/manager/goto_table_2.do",
					"priority": 1,
					"imgurl": "#",
					"key": "um:view",
					"type": "0",
					"parent_id":"7",
					"children":[]
			    },
			    {
			    	"id":10,
					"name":"MENU_TABLES_3",
					"url":"${base}/manager/goto_dataTable.do",
					"priority": 1,
					"imgurl": "#",
					"key": "um:view",
					"type": "0",
					"parent_id":"7",
					"children":[]
			    }
			]
		},
		{
			"id":11,
			"name":"MENU_MODAL",
			"url":"${base}/manager/goto_modal.do",
			"priority": 1,
			"imgurl": "fa-clone",
			"key": "um:view",
			"type": "0",
			"parent_id":"root",
			"children":[]
		},
		{
			"id":12,
			"name":"MENU_TREE",
			"url":"${base}/manager/goto_tree.do",
			"priority": 1,
			"imgurl": "fa-sitemap",
			"key": "um:view",
			"type": "0",
			"parent_id":"root",
			"children":[]
		},
		{
			"id":13,
			"name":"MENU_STEP",
			"url":"#",
			"priority": 1,
			"imgurl": "fa-files-o",
			"key": "um:view",
			"type": "0",
			"parent_id":"root",
			"children":[
			   {
			   	"id":14,
				"name":"MENU_STEP_1",
				"url":"${base}/manager/goto_step.do",
				"priority": 1,
				"imgurl": "",
				"key": "um:view",
				"type": "0",
				"parent_id":"13",
					"children":[]
			   },
			   {
			   	"id":15,
				"name":"MENU_STEP_2",
				"url":"${base}/manager/goto_step_2.do",
				"priority": 1,
				"imgurl": "",
				"key": "um:view",
				"type": "0",
				"parent_id":"13",
					"children":[]
			   }
			]
		},{
			"id":16,
			"name":"MENU_USER_CENTER",
			"url":"#",
			"priority": 1,
			"imgurl": "fa-user",
			"key": "um:view",
			"type": "0",
			"parent_id":"root",
			"children":[
				{
					"id":17,
					"name":"MENU_USER_INFO",
					"url":"${base}/manager/goto_userInfo.do",
					"priority": 1,
					"imgurl": "",
					"key": "um:view",
					"type": "0",
					"parent_id":"16",
					"children":[]
				},
				{
					"id":18,
					"name":"MENU_MODIFY_PWD",
					"url":"${base}/manager/goto_userPassword_modify.do",
					"priority": 1,
					"imgurl": "",
					"key": "um:view",
					"type": "0",
					"parent_id":"16",
					"children":[]
				},
				{
					"id":19,
					"name":"MENU_USER_MS",
					"url":"${base}/manager/goto_user_ms.do",
					"priority": 1,
					"imgurl": "",
					"key": "um:view",
					"type": "0",
					"parent_id":"16",
					"children":[]
				},
				{
					"id":20,
					"name":"MENU_ROLE_MS",
					"url":"${base}/manager/goto_roleManage.do",
					"priority": 1,
					"imgurl": "",
					"key": "um:view",
					"type": "0",
					"parent_id":"16",
					"children":[]
				}
			]
		}
			
		]
	}
}