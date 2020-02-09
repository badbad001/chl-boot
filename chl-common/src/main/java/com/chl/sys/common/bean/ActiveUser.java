package com.chl.sys.common.bean;


import com.chl.sys.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveUser {

	private User user;

	//权限编码集合
	private List<String> percodes;

	//这个角色暂时用不到
	private List<String> role;
}
