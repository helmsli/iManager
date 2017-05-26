var userlistResponse = {
	"result": "0",
	"responseInfo": {
		"page": {
			"totalNum": 25,
			"pageNum": 1,
			"pageSize": 2,
			"startRow": 1,
			"pages": 13,
			"orderField": "",
			"direct": "DESC"
		},
		"lists": [{
			"id":"1",
			"loginname": "admin",
			"firstname": "zhang",
			"lastname": "san",
			"phone": "1351810999911111222",
			"email": "yaoqiang@yahoo.cn",
			"createTime": "Aug 3, 2012 10:58:38 PM",
			"status": 1,
			"birthday": "Aug 3, 2012 10:58:38 PM",
			"address": "北京",
			"sex": 1,
			"imgurl": "图片地址",
			"roleList":[{"id":11,"name":'客服'},{"id":22,"name":"运营"}]
		}]
	}
};

var userModel = {
	dataList: [{
		result: 0,
		page: 1,
		pageSize: 20,
		list: [{
			"id": "851228511",
			"loginname": "",
			"btsType": "单板音",
			"permission": []
		}, {
			"id": "851228522",
			"btsName": "李四",
			"btsType": "单板音",
			"permission": ["0010101"]
		}, {
			"id": "851228533",
			"btsName": "张role_01_02",
			"btsType": "无类型",
			"permission": ["0010101"]
		}, {
			"id": "851228544",
			"btsName": "李role_01_02",
			"btsType": "无类型",
			"permission": ["0010101"]
		}, {
			"id": "851228555",
			"btsName": "王五",
			"btsType": "单板音",
			"permission": ["0010101"]
		}, {
			"id": "851228566",
			"btsName": "赵六",
			"btsType": "单板音",
			"permission": ["0010101"]
		}, {
			"id": "851228547",
			"btsName": "李role_01_02",
			"btsType": "无类型",
			"permission": ["0010101"]
		}, {
			"id": "851228558",
			"btsName": "王五",
			"btsType": "单板音",
			"permission": ["0010101"]
		}, {
			"id": "851228569",
			"btsName": "赵六",
			"btsType": "单板音",
			"permission": ["0010101"]
		}, {
			"id": "8512285310",
			"btsName": "张role_01_02",
			"btsType": "无类型",
			"permission": ["0010101"]
		}]
	}, {
		result: 0,
		page: 2,
		pageSize: 10,
		list: [{
			"id": "8512285111",
			"btsName": "张三",
			"btsType": "单板音",
			"permission": []
		}, {
			"id": "8512285212",
			"btsName": "李四",
			"btsType": "单板音",
			"permission": ["0010101"]
		}]
	}],
	getUserById: function(btsId) {
		var user = {
			"id": "",
			"btsName": "",
			"btsType": "",
			"permission": []
		};
		var userList = window.localStorage.getItem("userList");
		if(userList != null && userList != "") {
			this.dataList = angular.fromJson(userList);
		}
		var arr = this.dataList;
		for(var i = 0; i < arr.length; i++) {
			list = arr[i].list;
			for(var j = 0; j < list.length; j++) {
				if(btsId == list[j].btsId) {
					return list[j];
				}
			}
		}
		return user; //返回空user对象
	},
	setUser: function(user) {

		if(typeof user.btsId === 'undefined' || user.btsId === '') { //新建
			user.btsId = (new Date).getTime();
			this.dataList[1].list.push(user);
		} else { //修改
			var arr = this.dataList;
			for(var i = 0; i < arr.length; i++) {
				list = arr[i].list;
				for(var j = 0; j < list.length; j++) {
					if(user.btsId == list[j].btsId) {
						list[j] = user;
						break;
					}
				}
			}
		}
		window.localStorage.setItem("userList", angular.toJson(this.dataList));

	},
	getUserList: function(index) {
		var userList = window.localStorage.getItem("userList");
		if(userList != null && userList != "" && userList != "[]" && userList.length != 0) {
			this.dataList = angular.fromJson(userList);
		}
		return this.dataList[index].list;
	}
};