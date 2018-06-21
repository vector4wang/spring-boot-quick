package com.quick.api;


import com.quick.po.Address;
import com.quick.po.ParaModel;
import com.quick.utils.BaseResp;
import com.quick.utils.ResultStatus;
import io.swagger.annotations.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/4/12
 * Time: 8:41
 * Description:
 */
@RestController
@RequestMapping("/api")
@Api(value = "api", description = "WebController") // Swagger UI 对应api的标题描述
public class WebController {

	@ApiOperation(value = "获取地址信息", notes = "我是描述")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "body", name = "address", dataType = "Address", required = true, value = "省", defaultValue = "广东省"),
	})
	@PostMapping(value = "/address")
	public BaseResp<Address> getAddressInfo(@RequestBody Address address) {
		BaseResp<Address> resp = new BaseResp<Address>();
		resp.setMessage(ResultStatus.SUCCESS.getErrorMsg());
		resp.setCode(ResultStatus.SUCCESS.getErrorCode());
		resp.setData(address);
		return resp;
	}

	@PostMapping(value = "/address2")
	public BaseResp<Address> getAddressInfo2() {

		Address address = new Address();
		address.setProvince("wangxc");
		address.setArea("wangxc");
		address.setStreet("wangxc");
		address.setNum("wangxc");
		BaseResp<Address> resp = new BaseResp<>(ResultStatus.SUCCESS, address);
		return resp;

	}

	@ApiOperation("获取地址信息")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "province", dataType = "String", required = true, value = "省", defaultValue = "广东省"),
			@ApiImplicitParam(paramType = "query", name = "area", dataType = "String", required = true, value = "地区", defaultValue = "南山区"),
			@ApiImplicitParam(paramType = "query", name = "street", dataType = "String", required = true, value = "街道", defaultValue = "桃园路"),
			@ApiImplicitParam(paramType = "query", name = "num", dataType = "String", required = true, value = "门牌号", defaultValue = "666")})
	@ApiResponses({@ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
	@RequestMapping(value = "/address/list", method = RequestMethod.POST)
	public BaseResp<List<Address>> getAddressList(@RequestParam(value = "province") String province,
			@RequestParam(value = "area") String area, @RequestParam(value = "street") String street,
			@RequestParam(value = "num") String num) {

		if (StringUtils.isEmpty(province) || StringUtils.isEmpty(area) || StringUtils.isEmpty(street) || StringUtils
				.isEmpty(num)) {
			return new BaseResp(ResultStatus.error_invalid_argument);
		}
		Address address = new Address();
		address.setProvince(province);
		address.setArea(area);
		address.setStreet(street);
		address.setNum(num);
		List<Address> lists = new ArrayList<>();
		lists.add(address);
		lists.add(address);
		lists.add(address);
		return new BaseResp(ResultStatus.SUCCESS, lists);

	}

	@ApiOperation("获取地址信息(参数体)")
	@ApiResponses({@ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
	@RequestMapping(value = "/address/area/list", method = RequestMethod.POST)
	public BaseResp<String> getAddressList(@RequestBody ParaModel paraModel) {
		return new BaseResp(ResultStatus.SUCCESS, paraModel.toString());
	}

	@ApiOperation("获取地址信息(路径传参)")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "area", dataType = "String", required = true, value = "区域", defaultValue = "南山区"),
			@ApiImplicitParam(paramType = "path", name = "number", dataType = "String", required = true, value = "门牌号", defaultValue = "9527")})
	@ApiResponses({@ApiResponse(code = 400, message = "请求参数没填好"),
			@ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
	@RequestMapping(value = "/address/{area}/{number}", method = RequestMethod.GET)
	public BaseResp<String> getAddress(@PathVariable("area") String area, @PathVariable("number") String number) {
		Address address = new Address();
		address.setProvince("广东省");
		address.setArea(area);
		address.setStreet("桃园街道");
		address.setNum(number);
		return new BaseResp(ResultStatus.SUCCESS, address.toString());
	}
}
